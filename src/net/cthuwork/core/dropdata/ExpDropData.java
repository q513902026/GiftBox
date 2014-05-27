package net.cthuwork.core.dropdata;

import net.cthuwork.core.DropData;
import net.cthuwork.core.DropData.DropType;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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
    public void processDrop(Player player)
    {
        ExperienceOrb experienceOrb = (ExperienceOrb) player.getWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);
        experienceOrb.setExperience(exp);
    }

}
