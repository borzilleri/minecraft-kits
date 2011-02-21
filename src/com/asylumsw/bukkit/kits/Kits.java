package com.asylumsw.bukkit.kits;

import java.io.File;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jonathan
 */
public class Kits extends JavaPlugin {
	private KitsPlayerListener playerListener;
	
	@Override
	public void onEnable() {
		PackageList packages = new PackageList();
		packages.load();
		playerListener = new KitsPlayerListener(this, packages);

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Event.Priority.Normal, this);

		// EXAMPLE: Custom code, here we just output some info so we can check all is well
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable() {
		System.out.println("Kits Disabled.");
	}

	public static void main(String[] args) {}
}
