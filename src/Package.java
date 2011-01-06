
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author jonathan
 */
public class Package {
	protected String name;
	protected int cooldown;
	protected EnumMap<EntityType, Integer> entities;
	protected EnumMap<BlockType, Integer> blocks;

	public Package(String name, int cooldown, String[] items) {
		entities = new EnumMap<EntityType,Integer>(EntityType.class);
		blocks = new EnumMap<BlockType,Integer>(BlockType.class);
		this.name = name;
		this.cooldown = (0 >= cooldown) ? 0 : cooldown;

		//EntityType itemType;
		Integer number;
		Boolean first = true;
		for(String i: items) {
			if( first ) {
				first = false;
				continue;
			}
			String[] item = i.split(":");
			try {
				number = (item.length < 2) ? 1 : Integer.parseInt(item[1]);
			}
			catch( NumberFormatException e ) {
				number = 1;
			}
			
			try {
				EntityType itemEnum = EntityType.valueOf(item[0]);
				entities.put(itemEnum, number);
			}
			catch( IllegalArgumentException e ) {
				// No EntityType found, attempt to find a BlockType.
				try {
					BlockType blockEnum = BlockType.valueOf(item[0]);
					blocks.put(blockEnum, number);
				}
				catch( IllegalArgumentException e2 ) {
					// No BlockType found.
				}
				catch( Exception e2 ) {
				}
			}
			catch( Exception e ) {
				Server.log(Level.SEVERE, e.getMessage());
			}			
		}
	}
	
	public String getName() {
		return name;
	}
	public int getNumItems() {
		return blocks.size() + entities.size();
	}
	public int getCooldown() {
		return cooldown;
	}

	@Override
	public String toString() {
		String line = this.name+";";

		for( Map.Entry<EntityType,Integer> thisItem : entities.entrySet()) {
			line += String.format("%s:%d;", thisItem.getKey().toString(), thisItem.getValue());
		}
		for( Map.Entry<BlockType,Integer> thisBlock : blocks.entrySet() ) {
			line += String.format("%s:%d;", thisBlock.getKey().toString(), thisBlock.getValue());
		}
		return line;
	}

	public String chatMessage() {
		String message = Color.LightGray.getFormat() + name + ": ";


		for (Map.Entry<EntityType,Integer> item : entities.entrySet()) {
			message += Color.LightPurple.getFormat() + item.getKey().toString() + " "
							+ Color.LightGray.getFormat() + "("+item.getValue()+"), ";
		}

		for( Map.Entry<BlockType,Integer> item: blocks.entrySet() ) {
			message += Color.LightPurple.getFormat() + item.getKey().toString() + " "
							+ Color.LightGray.getFormat() + "("+item.getValue()+"), ";
		}
		
		return message;
	}

	public void giveToPlayer(Player player) {
		for( Map.Entry<EntityType,Integer> item: entities.entrySet()) {
			World.spawnMiniblock(item.getKey(), item.getValue(), player.getLocation());
			//player.giveItem(item.getKey(), item.getValue());
		}
		for( Map.Entry<BlockType,Integer> item: blocks.entrySet() ) {
			World.spawnMiniblock(item.getKey(), item.getValue(), player.getLocation());
			//player.giveItem(item.getKey(), item.getValue());
		}
	}
}
