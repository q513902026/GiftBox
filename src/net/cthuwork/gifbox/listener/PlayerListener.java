package net.cthuwork.gifbox.listener;

import java.util.List;

import net.cthuwork.core.DropData;
import net.cthuwork.gifbox.GifBox;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener
{
    @EventHandler
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
        aPlayHandItemStack.setType(Material.AIR);
        event.setCancelled(true);
        
    }
}
