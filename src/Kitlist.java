
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * kitName;123:4;567:1
 * kitName:345:2
 *
 * @author jonathan
 */
public class Kitlist extends FileLoader {
	protected LinkedHashMap<String, Kit> kits;

	protected class Kit {
		private String name;
		private Integer itemId;
		private Integer number;
		public String getName() {
			return name;
		}
		public Integer getItemId() {
			return itemId;
		}
		public Integer getNumber() {
			return number;
		}
		public Kit(String name, Integer id, Integer num) {
			this.name = name;
			this.itemId = id;
			this.number = num;
		}
	}

	public Kitlist() {
		this.filename = "kits.txt";
		this.kits = new LinkedHashMap<String,Kit>();
	}

	public void printKits(Player player) {
		Kit thisKit;
		EntityTypeEnum thisItem;
		MessageBlock[] message = new MessageBlock[2];
		message[0].setColor(ColorEnum.Gray);
		message[1].setColor(ColorEnum.LightBlue);

		for( Map.Entry<String, Kit> kitEntry: kits.entrySet() ) {
			thisKit = kitEntry.getValue();
			thisItem = World.getMiniblocksOfType(thisKit.getItemId()).
				get(0).getEntityEnum();

			if( null != thisItem ) {
				message[0].setMessage(thisItem.toString()+": ");

				player.sendPlayerMessage(message);
			}
		}
	}
	
	protected void addKit(String name, Integer itemId, Integer number) {
		Kit temp = new Kit(name,itemId,number);
		kits.put(name, temp);
	}

	@Override
	protected void beforeLoad() {
		kits.clear();
	}

	@Override
	protected void loadLine(String line) {
		String[] tokens = line.split(":");
		Integer number = (tokens.length<3) ? 1 : Integer.parseInt(tokens[2]);

		if( tokens.length < 2 ) return;
		this.addKit(tokens[0], Integer.parseInt(tokens[1]), number);
	}

	@Override
	protected String saveString() {
		String line="";
		Kit thisKit;
		for( Map.Entry<String, Kit> kitEntry: kits.entrySet() ) {
			thisKit = kitEntry.getValue();
			line += String.format("%s:%d:%d\r\n",
				thisKit.getName(), thisKit.getItemId(), thisKit.getNumber());
		}
		return line;
	}
	
}
