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

package club.deltapvp.core;

import club.deltapvp.core.impl.api.DeltaAPIImpl;
import club.deltapvp.core.impl.api.DeltaItemBuilderAPI;
import club.deltapvp.core.impl.api.DeltaSQLAPIImpl;
import club.deltapvp.core.impl.event.CustomPlayerEventListener;
import club.deltapvp.core.impl.gui.GUIListener;
import club.deltapvp.core.impl.hologram.HologramListener;
import club.deltapvp.core.impl.shortcommands.ShortCommandsListener;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.DeltaPlugin;
import lombok.Getter;
import lombok.Setter;

public final class Core extends DeltaPlugin {

    @Getter
    @Setter
    private static Core instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        new DeltaAPIImpl();
        new DeltaSQLAPIImpl();
        new DeltaItemBuilderAPI();
        registerListeners(
                new GUIListener(),
                new HologramListener(),
                new CustomPlayerEventListener(),
                new ShortCommandsListener()
        );

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DeltaAPI api = DeltaAPI.getInstance();
        api.getHologramManager().getHolograms().forEach(hologram -> api.getHologramManager().removeHologram(hologram));
    }

}
