package fat.TerraForma;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.ItemStack;


public class TFInteract implements Listener{
	
	public final TerraForma plugin;
	
	
	public TFInteract(TerraForma p) {
		this.plugin = p;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		// probably shouldn't store these if only doing one check -hav
		ItemStack inHand = player.getItemInHand();
		Material hand = inHand.getType();
		
		if (hand == Material.WOOD_PICKAXE){
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
	
				TerraFormaPlayer tfPlayer = TFPlayers.getPlayer(event.getPlayer());
				if (tfPlayer != null){
					tfPlayer.playerClickedAir(event);
				}
	
			} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				TerraFormaPlayer tfPlayer = TFPlayers.getPlayer(event.getPlayer());
				if (tfPlayer != null){
					tfPlayer.playerClickedBlock(event);
				}
				
			}
		}
	}
}
