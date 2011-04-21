package com.asylumsw.bukkit.kits;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.bukkit.Material;

/**
 * Package file format:
 *
 * NAME:COOLDOWN;ITEM_NAME:ITEM_NAME_COUNT;ITEM_TWO:ITEM_TWO_COUNT;...;ITEM_N:ITEM_N_COUNT;
 *
 * NAME: string
 * COOLDOWN: integer (seconds)
 * @author jonathan
 */
public class PackageFile {

	protected String filename = "kits.txt";

	public PackageFile() {
	}

	public PackageFile(String filename) {
		this.filename = filename;
	}

	protected File obtainFile() {
		if (filename.isEmpty()) {
			System.out.println("[KITS] Filename is blank.");
			System.exit(-1);
		}

		File check;
		check = new File(filename);
		if (check.exists()) {
			return check;
		}

		try {
			check.createNewFile();
		}
		catch (Exception e) {
			System.out.println("[KITS] Could not create file: " + filename);
			System.exit(-1);
		}
		return check;
	}

	public HashMap<String, Package> load() {
		HashMap<String, Package> packages = new HashMap<String, Package>();
		
		File inFile = obtainFile();
		if (null == inFile) {
			System.out.println(String.format("[KITS] Unable to load %s.", filename));
			return packages;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) continue;
				String[] tokens = line.split(";");
				if (tokens.length < 2) continue;

				Package newPkg = makePackage(tokens);
				if (null != newPkg) {
					packages.put(newPkg.getName(), newPkg);
				}
			}
			reader.close();
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return packages;
	}

	protected Package makePackage(String[] tokens) {
		String[] pkgInfo = tokens[0].split(":");
		if (2 > pkgInfo.length) {
			System.out.println("Invalid Package: " + tokens[0]);
			return null;
		}

		String pkgName = pkgInfo[0];
		if( pkgName.equalsIgnoreCase("list") || pkgName.equalsIgnoreCase("reload") ||
						pkgName.equalsIgnoreCase("cooling") ) {
			System.out.println("Invalid Package name: "+pkgName);
			return null;
		}

		Package pkg;
		int cooldown;

		try {
			cooldown = Integer.parseInt(pkgInfo[1]);
		}
		catch (NumberFormatException ex) {
			cooldown = 0;
		}

		pkg = new Package(pkgName, cooldown);

		String[] itemInfo;
		int quantity;
		Material item;
		for (int i = 1; i < tokens.length; i++) {
			itemInfo = tokens[i].split(":");
			if (2 > itemInfo.length) {
				continue;
			}

			try {
				quantity = Integer.parseInt(itemInfo[1]);
			}
			catch (NumberFormatException ex) {
				quantity = 1;
			}

			item = Material.getMaterial(itemInfo[0]);
			if (item != null) {
				pkg.addItem(item, quantity);
			}
			else {
				System.out.println("Invalid package item: " + itemInfo[0]);
			}
		}

		if (0 >= pkg.getNumItems()) {
			System.out.println("Package has no valid items: " + pkg.getName());
			return null;
		}

		return pkg;
	}
}
