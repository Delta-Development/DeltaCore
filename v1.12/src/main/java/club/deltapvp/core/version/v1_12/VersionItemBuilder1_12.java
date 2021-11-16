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

import club.deltapvp.api.utilities.builder.itembuilder.AbstractVersionItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VersionItemBuilder1_12 extends AbstractVersionItemBuilder {
    @Override
    public void setUnbreakable(ItemStack item, boolean b) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(b);
        item.setItemMeta(itemMeta);
    }
}
