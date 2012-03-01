package fat.TerraForma;

import java.util.HashMap;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class TerraForma extends JavaPlugin {
	
	//hashmap to store players in
	public static HashMap<String, TerraFormaPlayer> terraFormaPlayers = new HashMap<String, TerraFormaPlayer>();
	
	public static TerraForma plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public final TFInteract tFInteract = new TFInteract(this); 
	private Permission perm;
	
	//TODO Setup vault and permissions
	//Premission node for plateau tools
	public String makePlateau = "plateau.create";
	
	//on disable configuration
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " is now disabled.");
	}
	
	//on load configuration
	@Override
	public void onEnable(){
		
		//add all the players to TerraForma
		for (Player player : getServer().getOnlinePlayers()) {
			this.addTerraFormaPlayer(player);
		}
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.tFInteract, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " is now enabled.");
		
		//vault
		setupVault();
	}
	
	// gets the hashmap for plateau players
	public HashMap<String, TerraFormaPlayer> getTerraFormaPlayers() {
		return terraFormaPlayers;
	}
	
	//add player to the players hashmap
	public void addTerraFormaPlayer(Player player){
		log.info("HashMap: " + terraFormaPlayers.toString());
		log.info("Adding " + player.getName());
		try {
			TerraFormaPlayer terraFormaPlayer = terraFormaPlayers.get(player.getName());
			terraFormaPlayer.updatePlayer(player);
		}
		// if we didn't find the player in the hashmap we'll just add them like normal
		catch (Exception e) {
			terraFormaPlayers.put(player.getName(), new TerraFormaPlayer(player));
		}
	}
	
	//reads the users commands
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String command = commandLabel;
		if (command.equalsIgnoreCase("tf")) {
			try {
				TerraFormaPlayer terraFormaPlayer = terraFormaPlayers.get(((Player) sender).getName());
				if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("Help") || args[0].equalsIgnoreCase("?")))) {
						sender.sendMessage(ChatColor.GOLD + "[TerraForma]");
						sender.sendMessage(ChatColor.GOLD + "A Bukkit plugin for creating and manipulating terrain.");
						sender.sendMessage(ChatColor.RED + "Using TerraForma:");
						sender.sendMessage(ChatColor.GRAY + "Right click from far away with wood pickaxe to create terrain.");
						sender.sendMessage(ChatColor.GRAY + "Right click block with wood pickaxe to select material type.");
						sender.sendMessage(ChatColor.YELLOW + "/tf on - " + ChatColor.AQUA + "enables TerraForma");
						sender.sendMessage(ChatColor.YELLOW + "/tf off - " + ChatColor.AQUA + "disables TerraForma");
						sender.sendMessage(ChatColor.YELLOW + "/tf type - " + ChatColor.AQUA + "enables selected material mode");
						sender.sendMessage(ChatColor.YELLOW + "/tf notype - " + ChatColor.AQUA + "disables selected material mode");
						sender.sendMessage(ChatColor.YELLOW + "/tf add - " + ChatColor.AQUA + "adds player to TerraForma (currently automatic)");
						sender.sendMessage(ChatColor.YELLOW + "/tf hollowcyl - " + ChatColor.AQUA + "Sets tool to hollow cylinder");
						sender.sendMessage(ChatColor.YELLOW + "/tf cylinder - " + ChatColor.AQUA + "Sets tool to cylinder");
						sender.sendMessage(ChatColor.YELLOW + "/tf plateau - " + ChatColor.AQUA + "Sets tool to plateau");
						sender.sendMessage(ChatColor.YELLOW + "/tf linetotarget - " + ChatColor.AQUA + "Sets tool to Line to Target");
						sender.sendMessage(ChatColor.YELLOW + "/tf hill - " + ChatColor.AQUA + "Sets tool to Hill");
						sender.sendMessage(ChatColor.YELLOW + "/tf valley - " + ChatColor.AQUA + "Sets tool to Valley");
						sender.sendMessage(ChatColor.YELLOW + "/tf height <number> - " + ChatColor.AQUA + "Sets tool height");
						sender.sendMessage(ChatColor.YELLOW + "/tf radius <number> - " + ChatColor.AQUA + "Sets tool radius");
						sender.sendMessage(ChatColor.YELLOW + "/tf slope <number> - " + ChatColor.AQUA + "Sets tool slope (currently only used with Hill Tool)");
				}else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("on")) {
						terraFormaPlayer.enable();
						sender.sendMessage(ChatColor.RED + "Terrain Tools enabled, type /tf off to disable");
					} else if (args[0].equalsIgnoreCase("off")) {
						terraFormaPlayer.disable();
						sender.sendMessage(ChatColor.RED + "Terrain Tools disabled, type /tf on to enable");
					} else if (args[0].equalsIgnoreCase("Type")) {
						terraFormaPlayer.enableSelectedType();
						sender.sendMessage(ChatColor.YELLOW + "Selected Material Mode enabled, type /tf NoType to disable");
					} else if (args[0].equalsIgnoreCase("NoType")) {
						terraFormaPlayer.disableSelectedType();
						sender.sendMessage(ChatColor.YELLOW + "Selected Material Mode disabled, type /tf Type to enable");
					} else if (args[0].equalsIgnoreCase("add")) {
						this.addTerraFormaPlayer((Player) sender);
						sender.sendMessage(ChatColor.RED + "" + terraFormaPlayers.toString());
					} else if (args[0].equalsIgnoreCase("HollowCyl")) {
						terraFormaPlayer.setCurTool(1);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Hollow Cylinder");
					} else if (args[0].equalsIgnoreCase("Plateau")) {
						terraFormaPlayer.setCurTool(2);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Plateau");
					} else if (args[0].equalsIgnoreCase("Cylinder")) {
						terraFormaPlayer.setCurTool(3);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Cylinder");
					} else if (args[0].equalsIgnoreCase("LineToTarget")) {
						terraFormaPlayer.setCurTool(4);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Line to Target");
					} else if (args[0].equalsIgnoreCase("Hill")) {
						terraFormaPlayer.setCurTool(5);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Hill");
					} else if (args[0].equalsIgnoreCase("Valley")) {
						terraFormaPlayer.setCurTool(6);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Valley");
					}

				} else if (args.length > 1 && args[0].equalsIgnoreCase("Height")) {

					Double value;
					try {
						value = Double.parseDouble(args[1]);
					} catch (NumberFormatException e) {
						value = 0.0;
					}

					terraFormaPlayer.setToolHeight(value.intValue());
					sender.sendMessage(ChatColor.AQUA + "" + "Tool height set to " + value);
				} else if (args.length > 1 && args[0].equalsIgnoreCase("Radius")) {

					Double value;
					try {
						value = Double.parseDouble(args[1]);
					} catch (NumberFormatException e) {
						value = 0.0;
					}

					terraFormaPlayer.setToolRadius(value.intValue());
					sender.sendMessage(ChatColor.AQUA + "" + "Tool radius set to " + value);
				} else if (args.length > 1 && args[0].equalsIgnoreCase("Slope")) {

					Double value;
					try {
						value = Double.parseDouble(args[1]);
					} catch (NumberFormatException e) {
						value = 0.0;
					}

					terraFormaPlayer.setToolSlope(value);
					sender.sendMessage(ChatColor.AQUA + "" + "Tool slope set to " + value);
				} else {
					sender.sendMessage(ChatColor.RED + "args.length" + args.length + " " + command + " args[0] " + args[0] + " args[1] " + args[1]);
					return false;
				}
			} catch (CommandException e) {
				log.warning("CAUGHT EXCEPTION:\n\tType: " + e.toString()
						+ "\n\tCommand: " + "\n\tBy: " + sender.getName());
				return false;
			} catch (NullPointerException e) {
				log.warning("CAUGHT EXCEPTION:\n\tType: " + e.toString()
						+ "\n\tCommand: " + "\n\tBy: " + sender.getName());
				return false;
			} catch (ArrayIndexOutOfBoundsException e) {
				log.warning("CAUGHT EXCEPTION:\n\tType: " + e.toString()
						+ "\n\tCommand: " + "\n\tBy: " + sender.getName());
				return false;
			}
		}
		return false;
	}
	
	/*
	public boolean readCommand(Player player, String command){
		return false;
				
	}
	*/
	
	//Vault Permissions plugin setup
	private void setupVault() {       
        RegisteredServiceProvider<Permission> permProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permProvider != null) {
            perm = permProvider.getProvider();
        }
    }
	
	//Gets user's permission
	public Permission getPerm(){
    	return perm;
    }
}
