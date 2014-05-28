package net.cthuwork.giftbox.core.dropdata;

import net.cthuwork.giftbox.core.DropData;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemStackDropData extends DropData {

    private ItemStack itemStack;
    
    public ItemStackDropData(ItemStack itemStack)
    {
        super(DropType.ITEM_STACK);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }
    
    @Override
    public void processDrop(Player player)
    {
        player.getInventory().addItem(itemStack);
    }
}
