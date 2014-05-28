package net.cthuwork.giftbox;

import java.util.Random;

import net.cthuwork.giftbox.command.CommandReceiver;
import net.cthuwork.giftbox.command.HelpCommand;
import net.cthuwork.giftbox.command.ReloadCommand;
import net.cthuwork.giftbox.config.GiftBoxSetting;
import net.cthuwork.giftbox.listener.PlayerListener;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GiftBox extends JavaPlugin
{
    public static GiftBox instance;
    public Random random;
    public GiftBoxSetting setting;
    public PlayerListener playListener;
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
        setting = new GiftBoxSetting(config);
        playListener = new PlayerListener();
        mainCommand = this.getCommand("GiftBox");
        commandReceiver = new CommandReceiver();
        
        mainCommand.setDescription(setting.getMainCommandDescription());
        mainCommand.setExecutor(commandReceiver);
        mainCommand.setUsage(setting.getMainCommandUsage());
        
        commandReceiver.registerCommand(new HelpCommand());
        commandReceiver.registerCommand(new ReloadCommand());
        
        this.getServer().getPluginManager().registerEvents(playListener, this);     
    }
    @Override
    public void onDisable()
    {
        instance = null;
    }
}
