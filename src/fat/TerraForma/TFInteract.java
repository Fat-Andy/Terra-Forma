package fat.TerraForma;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TFInteract implements Listener{

	public final TerraForma plugin;
	

	public TFInteract(TerraForma p) {
		this.plugin = p;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		if (player.getItemInHand().getType() == Material.WOOD_PICKAXE){
			//call to set the block type to use
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				
				TerraFormaPlayer tfPlayer = TFPlayers.getPlayer(player);
				
				if (tfPlayer != null){
					tfPlayer.playerClickedAir(event);
				}
			}
			//call to set the floor level
			else if (event.getAction() == Action.LEFT_CLICK_AIR) {
				
				TerraFormaPlayer tfPlayer = TFPlayers.getPlayer(player);
				
				if (tfPlayer != null){
					tfPlayer.playerLeftClickedAir(event);
				}
			}
			//call to draw terrain
			else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				TerraFormaPlayer tfPlayer = TFPlayers.getPlayer(player);
				
				if (tfPlayer != null){
					tfPlayer.playerClickedBlock(event);
				}
			}
		}
	}
}

