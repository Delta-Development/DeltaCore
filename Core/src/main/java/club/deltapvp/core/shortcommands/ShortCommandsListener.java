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

package club.deltapvp.core.shortcommands;

import club.deltapvp.api.commands.shortcommands.ShortCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class ShortCommandsListener implements Listener {

    private final ShortCommands shortCommands;

    public ShortCommandsListener() {
        new ShortCommandsProvider();

        shortCommands = ShortCommands.getInstance();
    }

    @EventHandler
    public void onCommandInput(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        message = message.replaceFirst("/", "");
        String[] args = message.split(" ");
        String cmd = args[0];
        shortCommands.getCommand(cmd).ifPresent(command -> {
            if (event.isCancelled())
                return;

            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            command.execute(event.getPlayer(), null, newArgs);

            event.setCancelled(true);
        });

        shortCommands.getSubCommand(cmd).ifPresent(subCommand -> {
            if (event.isCancelled())
                return;

            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            subCommand.execute(event.getPlayer(), newArgs);

            event.setCancelled(true);
        });
    }

}
