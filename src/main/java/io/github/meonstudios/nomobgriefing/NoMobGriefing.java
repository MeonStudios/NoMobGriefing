package io.github.meonstudios.nomobgriefing;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.meonstudios.nomobgriefing.commands.MyCommandExecuter;

public final class NoMobGriefing extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getLogger().info("***NoMobGriefing plugin by PinkNeonDino has been enabled.***");
		// Add the event listener
		getServer().getPluginManager().registerEvents(new MyEventListener(this), this);
		// Add the command executor
		MyCommandExecuter executer = new MyCommandExecuter(this);
		this.getCommand("nmg").setExecutor(executer);
		this.getCommand("nmg").setTabCompleter(executer);
		// Load a default config if it hasn't been loaded before
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("***NoMobGriefing plugin by PinkNeonDino has been disabled.***");
	}
}
