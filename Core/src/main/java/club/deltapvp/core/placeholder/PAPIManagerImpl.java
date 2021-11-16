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

package club.deltapvp.core.placeholder;

import club.deltapvp.api.DeltaPlugin;
import club.deltapvp.api.utilities.placeholder.PAPIManager;
import club.deltapvp.api.utilities.placeholder.PAPIPlaceholder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Deprecated
public class PAPIManagerImpl extends PAPIManager {

    private final HashMap<DeltaPlugin, ArrayList<PAPIPlaceholder>> registeredPlaceholders = new HashMap<>();

    public PAPIManagerImpl() {
        setInstance(this);
    }

    @Override
    public void registerPlaceholder(DeltaPlugin deltaPlugin, PAPIPlaceholder... papiPlaceholders) {
        ArrayList<PAPIPlaceholder> placeholders = registeredPlaceholders.get(deltaPlugin);
        if (placeholders == null) placeholders = new ArrayList<>();

        placeholders.addAll(Arrays.asList(papiPlaceholders));
    }

    @Override
    public String request(DeltaPlugin plugin, Player player, String[] params) {
        ArrayList<PAPIPlaceholder> placeholders = registeredPlaceholders.get(plugin);
        if (placeholders == null)
            return null;

        int checkingIndex = 0;
        Optional<PAPIPlaceholder> first = placeholders.stream().filter(papiPlaceholder -> {
            String identifier = papiPlaceholder.getIdentifier();
            int triggerOnArgument = papiPlaceholder.triggerOnArgument();
            return (checkingIndex == triggerOnArgument && params[checkingIndex].equalsIgnoreCase(identifier));
        }).findFirst();

        return (first.map(papiPlaceholder -> papiPlaceholder.request(player, params, checkingIndex)).orElse(null));
    }
}
