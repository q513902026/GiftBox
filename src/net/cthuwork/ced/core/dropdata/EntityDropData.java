package net.cthuwork.ced.core.dropdata;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.cthuwork.ced.core.DropData;

public class EntityDropData extends DropData
{
    private String entityName;
    private Entity entity2;
    public EntityDropData(String entityName)
    {
        super(DropType.MONSTER);
        this.entityName = entityName;
        
    }
    
    public Entity getEntity()
    {
        return entity2;
    }
    
    @Override
    public void processDrop(LivingEntity entity)
    {
       Entity entity2 = entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
       this.entity2 = entity2;
    }
}
