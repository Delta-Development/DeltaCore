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

package club.deltapvp.core.impl.event;

import club.deltapvp.deltacore.api.utilities.event.items.damage.PlayerDamageByEntityEvent;
import club.deltapvp.deltacore.api.utilities.event.items.damage.PlayerDamageByPlayerEvent;
import club.deltapvp.deltacore.api.utilities.event.items.damage.PlayerDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomPlayerEventListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player))
            return;

        EntityDamageEvent.DamageCause cause = event.getCause();

        Player player = (Player) entity;
        PlayerDamageEvent e = new PlayerDamageEvent(player, cause);
        e.setDamage(event.getDamage());

        PlayerDamageEvent called = (PlayerDamageEvent) e.call();
        if (called.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        double damage = called.getDamage();
        event.setDamage(damage);

    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Entity damager = event.getDamager();

        EntityDamageEvent.DamageCause cause = event.getCause();
        double damage = event.getDamage();

        if (!(damager instanceof Player)) {
            PlayerDamageByEntityEvent e = new PlayerDamageByEntityEvent(player, damager, cause);
            e.setDamage(damage);
            PlayerDamageByEntityEvent call = (PlayerDamageByEntityEvent) e.call();
            if (call.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            event.setDamage(call.getDamage());
        } else {
            Player attacker = (Player) damager;
            PlayerDamageByPlayerEvent e = new PlayerDamageByPlayerEvent(player, attacker, cause);
            e.setDamage(damage);
            PlayerDamageByPlayerEvent call = (PlayerDamageByPlayerEvent) e.call();
            if (call.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            event.setDamage(call.getDamage());
        }
    }
}
