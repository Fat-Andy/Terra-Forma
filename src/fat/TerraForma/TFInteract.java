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
		ItemStack inHand = player.getItemInHand();
		Material hand = inHand.getType();
		if (event.getAction() == Action.RIGHT_CLICK_AIR && hand == Material.WOOD_PICKAXE) {
			try {
				TerraFormaPlayer tf = TerraForma.terraFormaPlayers.get(event.getPlayer().getName());
				tf.onPlayerEvent(event);
			} catch (Exception e) {
				this.plugin.addTerraFormaPlayer(event.getPlayer());
			}
		}else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && hand == Material.WOOD_PICKAXE) {
			try {
				TerraFormaPlayer tf = TerraForma.terraFormaPlayers.get(event.getPlayer().getName());
				tf.onPlayerEvent2(event);
			} catch (Exception e) {
				this.plugin.addTerraFormaPlayer(event.getPlayer());
			}
		}
	}
}
