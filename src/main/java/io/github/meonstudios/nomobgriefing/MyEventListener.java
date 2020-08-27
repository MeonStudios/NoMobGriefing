package io.github.meonstudios.nomobgriefing;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.plugin.Plugin;

public final class MyEventListener implements Listener {
	
	private final Plugin plugin;
	
	public MyEventListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/*
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		plugin.getLogger().info(event.getEntity() + " | " + event.getBlock().getType());
	}
	*/
	
	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent event) {
		if (!(event.getEntityType() == EntityType.CREEPER) || plugin.getConfig().getBoolean("mob.creeper")) {
			return;
		}
		
		event.blockList().clear();
	}
	
	/**
	 * This method prevents entities from blowing up other entities, such as armor stands, items, etc.
	 */
	@EventHandler
	public void onEntityDestroyEntity(EntityDamageByEntityEvent event) {		
		// Only prevent damage to block entities
		if (event.getEntityType().isAlive() && event.getEntityType() != EntityType.ARMOR_STAND)
			return;
		
		switch(event.getDamager().getType()) {
			case CREEPER:
				if (!plugin.getConfig().getBoolean("mob.creeper")) {
					event.setCancelled(true);
				}
				break;
			case PRIMED_TNT:
				if (!plugin.getConfig().getBoolean("mob.tnt")) {
					event.setCancelled(true);
				}
				break;
			case MINECART_TNT:
				if (!plugin.getConfig().getBoolean("mob.tntminecart")) {
					event.setCancelled(true);
				}
				break;
			case FIREBALL:
				if (!plugin.getConfig().getBoolean("mob.ghast")) {
					event.setCancelled(true);
				}
				break;
			case WITHER:
			case WITHER_SKULL:
				if (!plugin.getConfig().getBoolean("mob.wither")) {
					event.setCancelled(true);
				}
				break;
			default:
				break;
		}		
	}
	
	/**
	 * This method prevents entities destroying hanging entities, such as paintings, item frames, etc.
	 */
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		switch(event.getRemover().getType()) {
			case CREEPER:
				if (!plugin.getConfig().getBoolean("mob.creeper")) {
					event.setCancelled(true);
				}
				break;
			case PLAYER:
				if (!plugin.getConfig().getBoolean("mob.tnt") || !plugin.getConfig().getBoolean("mob.tntminecart")) {
					if (event.getCause() == RemoveCause.EXPLOSION || event.getCause() == RemoveCause.ENTITY)
						event.setCancelled(true);
				}
				break;
			case GHAST:
				if (!plugin.getConfig().getBoolean("mob.ghast")) {
					event.setCancelled(true);
				}
				break;
			case WITHER:
			case WITHER_SKULL:
				if (!plugin.getConfig().getBoolean("mob.wither")) {
					event.setCancelled(true);
				}
				break;
			default:
				break;
		}
	}
	
	@EventHandler
	public void onZombieDoorBreak(EntityBreakDoorEvent event) {
		if (plugin.getConfig().getBoolean("mob.door")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onTurtleEggDestroy(EntityInteractEvent event) {
		if (!(event.getEntityType() == EntityType.ZOMBIE) || plugin.getConfig().getBoolean("mob.turtle_egg")) {
			return;
		}
		
		if (!(event.getBlock().getType() == Material.TURTLE_EGG)) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEndermanPickup(EntityChangeBlockEvent event) {
		if (!(event.getEntityType() == EntityType.ENDERMAN) || plugin.getConfig().getBoolean("mob.enderman")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onSheepEat(EntityChangeBlockEvent event) {
		if (!(event.getEntityType() == EntityType.SHEEP) || plugin.getConfig().getBoolean("mob.sheep")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onRabbitEat(EntityChangeBlockEvent event) {
		if (!(event.getEntityType() == EntityType.RABBIT) || plugin.getConfig().getBoolean("mob.rabbit")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onRavagerDestroy(EntityChangeBlockEvent event) {
		if (!(event.getEntityType() == EntityType.RAVAGER) || plugin.getConfig().getBoolean("mob.ravager")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onSnowgolemStep(EntityBlockFormEvent event) {
		if(!(event.getEntity() instanceof Snowman) || plugin.getConfig().getBoolean("mob.snowgolem")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onGhastballExplode(EntityExplodeEvent event) {
		if (!(event.getEntityType() == EntityType.FIREBALL) || plugin.getConfig().getBoolean("mob.ghast")) {
			return;
		}
		
		event.blockList().clear();
	}
	
	/**
	 * This method fires when the wither explodes and when a wither skull explodes
	 */
	@EventHandler
	public void onWitherskullExplode(EntityExplodeEvent event) {
		if (plugin.getConfig().getBoolean("mob.wither")) {
			return;
		}
		
		if (!(event.getEntityType() == EntityType.WITHER_SKULL || event.getEntityType() == EntityType.WITHER)) {
			return;
		}
		
		event.blockList().clear();
	}
	
	/**
	 * This method fires when the wither is breaking blocks because he is choked
	 */
	@EventHandler
	public void onWitherDestroy(EntityChangeBlockEvent event) {
		if (!(event.getEntityType() == EntityType.WITHER) || plugin.getConfig().getBoolean("mob.wither")) {
			return;
		}
		
		event.setCancelled(true);
	}
	
	
	@EventHandler
	public void onEnderdragonBlockDestroy(EntityExplodeEvent event) {
		if (!(event.getEntityType() == EntityType.ENDER_DRAGON) || plugin.getConfig().getBoolean("mob.enderdragon")) {
			return;
		}
		
		if (event.blockList().isEmpty())
			return;
		
		event.blockList().clear();		
	}
	
	@EventHandler
	public void onTNTExplode(EntityExplodeEvent event) {
		if (!(event.getEntityType() == EntityType.PRIMED_TNT) || plugin.getConfig().getBoolean("mob.tnt")) {
			return;
		}
		
		event.blockList().clear();
	}
	
	@EventHandler
	public void onTNTMinecraftExplode(EntityExplodeEvent event) {
		if (!(event.getEntityType() == EntityType.MINECART_TNT) || plugin.getConfig().getBoolean("mob.tntminecart")) {
			return;
		}
		
		event.blockList().clear();
	}
	
	@EventHandler
	public void onFarmlandTrample(EntityChangeBlockEvent event) {
		if (!(event.getBlock().getType() == Material.FARMLAND) || plugin.getConfig().getBoolean("mob.trampling")) {
			return;
		}

		event.setCancelled(true);
	}
	
	@EventHandler
	public void onSilverFishBlockEnter(EntityChangeBlockEvent event) {
		if(!(event.getEntityType() == EntityType.SILVERFISH) || plugin.getConfig().getBoolean("mob.silverfish")) {
			return;
		}
		
		event.setCancelled(true);
	}
}
