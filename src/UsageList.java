
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * File Format:
 * player_name:package_name;usage_time
 *
 * 
 * @author jborzilleri
 */
public class UsageList extends FileLoader {
	protected static LinkedHashMap<String,Calendar> uses;

	public UsageList() {
		uses = new LinkedHashMap<String, Calendar>();
		filename = "kit-usage.txt";
	}

	protected static String getUsageKey(String playerName, String packageName) {
		return playerName + ":" + packageName;
	}

	public void usePackage(String playerName, String packageName) {
		uses.put(getUsageKey(playerName,packageName), Calendar.getInstance());
		save();
	}

	public static boolean packageIsUsable(Player player, Package pkg) {
		String usageKey = getUsageKey(player.getName(),pkg.getName());
		if( uses.containsKey(usageKey) ) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.SECOND, pkg.getCooldown()*-1);
			return uses.get(usageKey).before(now);
		}
		return true;
	}
	
	@Override
	protected void beforeLoad() {
		uses.clear();
	}

	@Override
	protected void loadLine(String line) {
		String[] tokens = line.split(";");
		if( 2 > tokens.length ) return;

		try {
			Calendar usedOn = Calendar.getInstance();
			usedOn.setTimeInMillis(Long.parseLong(tokens[1]));
			uses.put(tokens[0], usedOn);
		}
		catch( NumberFormatException ex ) {
		}
	}

	@Override
	protected String saveString() {
		String line="";
		for( Map.Entry<String, Calendar> usageEntry: uses.entrySet() ) {
			line += String.format("%s;%d\r\n",
							usageEntry.getKey(), usageEntry.getValue().getTimeInMillis());
		}
		return line;
	}

}
