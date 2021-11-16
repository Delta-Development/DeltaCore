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

package club.deltapvp.core.sign;

import club.deltapvp.core.DeltaCorePlugin;
import club.deltapvp.core.version.v1_13.SignMenuFactory1_13;
import club.deltapvp.core.version.v1_16.SignMenuFactory1_16;
import club.deltapvp.core.version.v1_8_8.SignMenuFactory1_8;
import club.deltapvp.api.utilities.sign.AbstractSignFactory;
import club.deltapvp.api.utilities.sign.AbstractSignMenu;
import club.deltapvp.api.utilities.sign.VirtualSignEditor;
import club.deltapvp.api.utilities.version.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiPredicate;

public class VirtualSignEditorProvider implements VirtualSignEditor {

    private AbstractSignFactory signFactory;

    public VirtualSignEditorProvider() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib"))
            return;

        ServerVersion version = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
        DeltaCorePlugin plugin = DeltaCorePlugin.getInstance();
        if (version.equals(ServerVersion.V1_8))
            signFactory = new SignMenuFactory1_8(plugin);
        if (version.equals(ServerVersion.V1_13))
            signFactory = new SignMenuFactory1_13(plugin);
        else
            signFactory = new SignMenuFactory1_16(plugin);
    }


    @Override
    public AbstractSignMenu createSign(List<String> list) {
        return signFactory.newMenu(list);
    }

    @Override
    public AbstractSignMenu createSign(List<String> list, boolean b) {
        return createSign(list).reOpenIfFail(b);
    }

    @Override
    public AbstractSignMenu createSign(List<String> list, boolean b, BiPredicate<Player, String[]> biPredicate) {
        return createSign(list, b).response(biPredicate);
    }

    @Override
    public AbstractSignMenu createSign(List<String> list, BiPredicate<Player, String[]> biPredicate) {
        return signFactory.newMenu(list).response(biPredicate);
    }
}
