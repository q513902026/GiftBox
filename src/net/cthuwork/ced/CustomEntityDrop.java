package net.cthuwork.ced;

import java.util.Random;

import net.cthuwork.ced.config.CustomEntityDropSetting;
import net.cthuwork.ced.listener.EntityListener;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomEntityDrop extends JavaPlugin 
{
	public static CustomEntityDrop instance;

	public Random random;
	public EntityListener entityListener;
	public FileConfiguration config;
	public CustomEntityDropSetting setting;

	@Override
	public void onEnable() 
	{
		instance = this;

		this.saveDefaultConfig();

		random = new Random();
		config = this.getConfig();
		setting = new CustomEntityDropSetting(config);
		entityListener = new EntityListener();
		
		this.getServer().getPluginManager().registerEvents(entityListener, this);
	}

	@Override
	public void onDisable() 
	{
		instance = null;
	}

}
