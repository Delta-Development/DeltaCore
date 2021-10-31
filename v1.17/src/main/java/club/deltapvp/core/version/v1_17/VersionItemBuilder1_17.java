package club.deltapvp.core.version.v1_17;

import club.deltapvp.deltacore.api.utilities.builder.itembuilder.AbstractVersionItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class VersionItemBuilder1_17 extends AbstractVersionItemBuilder {
    @Override
    public void setUnbreakable(ItemStack itemStack, boolean b) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setUnbreakable(b);
        itemStack.setItemMeta(itemMeta);
    }
}
