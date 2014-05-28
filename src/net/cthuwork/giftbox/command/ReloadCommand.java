package net.cthuwork.giftbox.command;

import java.util.List;

import net.cthuwork.giftbox.GiftBox;
import net.cthuwork.giftbox.config.GiftBoxSetting;

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
		GiftBox.instance.reloadConfig();

		GiftBox.instance.config = GiftBox.instance.getConfig();
		GiftBox.instance.setting = new GiftBoxSetting(GiftBox.instance.config);
		sender.sendMessage("§a[GifBox]§f配置文件已重新加轄!");
	}

	@Override
	public List<String> tabComplete(CommandSender sender,
			String caseSensitiveName, String[] args)
	{
		return null;
	}
}
