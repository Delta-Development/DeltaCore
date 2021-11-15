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

package club.deltapvp.core.impl.version;

import club.deltapvp.deltacore.api.utilities.version.ServerVersion;
import club.deltapvp.deltacore.api.utilities.version.VersionChecker;
import org.bukkit.Bukkit;

public class iVersionChecker implements VersionChecker {

    private final ServerVersion serverVersion;

    public iVersionChecker() {
        serverVersion = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
    }

    @Override
    public boolean isModern() {
        return serverVersion.isAtLeast(ServerVersion.V1_13);
    }

    @Override
    public boolean isLegacy() {
        return serverVersion.isAtMost(ServerVersion.V1_12); // Could also return !isModern()
    }

    @Override
    public ServerVersion getVersion() {
        return serverVersion;
    }
}
