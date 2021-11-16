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

package club.deltapvp.core.hologram;

import club.deltapvp.api.utilities.hologram.Hologram;
import club.deltapvp.api.utilities.hologram.HologramManager;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;

public class HologramManagerProvider implements HologramManager {

    private final ArrayList<Hologram> holograms = new ArrayList<>();

    @Override
    public Hologram createHologram(Location location, int i, String... strings) {
        if (getHologram(i) != null)
            return null;

        HologramProvider hologram = new HologramProvider(location, strings);
        hologram.setIntID(i);

        holograms.add(hologram);

        return hologram;
    }

    @Override
    public Hologram createHologram(Location location, String s, String... strings) {
        if (getHologram(s) != null)
            return null;

        HologramProvider hologram = new HologramProvider(location, strings);
        hologram.setStringID(s);

        holograms.add(hologram);

        return hologram;
    }

    @Override
    public Hologram createHologram(Location location, int i, double v, String... strings) {
        if (getHologram(i) != null)
            return null;

        HologramProvider hologram = new HologramProvider(location, strings);
        hologram.setIntID(i);
        hologram.setSpaceInBetween(v);

        holograms.add(hologram);

        return hologram;
    }

    @Override
    public Hologram createHologram(Location location, String s, double v, String... strings) {
        if (getHologram(s) != null)
            return null;

        HologramProvider hologram = new HologramProvider(location, strings);
        hologram.setStringID(s);
        hologram.setSpaceInBetween(v);

        holograms.add(hologram);

        return hologram;
    }

    @Override
    public Hologram getHologram(String s) {
        return holograms.stream()
                .filter(hologram -> hologram.getStringID() != null)
                .filter(hologram -> hologram.getStringID().equalsIgnoreCase(s))
                .findFirst().orElse(null);
    }

    @Override
    public Hologram getHologram(int i) {
        return holograms.stream()
                .filter(hologram -> hologram.getIntID() != -1)
                .filter(hologram -> hologram.getIntID() == i)
                .findFirst().orElse(null);
    }

    @Override
    public Hologram getHologram(Location location) {
        return holograms.stream().filter(hologram -> {
            if (hologram.getLocation().equals(location))
                return true;

            ArmorStand as = hologram.getEntities().stream().filter(armorStand -> armorStand.getLocation().equals(location))
                    .findFirst().orElse(null);
            return as != null;
        }).findFirst().orElse(null);
    }

    @Override
    public Hologram getHologram(ArmorStand armorStand) {
        return holograms.stream().filter(hologram -> hologram.getEntities().contains(armorStand)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Hologram> getHolograms() {
        return holograms;
    }

    @Override
    public void removeHologram(int i) {
        Hologram hologram = getHologram(i);
        if (hologram == null)
            return;

        hologram.remove();
        holograms.remove(hologram);
    }

    @Override
    public void removeHologram(String s) {
        Hologram hologram = getHologram(s);
        if (hologram == null)
            return;

        hologram.remove();
        holograms.remove(hologram);
    }

    @Override
    public void removeHologram(Hologram hologram) {
        hologram.remove();
        holograms.remove(hologram);
    }
}
