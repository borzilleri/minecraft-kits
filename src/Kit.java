
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author jonathan
 */
public class Kit {

	protected String name;
	protected EnumMap<EntityTypeEnum, Integer> entities;
	protected EnumMap<BlockTypeEnum, Integer> blocks;

	public Kit(String name, String[] items) {
		entities = new EnumMap<EntityTypeEnum,Integer>(EntityTypeEnum.class);
		blocks = new EnumMap<BlockTypeEnum,Integer>(BlockTypeEnum.class);
		this.name = name;

		//EntityTypeEnum itemType;
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
				EntityTypeEnum itemEnum = EntityTypeEnum.valueOf(item[0]);
				entities.put(itemEnum, number);
			}
			catch( IllegalArgumentException e ) {
				// No EntityType found, attempt to find a BlockType.
				try {
					BlockTypeEnum blockEnum = BlockTypeEnum.valueOf(item[0]);
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

	@Override
	public String toString() {
		String line = this.name+";";

		for( Map.Entry<EntityTypeEnum,Integer> thisItem : entities.entrySet()) {
			line += String.format("%s:%d;", thisItem.getKey().toString(), thisItem.getValue());
		}
		for( Map.Entry<BlockTypeEnum,Integer> thisBlock : blocks.entrySet() ) {
			line += String.format("%s:%d;", thisBlock.getKey().toString(), thisBlock.getValue());
		}
		return line;
	}

	public MessageBlock[] chatMessage() {
		int numberOfMessageBlocks = 1 + entities.size()*2 + blocks.size()*2;		
		MessageBlock messages[] = new MessageBlock[numberOfMessageBlocks];

		int i = 0;
		messages[i] = new MessageBlock(ColorEnum.LightGray, name+": ");		

		for (Map.Entry<EntityTypeEnum,Integer> item : entities.entrySet()) {
			// Set the current Item's Name messageBlock
			messages[i+1] = new MessageBlock(ColorEnum.LightPurple, item.getKey().toString());
			messages[i+2] = new MessageBlock(ColorEnum.LightGray, String.format(
				" (%d), ", item.getValue()));
			i = i + 2;
		}

		for( Map.Entry<BlockTypeEnum,Integer> item: blocks.entrySet() ) {
			messages[i+1] = new MessageBlock(ColorEnum.LightBlue, item.getKey().toString());
			messages[i+2] = new MessageBlock(ColorEnum.LightGray, String.format(
							" (%d), ", item.getValue() ) );
			i = i + 2;
		}
		
		return messages;
	}

	public void giveTo(Player player) {
		for( Map.Entry<EntityTypeEnum,Integer> item: entities.entrySet()) {
			player.giveItem(item.getKey(), item.getValue());
		}
		for( Map.Entry<BlockTypeEnum,Integer> item: blocks.entrySet() ) {
			player.giveItem(item.getKey(), item.getValue());
		}
	}
}
