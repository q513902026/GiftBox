package net.cthuwork.giftbox.core.dropdata;

import net.cthuwork.giftbox.core.DropData;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemStackDropData extends DropData
{
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
        if (player.getInventory().getSize() == player.getInventory().getMaxStackSize())
        {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
        else
        {
            player.getInventory().addItem(itemStack);
            player.updateInventory();
        }
        if (itemStack.hasItemMeta())
        {
            player.sendMessage("[GiftBox]:恭喜你获得了" + itemStack.getItemMeta().getDisplayName() + itemStack.getAmount() + "件。");
        }
        else
        {
            player.sendMessage("[GiftBox]:恭喜你获得了" + itemStack.getType().toString() + itemStack.getAmount() + "件。");
        }
    }
}
