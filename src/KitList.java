
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Kit File Format:
 * packageName:COOLDOWN;ITEM_ONE:QTY;ITEM_N:QTY;
 *
 * Example:
 * tools:300;IRON_PICK:2;IRON_SPADE:2;IRON_AXE:1;
 *
 *
 * @author jonathan
 */
public class KitList extends FileLoader {
	protected LinkedHashMap<String, Package> kits;
	
	public KitList() {
		this.filename = "kits.txt";
		this.kits = new LinkedHashMap<String,Package>();
	}

	public void printKits(Player player) {
		Package thisKit;

		for( Map.Entry<String, Package> kitEntry: kits.entrySet() ) {
			thisKit = kitEntry.getValue();
			player.sendChat(thisKit.chatMessage());
		}
	}
	
	protected void addKit(String[] tokens) {
		String[] kitInfo = tokens[0].split(":");
		if( 2 > kitInfo.length ) return;
		
		try {
			int cooldown = Integer.parseInt(kitInfo[1]);
			Package pkg = new Package(kitInfo[0], cooldown, tokens);
			if( 0 < pkg.getNumItems() ) {
				kits.put(kitInfo[0], pkg);
			}
		}
		catch( NumberFormatException ex ) {
		}
	}

	@Override
	protected void beforeLoad() {
		kits.clear();
	}

	@Override
	protected void loadLine(String line) {
		String[] tokens = line.split(";");
		if( tokens.length < 2 ) return;

		this.addKit(tokens);
	}

	@Override
	protected String saveString() {
		String line="";
		for( Map.Entry<String, Package> kitEntry: kits.entrySet() ) {
			line += String.format("%s\r\n", kitEntry.getValue().toString());
		}
		return line;
	}
	
}
