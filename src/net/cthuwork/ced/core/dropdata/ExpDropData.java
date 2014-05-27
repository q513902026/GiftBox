package net.cthuwork.ced.core.dropdata;

import net.cthuwork.ced.core.DropData;
import net.cthuwork.ced.core.DropData.DropType;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;

public class ExpDropData extends DropData {

    private int exp;
    
    public ExpDropData(int exp)
    {
        super(DropType.EXP);
        this.exp = exp;
    }

    public int getExp()
    {
        return exp;
    }
    @Override
    public void processDrop(LivingEntity entity)
    {
        ExperienceOrb experienceOrb = (ExperienceOrb) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.EXPERIENCE_ORB);
        experienceOrb.setExperience(exp);
    }

}
