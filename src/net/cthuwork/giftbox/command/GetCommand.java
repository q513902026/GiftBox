package net.cthuwork.giftbox.command;

import java.util.List;

import net.cthuwork.giftbox.GiftBox;
import net.cthuwork.giftbox.core.DropData;
import net.cthuwork.giftbox.core.dropdata.ItemStackDropData;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetCommand extends BaseCommand
{
    public GetCommand()
    {
        super("get", "g");
    }
    @Override
    public void runCommand(CommandSender sender, String caseSensitiveName, String[] args)
    {
        if (args.length != 1)
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
        final String key = args[0];
        List<String> giftKeys = GiftBox.instance.setting.getGiftKeys();
        if (!giftKeys.contains(key))
        {
            aPlayer.sendMessage(ChatColor.RED + "[GiftBox|System]不存在的ChestKey值");
            return;
        }
        DropData drop = new ItemStackDropData(GiftBox.instance.setting.getItemStackFromChestKey(key));
        drop.processDrop(aPlayer);
    }
    @Override
    public List<String> tabComplete(CommandSender sender, String caseSensitiveName, String[] args)
    {
        // TODO 自动生成的方法存根
        return null;
    }
}
