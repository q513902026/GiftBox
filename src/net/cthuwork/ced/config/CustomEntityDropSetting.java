package net.cthuwork.ced.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.cthuwork.ced.CustomEntityDrop;
import net.cthuwork.ced.core.DropData;
import net.cthuwork.ced.core.dropdata.EntityDropData;
import net.cthuwork.ced.core.dropdata.ExpDropData;
import net.cthuwork.ced.core.dropdata.ItemStackDropData;
import net.cthuwork.gifbox.config.PluginSetting;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEntityDropSetting extends PluginSetting
{
    public ConfigurationSection customDropSetting;
    public ConfigurationSection commandSetting;
    public List<LivingEntity> ignoreEntity = new ArrayList<>();
    
    public CustomEntityDropSetting(FileConfiguration config)
    {
        super(config);
        customDropSetting = config.getConfigurationSection("customEntityDrop.customDropSetting");
        commandSetting = config.getConfigurationSection("customEntityDrop.commandSetting");
    }
    public List<DropData> getRandomDropsFromLivingEntity(LivingEntity entity)
    {
        ConfigurationSection worldSetting = null;
        ConfigurationSection reviewWorldSetting = null;
        if (!customDropSetting.isConfigurationSection(entity.getWorld().getName()))
        {
            if (!customDropSetting.isConfigurationSection("all_unnamed_worlds"))
            {
                return null;
            }
            else
            {
                worldSetting = customDropSetting.getConfigurationSection("all_unnamed_worlds");
            }
        }
        else
        {
            worldSetting = customDropSetting.getConfigurationSection(entity.getWorld().getName());
            if (customDropSetting.isConfigurationSection("all_unnamed_worlds"))
            {
                reviewWorldSetting = customDropSetting.getConfigurationSection("all_unnamed_worlds");
            }
        }
        ConfigurationSection entitySetting = null;
        while (true)
        {
            if (!worldSetting.isConfigurationSection(entity.getType().toString() + ":" + entity.getCustomName()))
            {
                if (!worldSetting.isConfigurationSection(entity.getType().toString()))
                {
                    if (reviewWorldSetting == null)
                    {
                        return null;
                    }
                    else
                    {
                        worldSetting = reviewWorldSetting;
                        reviewWorldSetting = null;
                    }
                }
                else
                {
                    entitySetting = worldSetting.getConfigurationSection(entity.getType().toString());
                    break;
                }
            }
            else
            {
                entitySetting = worldSetting.getConfigurationSection(entity.getType().toString() + ":" + entity.getCustomName());
                break;
            }
        }
        if (!entitySetting.isList("dropGroups"))
        {
            return null;
        }
        List<?> dropGroupsSettingList = entitySetting.getList("dropGroups");
        int sumProbability = 0;
        Map<ArrayList<?>, Integer> dropGroupsMap = new HashMap<ArrayList<?>, Integer>();
        for (Object object : dropGroupsSettingList)
        {
            if (object instanceof Map)
            {
                Map<?, ?> dropGroupSetting = (Map<?, ?>) object;
                Object drops = dropGroupSetting.get("drops");
                Object probability = dropGroupSetting.get("probability");
                if (!(drops instanceof List))
                {
                    continue;
                }
                if (!(probability instanceof Integer) || (int) probability <= 0)
                {
                    continue;
                }
                sumProbability += (int) probability;
                dropGroupsMap.put((ArrayList<?>) drops, (int) probability);
            }
        }
        List<?> dropSettingList = null;
        if (sumProbability == 0)
        {
            return null;
        }
        int randomNumber = CustomEntityDrop.instance.random.nextInt(sumProbability);
        int currentProbability = 0;
        for (Entry<ArrayList<?>, Integer> entry : dropGroupsMap.entrySet())
        {
            currentProbability += entry.getValue();
            if (randomNumber < currentProbability)
            {
                dropSettingList = entry.getKey();
                break;
            }
        }
        List<DropData> dropList = new ArrayList<DropData>();
        for (Object object : dropSettingList)
        {
            if (object instanceof Map)
            {
                Map<?, ?> dropData = (Map<?, ?>) object;
                Object percentObj = dropData.get("percent");
                if (!(percentObj instanceof Number))
                {
                    continue;
                }
                double percent = ((Number) percentObj).doubleValue();
                if (percent < 0 || percent > 100)
                {
                    continue;
                }
                double randomPercent = CustomEntityDrop.instance.random
                        .nextDouble() * 100D;
                if (randomPercent > percent)
                {
                    continue;
                }
                itemstack:
                {
                    Object itemObj = dropData.get("item");
                    if (!(itemObj instanceof ItemStack))
                    {
                        break itemstack;
                    }
                    ItemStack item = (ItemStack) itemObj;
                    dropList.add(new ItemStackDropData(item));
                }
                exp:
                {
                    Object expObj = dropData.get("exp");
                    if (!(expObj instanceof Integer))
                    {
                        break exp;
                    }
                    int exp = (int) expObj;
                    dropList.add(new ExpDropData(exp));
                }
                /*monster:
                {
                    Object monsterObj = dropData.get("entity");
                    if (!(monsterObj instanceof ItemStack))
                    {
                        break monster;
                    }
                    String monster = (String) monsterObj;
                    dropList.add(new EntityDropData(monster));
                }*/
                
            }
        }
        return dropList;
    }
    public boolean isCustomDropEntity(LivingEntity entity)
    {
        if (customDropSetting.isConfigurationSection(entity.getWorld().getName()))
        {
            ConfigurationSection worldSetting = customDropSetting.getConfigurationSection(entity.getWorld().getName());
            if (worldSetting.isConfigurationSection(entity.getType().name() + ":" + entity.getCustomName()))
            {
                return true;
            }
            if (worldSetting.isConfigurationSection(entity.getType().name()))
            {
                return true;
            }
        }
        if (customDropSetting.isConfigurationSection("all_unnamed_worlds"))
        {
            ConfigurationSection worldSetting = customDropSetting
                    .getConfigurationSection("all_unnamed_worlds");
            if (worldSetting.isConfigurationSection(entity.getType().toString() + ":" + entity.getCustomName()))
            {
                return true;
            }
            if (worldSetting.isConfigurationSection(entity.getType().toString()))
            {
                return true;
            }
        }
        return false;
    }
    /*
    public List<DropData> getEntityDeathDrops(EntityDeathEvent event)
    {
        List<ItemStack> dropList = event.getDrops();
        List<DropData> dropDataList = new ArrayList<DropData>();
        for (ItemStack itemstack : dropList)
        {
            dropDataList.add(new ItemStackDropData(itemstack));
        }
        return dropDataList.isEmpty() ? null : dropDataList;
    }
    */
    public String getMainCommandUsage()
    {
        return commandSetting.getString("usage", "/<customEntityDrop|ced> [子命令]");
    }
    public String getMainCommandDescription()
    {
        return commandSetting.getString("description", "使用此命令来操作自定义掉落插件");
    }
    public String getCommandUsage(String commandName)
    {
        return commandSetting.getString(commandName + ".usage", "/<customEntityDrop|ced> <" + commandName + "> [参数[ ...]]");
    }
    public String getCommandDescription(String commandName)
    {
        return commandSetting.getString(commandName + ".description", "自定义掉落插件" + commandName + "指令");
    }
    public String getCommandPermission(String commandName)
    {
        return commandSetting.getString(commandName + ".permission", "");
    }
    public String getCommandPermissionMessage(String commandName)
    {
        return commandSetting.getString(commandName + ".permission-message", "");
    }
    @Override
    public void save()
    {
        CustomEntityDrop.instance.saveConfig();
    }
}
