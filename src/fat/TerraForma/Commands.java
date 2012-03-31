package fat.TerraForma;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	private TerraForma plugin;

	private static final String cmdMain = "tf";
	public Commands(TerraForma tf){
		plugin = tf;
	}

	@Override
	//
	//reads the users commands
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if (command.equalsIgnoreCase("tf")) {
			try {
				TerraFormaPlayer terraFormaPlayer = TFPlayers.getPlayer((Player)sender);
				if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("Help") || args[0].equalsIgnoreCase("?")))) {
						sender.sendMessage(ChatColor.BLUE + "~~" + ChatColor.DARK_GREEN + "TerraForma" + ChatColor.BLUE + "~~" + ChatColor.AQUA + " - Right click from far away with a wooden     pickaxe to manipulate terrain.");
						sender.sendMessage(ChatColor.GOLD + "Commands:");
						sender.sendMessage(ChatColor.YELLOW + "/tf on/off - " + ChatColor.AQUA + "Enables/Disables TerraForma");
						sender.sendMessage(ChatColor.YELLOW + "/tf matmode - " + ChatColor.AQUA + "Cycles material selection mode: " + ChatColor.LIGHT_PURPLE + "right click "  + ChatColor.AQUA + "or     "  + ChatColor.LIGHT_PURPLE + "point cursor "  + ChatColor.AQUA + "for desired block type.");
						sender.sendMessage(ChatColor.YELLOW + "/tf radmode - " + ChatColor.AQUA + "Cycles radius mode: " + ChatColor.LIGHT_PURPLE + "user defined "  + ChatColor.AQUA + "or     "  + ChatColor.LIGHT_PURPLE + "equation defined");
						sender.sendMessage(ChatColor.YELLOW + "/tf hollowcyl - " + ChatColor.AQUA + "Sets tool to Hollow Cylinder");
						sender.sendMessage(ChatColor.YELLOW + "/tf cylinder - " + ChatColor.AQUA + "Sets tool to Cylinder");
						sender.sendMessage(ChatColor.YELLOW + "/tf plateau - " + ChatColor.AQUA + "Sets tool to Plateau");
						sender.sendMessage(ChatColor.YELLOW + "/tf linetotarget - " + ChatColor.AQUA + "Sets tool to Line to Target");
						sender.sendMessage(ChatColor.YELLOW + "/tf hill - " + ChatColor.AQUA + "Sets tool to Hill");
						sender.sendMessage(ChatColor.YELLOW + "/tf valley - " + ChatColor.AQUA + "Sets tool to Valley");
						sender.sendMessage(ChatColor.YELLOW + "/tf level - " + ChatColor.AQUA + "Sets tool to Level");
						sender.sendMessage(ChatColor.YELLOW + "/tf height <number> - " + ChatColor.AQUA + "Sets tool Height");
						sender.sendMessage(ChatColor.YELLOW + "/tf radius <number> - " + ChatColor.AQUA + "Sets tool Radius");
						sender.sendMessage(ChatColor.YELLOW + "/tf slope <number> - " + ChatColor.AQUA + "Sets tool Slope");
						//TODO add in undo method
						sender.sendMessage(ChatColor.YELLOW + "/tf undo - " + ChatColor.AQUA + "--Available Soon--");
				}else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("on")) {
						terraFormaPlayer.enable();
						sender.sendMessage(ChatColor.GOLD + "Terrain Tools enabled, type /tf off to disable");
					} else if (args[0].equalsIgnoreCase("off")) {
						terraFormaPlayer.disable();
						sender.sendMessage(ChatColor.GOLD + "Terrain Tools disabled, type /tf on to enable");
					} else if (args[0].equalsIgnoreCase("Set")) {
						sender.sendMessage(ChatColor.AQUA +  "Enabled: " + ChatColor.GOLD +  terraFormaPlayer.isEnabled() +
								ChatColor.AQUA +  " Selected Mat type: " + ChatColor.GOLD +
								terraFormaPlayer.getSelectedMatData() + ChatColor.AQUA +  " Mat Mode: " + ChatColor.GOLD +
								terraFormaPlayer.getUseSelectedMatType() + ChatColor.AQUA +  " Rad Mode: " + ChatColor.GOLD +
								terraFormaPlayer.getUserRadMode() + ChatColor.AQUA + "" + " Current tool set to " +
								ChatColor.GOLD + terraFormaPlayer.getCurTool() + ChatColor.AQUA +  " Height: " + ChatColor.GOLD +
								terraFormaPlayer.getToolHeight() + ChatColor.AQUA +  " Slope: " + ChatColor.GOLD +
								terraFormaPlayer.getToolSlope() + ChatColor.AQUA +  " Floor Height: " + ChatColor.GOLD +
								terraFormaPlayer.getFloorHeight() + ChatColor.AQUA +  " Radius: " + ChatColor.GOLD +
								terraFormaPlayer.getToolRadius());
					} else if (args[0].equalsIgnoreCase("Matmode")) {
						if (terraFormaPlayer.getUseSelectedMatType() == false){
							terraFormaPlayer.enableSelectedType();
							sender.sendMessage(ChatColor.AQUA + "Right click to set desired material.");
						}else {
							terraFormaPlayer.disableSelectedType();
							sender.sendMessage(ChatColor.AQUA + "Material will be set to the block at the cursor.");
						}
					} else if (args[0].equalsIgnoreCase("RadMode")) {
						if (terraFormaPlayer.getUserRadMode() == false){
							terraFormaPlayer.enableUserRadMode();
							sender.sendMessage(ChatColor.AQUA + "User defined, limited radius");
						}else {
							terraFormaPlayer.disableUserRadMode();
							sender.sendMessage(ChatColor.AQUA + "Equation defined, radius will not be limited");
						}	
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
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to " + ChatColor.GOLD + "Hill. " + ChatColor.AQUA +"Height: " + ChatColor.GOLD +  terraFormaPlayer.getToolHeight() + ChatColor.AQUA +  " Slope: " + ChatColor.GOLD +  terraFormaPlayer.getToolSlope());
					} else if (args[0].equalsIgnoreCase("Valley")) {
						terraFormaPlayer.setCurTool(6);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to Valley");
					} else if (args[0].equalsIgnoreCase("Level")) {
						terraFormaPlayer.setCurTool(7);
						sender.sendMessage(ChatColor.AQUA + "" + "Current tool set to " + ChatColor.GOLD + "Level. " + ChatColor.AQUA +  "Clear Height: " + ChatColor.GOLD +  terraFormaPlayer.getToolHeight() + ChatColor.AQUA +  " Floor Height: " + ChatColor.GOLD +  terraFormaPlayer.getFloorHeight());
					}

				}
				//TODO Set Max and Min Limits for Height/Radius/Depth/Slope,
				//Also, check for any divide by 0's
				else if (args.length > 1 && (args[0].equalsIgnoreCase("Height") || args[0].equalsIgnoreCase("h"))) {

					Double value;
					try {
						value = Double.parseDouble(args[1]);
					} catch (NumberFormatException e) {
						value = 0.0;
					}

					terraFormaPlayer.setToolHeight(value.intValue());
					sender.sendMessage(ChatColor.AQUA + "" + "Tool height set to " + ChatColor.GOLD + value);
				} else if (args.length > 1 && (args[0].equalsIgnoreCase("Radius") || args[0].equalsIgnoreCase("r"))) {

					Double value;
					try {
						value = Double.parseDouble(args[1]);
					} catch (NumberFormatException e) {
						value = 0.0;
					}

					terraFormaPlayer.setToolRadius(value.intValue());
					sender.sendMessage(ChatColor.AQUA + "" + "Tool radius set to " + ChatColor.GOLD + value);
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
				plugin.log.warning("CAUGHT EXCEPTION:\n\tType: " + e.toString()
						+ "\n\tCommand: " + "\n\tBy: " + sender.getName());
				e.printStackTrace();
				return false;
			} catch (NullPointerException e) {
				plugin.log.warning("CAUGHT EXCEPTION:\n\tType: " + e.toString()
						+ "\n\tCommand: " + "\n\tBy: " + sender.getName());
				e.printStackTrace();
				return false;
			} catch (ArrayIndexOutOfBoundsException e) {
				plugin.log.warning("CAUGHT EXCEPTION:\n\tType: " + e.toString()
						+ "\n\tCommand: " + "\n\tBy: " + sender.getName());
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public static String getMain() {
		return cmdMain;
	}
}