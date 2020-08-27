package io.github.meonstudios.nomobgriefing.commands;

import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.github.meonstudios.nomobgriefing.MessageHelper;

public class ListCommand extends BaseCommand {

	public ListCommand(Plugin plugin) {
		super(plugin);
		name = "list";
		usage += "/nmg list";
		description += "Shows which mob griefing has been enabled or disabled.";
		permissionNode += name;
	}
	
	@Override
	public boolean execute(Player player, String[] args) {
		if (!super.execute(player, args)) {
			return false;
		}
		player.sendMessage(MessageHelper.prefix + "Below is a list of mobs and whether they can grief or not:");
		Set<String> mobs = plugin.getConfig().getConfigurationSection("mob").getKeys(false);
		for (String s : mobs) {
			String message = ChatColor.GOLD + WordUtils.capitalize(s.replace('_', ' ')) + ": ";
			if (plugin.getConfig().getBoolean("mob." + s)) {
				message += ChatColor.GREEN + "enabled";
			} else {
				message += ChatColor.RED + "disabled";
			}
			player.sendMessage(message);
		}
		return true;
	}	
}
