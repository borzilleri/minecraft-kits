
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


	public Kitlist() {
		this.filename = "kits.txt";
		this.kits = new LinkedHashMap<String,Kit>();
	}

	public void printKits(Player player) {
		Kit thisKit;

		for( Map.Entry<String, Kit> kitEntry: kits.entrySet() ) {
			thisKit = kitEntry.getValue();
			player.sendPlayerMessage(thisKit.chatMessage());
		}
	}
	
	protected void addKit(String[] tokens) {
		String name = tokens[0];
		
		Kit temp = new Kit(name, tokens);
		kits.put(name, temp);
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
		Kit thisKit;
		for( Map.Entry<String, Kit> kitEntry: kits.entrySet() ) {
			thisKit = kitEntry.getValue();
			line += String.format("%s\r\n", kitEntry.toString());
		}
		return line;
	}
	
}
