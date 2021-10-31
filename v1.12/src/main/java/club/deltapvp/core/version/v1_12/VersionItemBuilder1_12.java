package club.deltapvp.core.version.v1_12;

import club.deltapvp.deltacore.api.utilities.builder.itembuilder.AbstractVersionItemBuilder;
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