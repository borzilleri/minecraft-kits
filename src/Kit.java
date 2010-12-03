
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author jonathan
 */
public class Kit {

	protected String name;
	protected EnumMap<EntityTypeEnum, Integer> kitItems;

	public Kit(String name, String[] items) {
		kitItems = new EnumMap<EntityTypeEnum,Integer>(EntityTypeEnum.class);
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
				kitItems.put(itemEnum, number);
			}
			catch( IllegalArgumentException e ) {
				// No actual item, just ignore it.
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

		for( Map.Entry<EntityTypeEnum,Integer> thisItem : kitItems.entrySet()) {
			line += String.format("%s:%d;", thisItem.getKey().toString(), thisItem.getValue());
		}
		return line;
	}

	public MessageBlock[] chatMessage() {
		MessageBlock messages[] = new MessageBlock[(kitItems.size()*2)+1];
		int i = 0;
		messages[i] = new MessageBlock(ColorEnum.LightGray, name+": ");		
		for (Map.Entry<EntityTypeEnum,Integer> item : kitItems.entrySet()) {
			// Set the current Item's Name messageBlock
			messages[i+1] = new MessageBlock(ColorEnum.LightPurple, item.getKey().toString());
			messages[i+2] = new MessageBlock(ColorEnum.LightGray, String.format(
				" (%d), ", item.getValue()));
			i = i + 2;
		}
		return messages;
	}

	public void giveTo(Player player) {
		for( Map.Entry<EntityTypeEnum,Integer> item: kitItems.entrySet()) {
			player.giveItem(item.getKey(), item.getValue());
		}
	}
}
