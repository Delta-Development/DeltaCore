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

package club.deltapvp.core.version.v1_14.hologram;

import club.deltapvp.api.DeltaPlugin;
import club.deltapvp.api.utilities.DeltaUtils;
import club.deltapvp.api.utilities.hologram.v2.Hologram;
import club.deltapvp.api.utilities.hologram.v2.backend.AbstractHologramHandler;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class HologramHandler1_14 extends AbstractHologramHandler {

    private static final HashMap<Hologram, LinkedList<EntityArmorStand>> hologramEntities = new HashMap<>();
    private final HashMap<Hologram, LinkedList<String>> holograms = new HashMap<>();
    private final DeltaPlugin plugin;

    public HologramHandler1_14(DeltaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new HologramPlayerListener(plugin), plugin);
    }

    public static Optional<Hologram> getByID(int id) {
        Optional<Map.Entry<Hologram, LinkedList<EntityArmorStand>>> first = hologramEntities.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(armorStand -> armorStand.getId() == id))
                .findFirst();

        if (!first.isPresent())
            return Optional.empty();

        Map.Entry<Hologram, LinkedList<EntityArmorStand>> entry = first.get();
        return Optional.of(entry.getKey());
    }

    @Override
    public void create(Player player, Hologram hologram, boolean all) {
        if (!holograms.containsKey(hologram)) {
            LinkedList<String> lines = new LinkedList<>(hologram.lines());
            holograms.put(hologram, lines);
        }
        update(player, hologram, all);
    }

    @Override
    public void remove(Player player, Hologram hologram, boolean all) {
        LinkedList<EntityArmorStand> entityArmorStands = hologramEntities.get(hologram);
        if (entityArmorStands == null)
            return;

        if (!all) {
            entityArmorStands.forEach(armorStand -> {
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armorStand.getId());
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(p -> entityArmorStands.forEach(armorStand -> {
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armorStand.getId());
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }));
        }
    }

    @Override
    public void update(Player player, Hologram hologram, boolean all) {
        remove(player, hologram, all);


        LinkedList<String> lines = holograms.get(hologram);
        if (lines == null) {
            create(player, hologram, all);
            return;
        }
        LinkedList<EntityArmorStand> entities = new LinkedList<>();
        double space = .25;

        Location location = hologram.location().clone();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle();
        for (String line : lines) {
            EntityArmorStand armorStand = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());
            armorStand.setNoGravity(false);
            armorStand.setArms(false);
            armorStand.setBasePlate(false);
            armorStand.setSmall(true);
            armorStand.setInvisible(true);

            ChatComponentText name = new ChatComponentText(DeltaUtils.color(line));
            armorStand.setCustomName(name);
            armorStand.setCustomNameVisible(true);

            location.subtract(0, space, 0);

            entities.add(armorStand);
        }

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (EntityArmorStand armorStand : entities) {
            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armorStand);
            if (!all)
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            else
                onlinePlayers.forEach(p -> ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet));
        }

        if (hologramEntities.containsKey(hologram))
            hologramEntities.replace(hologram, entities);
        else
            hologramEntities.put(hologram, entities);

    }
}
