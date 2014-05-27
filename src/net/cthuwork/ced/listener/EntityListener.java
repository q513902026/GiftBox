package net.cthuwork.ced.listener;

import java.util.List;

import net.cthuwork.ced.CustomEntityDrop;
import net.cthuwork.ced.core.DropData;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event)
    {
        if (event.getEntityType() == EntityType.PLAYER)
        {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (!CustomEntityDrop.instance.setting.isCustomDropEntity(entity))
        {
            return;
        }
        List<DropData> drops = CustomEntityDrop.instance.setting.getRandomDropsFromLivingEntity(entity);
        if (drops == null)
        {
            return;
        }
        for (DropData dropData : drops)
        {
            dropData.processDrop(entity);
        }
    }
    
}
