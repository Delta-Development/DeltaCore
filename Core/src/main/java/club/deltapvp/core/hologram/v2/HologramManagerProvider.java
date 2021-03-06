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

package club.deltapvp.core.hologram.v2;

import club.deltapvp.core.DeltaCorePlugin;
import club.deltapvp.core.version.v1_11.hologram.HologramHandler1_11;
import club.deltapvp.core.version.v1_12.hologram.HologramHandler1_12;
import club.deltapvp.core.version.v1_13.hologram.HologramHandler1_13;
import club.deltapvp.core.version.v1_14.hologram.HologramHandler1_14;
import club.deltapvp.core.version.v1_15.hologram.HologramHandler1_15;
import club.deltapvp.core.version.v1_16.hologram.HologramHandler1_16;
import club.deltapvp.core.version.v1_17.hologram.HologramHandler1_17;
import club.deltapvp.core.version.v1_8_8.hologram.HologramHandler1_8;
import club.deltapvp.api.DeltaPlugin;
import club.deltapvp.api.utilities.hologram.v2.Hologram;
import club.deltapvp.api.utilities.hologram.v2.backend.AbstractHologramHandler;
import club.deltapvp.api.utilities.hologram.v2.backend.HologramManager;
import club.deltapvp.api.utilities.version.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class HologramManagerProvider extends HologramManager {

    private final HashMap<DeltaPlugin, List<Hologram>> registeredHolograms = new HashMap<>();
    private AbstractHologramHandler handler = null;

    public HologramManagerProvider() {
        setInstance(this);

        ServerVersion version = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
        DeltaCorePlugin plugin = DeltaCorePlugin.getInstance();
        if (version.equals(ServerVersion.V1_8))
            handler = new HologramHandler1_8(plugin);
        if (version.equals(ServerVersion.V1_11))
            handler = new HologramHandler1_11(plugin);
        if (version.equals(ServerVersion.V1_12))
            handler = new HologramHandler1_12(plugin);
        if (version.equals(ServerVersion.V1_13))
            handler = new HologramHandler1_13(plugin);
        if (version.equals(ServerVersion.V1_14))
            handler = new HologramHandler1_14(plugin);
        if (version.equals(ServerVersion.V1_15))
            handler = new HologramHandler1_15(plugin);
        if (version.equals(ServerVersion.V1_16))
            handler = new HologramHandler1_16(plugin);
        if (version.equals(ServerVersion.V1_17))
            handler = new HologramHandler1_17(plugin);

        if (handler == null)
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
    }

    @Override
    public void registerHologram(DeltaPlugin deltaPlugin, Hologram hologram) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        List<Hologram> holograms = registeredHolograms.get(deltaPlugin);
        if (holograms == null)
            holograms = new ArrayList<>();

        if (holograms.contains(hologram))
            return;

        holograms.add(hologram);

        if (registeredHolograms.containsKey(deltaPlugin))
            registeredHolograms.replace(deltaPlugin, holograms);
        else
            registeredHolograms.put(deltaPlugin, holograms);
    }

    @Override
    public void updateHologram(String handle, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        Optional<Hologram> hologram = getHologram(handle);
        if (!hologram.isPresent())
            return;

        handler.update(player, hologram.get(), all);
    }

    @Override
    public void updateHologram(Hologram hologram, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        handler.update(player, hologram, all);
    }

    @Override
    public void removeHologram(String handle, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }
        Optional<Hologram> hologram = getHologram(handle);
        if (!hologram.isPresent())
            return;

        handler.remove(player, hologram.get(), all);
    }

    @Override
    public void removeHologram(Hologram hologram, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        handler.remove(player, hologram, all);
    }

    Optional<Hologram> getHologram(String handle) {
        Optional<Map.Entry<DeltaPlugin, List<Hologram>>> first = registeredHolograms.entrySet()
                .stream()
                .filter(deltaPluginListEntry -> deltaPluginListEntry.getValue()
                        .stream()
                        .anyMatch(hologram -> hologram.handle().equalsIgnoreCase(handle)))
                .findFirst();

        if (!first.isPresent())
            return Optional.empty();

        Map.Entry<DeltaPlugin, List<Hologram>> e = first.get();
        return (e.getValue().stream().filter(hologram1 -> hologram1.handle().equalsIgnoreCase(handle)).findFirst());
    }

    @Override
    public List<Hologram> getHolograms(DeltaPlugin plugin) {
        List<Hologram> holograms = registeredHolograms.get(plugin);
        return (holograms == null ? Collections.emptyList() : holograms);
    }
}
