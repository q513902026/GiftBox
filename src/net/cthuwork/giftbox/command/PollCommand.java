package net.cthuwork.giftbox.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PollCommand extends BaseCommand
{
    public PollCommand()
    {
        super("poll", "p");
        // TODO 自动生成的构造函数存根
    }
    @Override
    public void runCommand(CommandSender sender, String caseSensitiveName, String[] args)
    {
        if (args.length != 0)
        {
            PrintUsage(sender);
            return;
        }
        if (!(sender instanceof Player))
        {
            PrintUsage(sender);
            return;
        }
        Player aPlayer = (Player) sender;
        aPlayer.sendMessage(ChatColor.RED + "[GiftBox|System] :" + aPlayer.getItemInHand().getType().toString());
    }
    @Override
    public List<String> tabComplete(CommandSender sender, String caseSensitiveName, String[] args)
    {
        // TODO 自动生成的方法存根
        return null;
    }
}
