/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonathan
 */
public class Kits extends Mod {
	protected static Kitlist kits;

	@Override
	public void activate() {
		Kits.kits = new Kitlist();
		Kits.kits.load();
	}

	public boolean parseCommand(Player player, String[] tokens, Boolean isAdmin) {
		if( tokens.length < 1 ) return false;
		String command = tokens[0].substring(1);

		if( command.equalsIgnoreCase("help") ) {

			return true;
		}
		else if( command.equalsIgnoreCase("kit") && tokens.length < 2 ) {
			player.sendPlayerMessage("Available Kits:", ColorEnum.Gray);
			kits.printKits(player);
			return true;
		}
		else if( command.equalsIgnoreCase("kit") ) {
			this.generateKit(tokens[1]);
			return true;
		}

		return false;
	}
	public boolean onPlayerChat(Player player, String command, Boolean isAdmin) {
		String[] tokens = command.split(" ");
		return this.parseCommand(player, tokens, isAdmin);
	}
	public boolean onPlayerCommand(Player player, String[] tokens, Boolean isAdmin) {
		return this.parseCommand(player, tokens, isAdmin);
	}


	protected void generateKit(String name) {
		
	}
	
}
