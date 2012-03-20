package fat.TerraForma;

import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class TerraForma extends JavaPlugin {
	
	public final Logger log = Logger.getLogger("Minecraft");
	public final TFInteract tFInteract = new TFInteract(this); 
	
	private Commands commands = new Commands(this);
	
	//on disable configuration
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info("[" + pdfFile.getName() + "] is now disabled.");
	}
	
	//on load configuration
	@Override
	public void onEnable(){		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.tFInteract, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info("[" + pdfFile.getName() + "] is now enabled.");
		
		TFPlayers.setup();
		//vault
		setupVault();
		
		getCommand(Commands.getMain()).setExecutor(commands);
	}

	
	//Vault Permissions plugin setup
	private void setupVault() {
		// Allows use without Vault if desired
		if(getServer().getPluginManager().getPlugin("Vault") == null){
    		log.info(String.format("[%s] Vault not found. Permissions disabled.",getDescription().getName()));
    		return;
    	}
        RegisteredServiceProvider<Permission> permProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permProvider != null) {
        	Perms.setPerm(permProvider.getProvider());
        }
    }
}
