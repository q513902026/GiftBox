package net.cthuwork.gifbox;

import java.util.Random;

import net.cthuwork.gifbox.command.CommandReceiver;
import net.cthuwork.gifbox.command.HelpCommand;
import net.cthuwork.gifbox.command.ReloadCommand;
import net.cthuwork.gifbox.config.GifBoxSetting;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GifBox extends JavaPlugin
{
    public static GifBox instance;
    
    public Random random;
    public GifBoxSetting setting;
    public FileConfiguration config;
    public PluginCommand mainCommand;
    public CommandReceiver commandReceiver;

    @Override
    public void onEnable() 
    {
        
        instance = this;
        this.saveDefaultConfig();
        
        random = new Random();
        config = this.getConfig();
        setting = new GifBoxSetting(config);
        mainCommand = this.getCommand("CustomEntityDrop");
        commandReceiver = new CommandReceiver();
        
        mainCommand.setDescription(setting.getMainCommandDescription());
        mainCommand.setExecutor(commandReceiver);
        mainCommand.setUsage(setting.getMainCommandUsage());
        
        commandReceiver.registerCommand(new HelpCommand());
        commandReceiver.registerCommand(new ReloadCommand());
        
    }
    
    @Override
    public void onDisable()
    {
        instance = null;
    }
}
