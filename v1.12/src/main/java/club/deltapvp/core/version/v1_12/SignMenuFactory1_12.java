/*
 *     DeltaCore is a Minecraft Java API Provider.
 *     Copyright (C) 2021 DeltaDevelopment
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 */

package club.deltapvp.core.version.v1_12;

import club.deltapvp.deltacore.api.utilities.sign.AbstractSignFactory;
import club.deltapvp.deltacore.api.utilities.sign.AbstractSignMenu;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

// This code was made by FrostedSnowman and edited by Atog_.

@SuppressWarnings("deprecation")
public final class SignMenuFactory1_12 extends AbstractSignFactory {

    public SignMenuFactory1_12(Plugin plugin) {
        super(plugin);
    }

    public AbstractSignMenu newMenu(List<String> text) {
        return new SignMenu1_8(text);
    }

    @Override
    public void listen() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, PacketType.Play.Client.UPDATE_SIGN) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                AbstractSignMenu menu = inputs.remove(player);

                if (menu == null) {
                    return;
                }
                event.setCancelled(true);

                WrappedChatComponent[] read = event.getPacket().getChatComponentArrays().read(0);
                List<String> strings = new ArrayList<>();
                for (WrappedChatComponent comp : read) {
                    String json = comp.getJson();
                    String result = json.replaceAll("^\"+|\"+$", "");
                    strings.add(result);
                }

                String[] array = strings.toArray(new String[0]);

                boolean success = menu.response.test(player, array);

                if (!success && menu.reopenIfFail && !menu.forceClose) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> menu.open(player), 2L);
                }
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (player.isOnline()) {
                        Location location = menu.position.toLocation(player.getWorld());
                        player.sendBlockChange(location, Material.WALL_SIGN, (byte) 0);
                        player.sendSignChange(location, menu.text.toArray(new String[0]));
                    }
                }, 2L);
            }
        });
    }

    public class SignMenu1_8 extends AbstractSignMenu {

        public SignMenu1_8(List<String> text) {
            super(text);
        }

        @Override
        public AbstractSignMenu reOpenIfFail(boolean value) {
            this.reopenIfFail = value;
            return this;
        }

        public AbstractSignMenu response(BiPredicate<Player, String[]> response) {
            this.response = response;
            return this;
        }

        public void open(Player player) {
            Objects.requireNonNull(player, "player is null");
            if (!player.isOnline()) {
                return;
            }
            Location location = player.getLocation();
            this.position = new BlockPosition(location.getBlockX(), location.getBlockY() + (255 - location.getBlockY()), location.getBlockZ());
            player.sendBlockChange(this.position.toLocation(location.getWorld()), Material.WALL_SIGN, (byte) 0);
            player.sendSignChange(this.position.toLocation(location.getWorld()), this.text.toArray(new String[0]));

            PacketContainer openSign = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            PacketContainer signData = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

            openSign.getBlockPositionModifier().write(0, this.position);

            NbtCompound signNBT = (NbtCompound) signData.getNbtModifier().read(0);

            for (int line = 0; line < SIGN_LINES; line++) {
                signNBT.put("Text" + (line + 1), this.text.size() > line ? String.format(NBT_FORMAT, color(this.text.get(line))) : "");
            }

            signNBT.put("x", this.position.getX());
            signNBT.put("y", this.position.getY());
            signNBT.put("z", this.position.getZ());
            signNBT.put("id", NBT_BLOCK_ID);

            signData.getBlockPositionModifier().write(0, this.position);
            signData.getIntegers().write(0, ACTION_INDEX);
            signData.getNbtModifier().write(0, signNBT);

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, signData);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, openSign);
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
            inputs.put(player, this);
        }

        /**
         * closes the menu. if force is true, the menu will close and will ignore the reopen
         * functionality. false by default.
         *
         * @param player the player
         * @param force  decides whether it will reopen if reopen is enabled
         */
        public void close(Player player, boolean force) {
            this.forceClose = force;
            if (player.isOnline()) {
                player.closeInventory();
            }
        }

        public void close(Player player) {
            close(player, false);
        }

        private String color(String input) {
            return ChatColor.translateAlternateColorCodes('&', input);
        }
    }
}
