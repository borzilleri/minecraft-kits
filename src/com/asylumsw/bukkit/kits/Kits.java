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
		if (!(sender instanceof Player)) return false;

		if (cmd.getName().equalsIgnoreCase("kit")) {
			if (1 > args.length) return false;
			if (args[0].equalsIgnoreCase("list")) {
				listPackages((Player) sender);
			}
			else {
				givePlayerPackage((Player) sender, args[0]);
			}
			return true;
		}

		return false;
	}

	public void listPackages(Player player) {
		player.sendMessage(ChatColor.GRAY + "Available Kits:");
		for (Map.Entry<String, Package> pkg : packages.entrySet()) {
			player.sendMessage(pkg.getValue().getChatMessage());
		}
	}

	public void givePlayerPackage(Player player, String pkgName) {
		if (!packages.containsKey(pkgName)) {
			player.sendMessage(ChatColor.RED + "ERROR: Unknown kit: " + pkgName);
			return;
		}
		packages.get(pkgName).spawnAtPlayer(player);
	}
}
