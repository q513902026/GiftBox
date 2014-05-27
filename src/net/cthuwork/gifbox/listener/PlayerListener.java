package net.cthuwork.gifbox.listener;

import java.util.List;

import net.cthuwork.gifbox.GifBox;
import net.cthuwork.gifbox.core.DropData;

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
        if(!GifBox.instance.setting.isGifBox(aPlayHandItemStack))
        {
            return;
        }
        List<DropData> dropList = GifBox.instance.setting.getRandomDropFromItemStack(aPlayHandItemStack);
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
