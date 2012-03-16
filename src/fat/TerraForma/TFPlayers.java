package fat.TerraForma;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class TFPlayers {

	private static HashMap<String, TerraFormaPlayer> players;
	
	public static void setup(){
		players = new HashMap<String, TerraFormaPlayer>();
	}
	
	public static TerraFormaPlayer getPlayer(Player player){
		TerraFormaPlayer tfPlayer = null;
		
		String playerName = player.getName();
		
		if (players.containsKey(playerName)){
			tfPlayer = players.get(playerName);
		} else {
			tfPlayer = new TerraFormaPlayer(player);
			players.put(playerName, tfPlayer);
		}
		
		return tfPlayer;
	}
	
}
