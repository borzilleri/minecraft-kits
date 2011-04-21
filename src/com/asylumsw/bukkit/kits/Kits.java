package com.asylumsw.bukkit.kits;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jonathan
 */
public class Kits extends JavaPlugin {

	private HashMap<String, Package> packages;
	private PackageFile file = new PackageFile();

	@Override
	public void onEnable() {
		packages = file.load();

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable() {
		System.out.println("Kits Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kit")) {
			if (1 > args.length) return false;

			if (args[0].equalsIgnoreCase("list")) {
				listPackages(sender);
			}
			else if (args[0].equalsIgnoreCase("reload")) {
				if (sender.isOp()) {
					sender.sendMessage(ChatColor.DARK_GRAY+"[kits] Reloading kits.");
					reloadPackages();
				}
				else {
					sender.sendMessage(ChatColor.DARK_GRAY+"[kits] "+
									ChatColor.RED + "ERROR: This command is restricted to Admins.");
				}
			}
			else if (args[0].equalsIgnoreCase("cooling") ) {
				if( sender.isOp() ) {
					listPackageCooldowns(sender);
				}
				else {
					sender.sendMessage(ChatColor.DARK_GRAY+"[kits] "+
									ChatColor.RED+"ERROR: This command is restricted to Admins.");
				}
			}
			else {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED+"[kits] ERROR: Kits can only be spawned by players.");
				}
				else {
					givePlayerPackage((Player) sender, args[0]);
				}
			}
			return true;
		}
		
		return false;
	}

	protected void reloadPackages() {
		HashMap<String, Package> newPackages = file.load();
		for (Map.Entry<String, Package> item : newPackages.entrySet()) {
			if (packages.containsKey(item.getKey())) {
				packages.get(item.getKey()).update(item.getValue());
			}
			else {
				packages.put(item.getKey(), item.getValue());
			}
		}

	}

	protected void listPackages(CommandSender player) {
		player.sendMessage(ChatColor.DARK_GRAY+"[kits] "+
						ChatColor.GRAY+"Available Kits:");
		for (Map.Entry<String, Package> pkg : packages.entrySet()) {
			player.sendMessage(ChatColor.DARK_GRAY+"[kits] "+
							pkg.getValue().getChatMessage());
		}
	}

	protected void listPackageCooldowns(CommandSender sender) {
		sender.sendMessage(ChatColor.DARK_GRAY+"[kits] "+
						ChatColor.GRAY+"Current Cooldowns:");
		for(Map.Entry<String,Package> pkg: packages.entrySet()) {
			String msg = pkg.getValue().getCooldownsChatMessage();
			if( null == msg ) continue;
			sender.sendMessage(ChatColor.DARK_GRAY+"[kits] "+
							msg);
		}
	}
	
	protected void givePlayerPackage(Player player, String pkgName) {
		if (!packages.containsKey(pkgName)) {
			player.sendMessage(ChatColor.RED + "ERROR: Unknown kit: " + pkgName);
			return;
		}
		packages.get(pkgName).spawnAtPlayer(player);
	}
}
