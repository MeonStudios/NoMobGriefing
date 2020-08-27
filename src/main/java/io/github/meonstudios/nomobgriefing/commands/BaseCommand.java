package io.github.meonstudios.nomobgriefing.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.github.meonstudios.nomobgriefing.MessageHelper;

public class BaseCommand {

	protected Plugin plugin;
	protected String name;
	protected String usage;
	protected String description;
	protected String permissionNode;
	protected int minArgs;
	
	public BaseCommand(Plugin plugin) {
		this.plugin = plugin;
		name = "base";
		usage = "";
		description = ChatColor.WHITE + "";
		permissionNode = "nmg.";
		minArgs = 0;
	}
	
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permissionNode)) {
			player.sendMessage(MessageHelper.prefix + ChatColor.RED + "You do not have permission to perform this command.");
			return false;
		}
		if (args.length < minArgs) {
			wrongUsage(player);
			return false;
		}
		return true;
	}
	
	public List<String> onTabComplete(Player player, String[] args) {
		return null;
	}
	
	public void wrongUsage(Player player) {
		player.sendMessage(MessageHelper.prefix + ChatColor.RED + "Not enough arguments, " 
				+ ChatColor.GRAY + "usage: " + ChatColor.GOLD + usage);
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPermissionNode() {
		return permissionNode;
	}
	
	public int getMinArgs() {
		return minArgs;
	}
}
