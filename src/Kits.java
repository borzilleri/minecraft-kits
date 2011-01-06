/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonathan
 */
public class Kits extends Mod {
	protected static KitList kits;
	protected static UsageList kitUses;

	@Override
	public void activate() {
		kits = new KitList();
		kits.load();
		kitUses = new UsageList();
		kitUses.load();
	}

	public boolean parseCommand(Player player, String[] tokens) {
		if( tokens.length < 1 ) return false;
		String command = tokens[0];

		if((command.equalsIgnoreCase("!kit") && tokens.length < 2) ||
						(command.equalsIgnoreCase("!kits")) ) {
			player.sendChat("Available Kits:", Color.Gray);
			kits.printKits(player);
			return true;
		}
		else if( command.equalsIgnoreCase("!kit") ) {
			this.generateKit(player, tokens[1]);
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "!kits; !kit <name>";
	}
	
	@Override
	public boolean onPlayerChat(Player player, String command) {
		String[] tokens = command.split(" ");
		return this.parseCommand(player, tokens);
	}

	@Override
	public boolean onPlayerCommand(Player player, String[] tokens) {
		return this.parseCommand(player, tokens);
	}
	
	protected void generateKit(Player player, String name) {
		if( kits.kits.containsKey(name) ) {
			Package pkg = kits.kits.get(name);
			if( UsageList.packageIsUsable(player, pkg) ) {
				player.sendChat("Generating Kit: "+name, Color.Gray);
				pkg.giveToPlayer(player);
			}
			else {
				player.sendChat("ERROR: Kit '"+name+"' cannot be used yet.", Color.Red);
			}
		}
		else {
			player.sendChat("Unknown Kit: "+name);
		}
	}
	
}
