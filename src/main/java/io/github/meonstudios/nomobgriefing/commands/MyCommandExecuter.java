package io.github.meonstudios.nomobgriefing.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.github.meonstudios.nomobgriefing.MessageHelper;

public class MyCommandExecuter implements CommandExecutor, TabCompleter {
	
	private final Plugin plugin;
	LinkedHashMap<String, BaseCommand> commands;
	
	public MyCommandExecuter(Plugin plugin) {
		this.plugin = plugin;
		commands = new LinkedHashMap<String, BaseCommand>();
		commands.put("list", new ListCommand(plugin));
		commands.put("creeper", new MobCommand("creeper", "Enables/disables creepers exploding blocks.", plugin));
		commands.put("door", new MobCommand("door", "Enables/disables zombies breaking doors.", plugin));
		commands.put("enderdragon", new MobCommand("enderdragon", "Enables/disables the enderdragon destroying blocks.", plugin));
		commands.put("enderman", new MobCommand("enderman", "Enables/disables endermen picking up blocks.", plugin));
		commands.put("ghast", new MobCommand("ghast", "Enables/disables ghasts exploding blocks.", plugin));
		commands.put("rabbit", new MobCommand("rabbit", "Enables/disables rabbits eating crops.", plugin));
		commands.put("ravager", new MobCommand("ravager", "Enables/disables ravagers from destroying blocks.", plugin));
		commands.put("sheep", new MobCommand("sheep", "Enables/disables sheep eating grass blocks.", plugin));
		commands.put("silverfish", new MobCommand("silverfish", "Enables/disables silverfish from entering and leaving (breaking) blocks.", plugin));
		commands.put("snowgolem", new MobCommand("snowgolem", "Enables/disables snowgolems creating snowblocks.", plugin));
		commands.put("tnt", new MobCommand("tnt", "Enables/disables tnt from destroying blocks.", plugin));
		commands.put("tntminecart", new MobCommand("tntminecart", "Enables/disables tnt minecarts from destroying blocks.", plugin));
		commands.put("trampling", new MobCommand("trampling", "Enables/disables mobs from trampling farmland.", plugin));
		commands.put("turtle_egg", new MobCommand("turtle_egg", "Enables/disables zombies breaking turtle eggs.", plugin));
		commands.put("wither", new MobCommand("wither", "Enables/disables withers destroying blocks.", plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		// Check if the player ran a command for our plugin
		if (cmd.getName().equalsIgnoreCase("nmg")) {
			// Only players can use this plugin
			if (!(sender instanceof Player)) {
				return true;
			}
			
			Player player = (Player) sender;
			
			// Base command with no arguments gives an explanation for the plugin
			if (args.length == 0) {
				SendInfoToPlayer(player);
				return true;
			}
			
			if (commands.containsKey(args[0].toLowerCase())){
				commands.get(args[0]).execute(player, args);
			} else {
				player.sendMessage(MessageHelper.prefix + ChatColor.RED + "This is not a supported command, use /nmg for a list of commands.");
			}
		}
		return true;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {		
		// Check if the player ran a command for our plugin
		if (cmd.getName().equalsIgnoreCase("nmg")) {
			// Tab complete only works for players
			if (!(sender instanceof Player)) {
				return null;
			}
			
			Player player = (Player) sender;
			
			if (args.length == 1) {
				List<String> result = new ArrayList<String>();
				for (String s : new ArrayList<String>(commands.keySet())) {
					if (s.startsWith(args[0].toLowerCase()) && player.hasPermission(commands.get(s).getPermissionNode()))
						result.add(s);
				}
				return result;
			}
			
			if (commands.containsKey(args[0].toLowerCase())){
				return commands.get(args[0]).onTabComplete(player, args);
			} 			
		}
		
		return null;
	}
	
	private void SendInfoToPlayer(Player player) {
		player.sendMessage(MessageHelper.longPrefixStart);
		player.sendMessage(ChatColor.GRAY + "Plugin made by: " + ChatColor.GOLD + "PinkNeonDinosaur");
		player.sendMessage(ChatColor.GRAY + "This plugin can be used to enable/disable griefing "
				+ "done by certain mobs, without having to set the doMobGriefing gamerule to true.");
		player.sendMessage(ChatColor.GRAY + "Below is a list of all commands you can use:");
		player.sendMessage(ChatColor.GOLD + "/nmg: " + ChatColor.WHITE + "Shows help and info about this plugin.");
		for (BaseCommand cmd : commands.values()) {
			if (player.hasPermission(cmd.getPermissionNode())) {
				player.sendMessage(ChatColor.GOLD + cmd.getUsage() + ": " + cmd.getDescription());
			}
		}
		player.sendMessage(MessageHelper.longPrefixEnd);
	}
}
