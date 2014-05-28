package net.cthuwork.giftbox.listener;

import java.util.List;

import net.cthuwork.giftbox.GiftBox;
import net.cthuwork.giftbox.core.DropData;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerRightClick(PlayerInteractEvent event)
    {
        final Player aPlayer = (Player) event.getPlayer();
        final Action aPlayerAction = (Action)event.getAction();
         ItemStack aPlayHandItemStack = aPlayer.getItemInHand();
        if ((aPlayerAction != Action.RIGHT_CLICK_BLOCK) && (aPlayerAction != Action.RIGHT_CLICK_AIR))
        {
            return;
        }
        if(!GiftBox.instance.setting.isGifBox(aPlayHandItemStack))
        {
            return;
        }
        List<DropData> dropList = GiftBox.instance.setting.getRandomDropFromItemStack(aPlayHandItemStack);
        if(dropList == null)
        {
            return;
        }
        for(DropData drop:dropList)
        {
            drop.processDrop(aPlayer);
        }
        aPlayHandItemStack.setType(Material.AIR);
        event.setCancelled(true);
        
    }
}
