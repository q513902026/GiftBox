package net.cthuwork.gifbox.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import net.cthuwork.gifbox.GifBox;
import net.cthuwork.gifbox.config.PluginSetting;
import net.cthuwork.gifbox.core.DropData;
import net.cthuwork.gifbox.core.dropdata.ExpDropData;
import net.cthuwork.gifbox.core.dropdata.ItemStackDropData;

public class GifBoxSetting extends PluginSetting
{
    public ConfigurationSection commandSetting;
    public ConfigurationSection gifBoxData;
    public ConfigurationSection gifBox;
    
    public boolean customDropSystem = false;
    
    public GifBoxSetting(FileConfiguration config)
    {
        super(config);
        customDropSystem = config.getBoolean("isCustomEntityDrop");
        gifBoxData = config.getConfigurationSection("gifBox.gifBoxData");
        gifBox = config.getConfigurationSection("gifBox.gifBox");
        commandSetting = config.getConfigurationSection("gifBox.commandSetting");
    }
    
    @SuppressWarnings("unused")
    public List<DropData> getRandomDropFromItemStack(ItemStack itemstack)
    {
        ConfigurationSection boxSetting = null;
        if (!(gifBox.isConfigurationSection(itemstack.getType().toString())))
        {
            return null;
        }
        else
        {
            boxSetting = gifBox.getConfigurationSection(itemstack.getType().toString());
        }
        if (!boxSetting.isList("dropGroup"))
        {
            return null;
        }
        List<?> dropGroupsSettingList = boxSetting.getList("dropGroups");
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
        int randomNumber = GifBox.instance.random.nextInt(sumProbability);
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
        List<String> dropKeyList = new ArrayList<String>();
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
                double randomPercent = GifBox.instance.random.nextDouble() * 100D;
                if (randomPercent > percent)
                {
                    continue;
                }
                Object dropDataKeyObj = dropData.get("itemKey");
                if (!(dropDataKeyObj instanceof String))
                {
                    break;
                }
                String dropDataKey = (String) dropDataKeyObj;
                dropKeyList.add(dropDataKey);
            }
        }
        if (dropKeyList == null)
        {
            return null;
        }
        List<List<DropData>> boxDropData = new ArrayList<>();
        for (String key : dropKeyList)
        {
            boxDropData.add(getGifBoxData(key));
        }
        for (List<DropData> list : boxDropData)
        {
            for (DropData dd : list)
            {
                dropList.add(dd);
            }
        }
        return dropList;
    }
    public List<DropData> getGifBoxData(String itemKey)
    {
        ConfigurationSection boxDataSetting = null;
        if (!(gifBoxData.isConfigurationSection(itemKey)))
        {
            return null;
        }
        boxDataSetting = gifBoxData.getConfigurationSection(itemKey);
        if (!boxDataSetting.isList("dropGroup"))
        {
            return null;
        }
        List<?> dropGroupsSettingList = boxDataSetting.getList("dropGroups");
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
        int randomNumber = GifBox.instance.random.nextInt(sumProbability);
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
                double randomPercent = GifBox.instance.random.nextDouble() * 100D;
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
            }
        }
        return dropList;
    }
    public boolean isGifBox(ItemStack itemstack)
    {
        ConfigurationSection boxSetting = null;
        if (gifBox.isConfigurationSection(itemstack.getType().toString()))
        {
            boxSetting = gifBox.getConfigurationSection(itemstack.getType().toString());
            Object itemObj = boxSetting.get("item");
            if (itemObj instanceof ItemStack)
            {
                ItemStack item = (ItemStack) itemObj;
                return item.equals(itemstack);
            }
        }
        return false;
    }
    
    @Override
    public void save()
    {
        GifBox.instance.saveConfig();
    }
    public String getMainCommandUsage()
    {
        return commandSetting.getString("usage", "/<GifBox|gb> [子命令]");
    }
    public String getMainCommandDescription()
    {
        return commandSetting.getString("description", "使用此命令来操作礼物盒子插件");
    }
    public String getCommandUsage(String commandName)
    {
        return commandSetting.getString(commandName + ".usage", "/<GifBox|gb> <" + commandName + "> [参数[ ...]]");
    }
    public String getCommandDescription(String commandName)
    {
        return commandSetting.getString(commandName + ".description", "礼物盒子" + commandName + "指令");
    }
    public String getCommandPermission(String commandName)
    {
        return commandSetting.getString(commandName + ".permission", "");
    }
    public String getCommandPermissionMessage(String commandName)
    {
        return commandSetting.getString(commandName + ".permission-message", "");
    }
}
