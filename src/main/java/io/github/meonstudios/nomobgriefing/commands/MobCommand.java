package io.github.meonstudios.nomobgriefing.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.github.meonstudios.nomobgriefing.MessageHelper;

public class MobCommand extends BaseCommand {

	public MobCommand(String mobName, String description, Plugin plugin) {
		super(plugin);
		name = mobName;
		usage += "/nmg " + mobName + " [enable|disable]";
		this.description += description;
		permissionNode += name;
		minArgs = 2;
	}
	
	@Override
	public boolean execute(Player player, String[] args) {
		if(!super.execute(player, args)) {
			return false;
		}
		
		String message = MessageHelper.prefix + WordUtils.capitalize(name) + " griefing has been ";
		
		if (args[1].equalsIgnoreCase("enable")) {
			player.sendMessage(message + ChatColor.GREEN + "enabled");
			plugin.getConfig().set("mob." + name, true);
		} else if (args[1].equalsIgnoreCase("disable")) {
			player.sendMessage(message + ChatColor.RED + "disabled");
			plugin.getConfig().set("mob." + name, false);
		} else {
			wrongUsage(player);
		}
		
		plugin.saveConfig();
		return true;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		List<String> strings = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		strings.add("enable");
		strings.add("disable");
		for (String s : strings) {
			if (s.startsWith(args[1].toLowerCase()))
				result.add(s);
		}
		return result;
	}
}
