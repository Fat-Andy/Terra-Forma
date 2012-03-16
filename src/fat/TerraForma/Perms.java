package fat.TerraForma;

import org.bukkit.entity.Player;

import net.milkbowl.vault.permission.Permission;

public class Perms {

	private static Permission perm;
	
	//Permission node for plateau tools
	private String makePlateau = "plateau.create";

	public static void setPerm(Permission newPerm) {
		perm = newPerm;
	}
		
	public static boolean permEnabled(){
    	return (perm != null);
    }
	
	public boolean hasCreatePlateau(Player player){
		if (permEnabled()){
			return perm.has(player, makePlateau);
		} else {
			return false;
		}
	}
}
