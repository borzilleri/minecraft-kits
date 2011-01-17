package com.asylumsw.bukkit.kits;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 *
 * @author jonathan
 */
public class KitsPlayerListener extends PlayerListener {
	private final Kits plugin;
	private PackageList packages;

	public KitsPlayerListener(Kits instance, PackageList packageList) {
		plugin = instance;
		packages = packageList;
	}

	@Override
	public void onPlayerCommand(PlayerChatEvent event) {
		String[] split = event.getMessage().split(" ");
		Player player = event.getPlayer();

		if (split[0].equalsIgnoreCase("/kit")) {
			if( 2 > split.length) {
				player.sendMessage(ChatColor.RED + "Usage: /kit <name>");
			}
			else {
				packages.givePlayerPackage(player, split[1]);
			}
			event.setCancelled(true);
		}
		else if( split[0].equalsIgnoreCase("/kits") ) {
			packages.listPackages(player);
			event.setCancelled(true);
		}
		
	}
}
