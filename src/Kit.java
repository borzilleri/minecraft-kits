
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class Kit {

	private String name;
	protected LinkedHashMap<Integer, Integer> kitItems;

	public Kit(String name, String[] items) {
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
				kitItems.put(Integer.parseInt(item[0]), number);
			}
			catch( Exception e ) {
			}			
			//itemType = World.getMiniblocksOfType(Integer.parseInt(item[0])).get(0).getEntityEnum();
		}
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String line = this.name+";";

		for( Map.Entry<Integer,Integer> thisItem : kitItems.entrySet()) {
			line += String.format("%d:%d;", thisItem.getKey(), thisItem.getValue());
		}
		return line;
	}

	public MessageBlock[] getKitMessage() {
		MessageBlock[] messages = new MessageBlock[2];
		EntityTypeEnum thisItemType;

		messages[0].setColor(ColorEnum.Gray);
		messages[0].setMessage(name + ": ");
		messages[1].setColor(ColorEnum.LightPurple);

		String itemString = "";
		for (Map.Entry<Integer, Integer> item : kitItems.entrySet()) {
			itemString += String.format("%d (%d),", item.getKey(), item.getValue());
		}
		messages[1].setMessage(itemString.substring(0, itemString.length()-1));

		return messages;
	}
}
