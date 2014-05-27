package net.cthuwork.gifbox.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import net.cthuwork.gifbox.GifBox;
import net.cthuwork.gifbox.config.PluginSetting;

public class GifBoxSetting extends PluginSetting
{
    public ConfigurationSection commandSetting;
    public ConfigurationSection gifBoxData;
    public ConfigurationSection gifBox;
    
    public GifBoxSetting(FileConfiguration config)
    {
        super(config);
        gifBoxData = config.getConfigurationSection("gifBox.gifBoxData");
        gifBox = config.getConfigurationSection("gifBox.gifBox");
        commandSetting = config.getConfigurationSection("gifBox.commandSetting");
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
