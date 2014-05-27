package net.cthuwork.gifbox.command;

import java.util.List;

import net.cthuwork.gifbox.GifBox;
import net.cthuwork.gifbox.config.GifBoxSetting;

import org.bukkit.command.CommandSender;

public class ReloadCommand extends BaseCommand
{
	public ReloadCommand()
	{
		super("reload", "r");
	}

	@Override
	public void runCommand(CommandSender sender, String caseSensitiveName,
			String[] args)
	{
		if (args.length != 0)
		{
			PrintUsage(sender);
			return;
		}
		GifBox.instance.reloadConfig();

		GifBox.instance.config = GifBox.instance.getConfig();
		GifBox.instance.setting = new GifBoxSetting(GifBox.instance.config);
		sender.sendMessage("§a[GifBox]§f配置文件已重新加轄!");
	}

	@Override
	public List<String> tabComplete(CommandSender sender,
			String caseSensitiveName, String[] args)
	{
		return null;
	}
}
