package net.cthuwork.ced.core.dropdata;

import net.cthuwork.ced.core.DropData;
import net.cthuwork.ced.core.DropData.DropType;

import org.bukkit.entity.LivingEntity;
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
    public void processDrop(LivingEntity entity)
    {
    	entity.getWorld().dropItem(entity.getLocation(), itemStack);
    }
}
