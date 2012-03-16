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
	//reads the users commands
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if (command.equalsIgnoreCase("tf")) {
			try {
				TerraFormaPlayer terraFormaPlayer = TFPlayers.getPlayer((Player)sender);
				if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("Help") || args[0].equalsIgnoreCase("?")))) {
						sender.sendMessage(ChatColor.GOLD + "[TerraForma] - Create and manipulate terrain.");
						sender.sendMessage(ChatColor.RED + "Using TerraForma:");
						sender.sendMessage(ChatColor.GRAY + "Right click from far away with wood pickaxe to create terrain.");
						sender.sendMessage(ChatColor.GRAY + "Right click block with wood pickaxe to select material type.");
						sender.sendMessage(ChatColor.YELLOW + "/tf on/off - " + ChatColor.AQUA + "enables/disables TerraForma");
						sender.sendMessage(ChatColor.YELLOW + "/tf type - " + ChatColor.AQUA + "enables selected material mode");
						sender.sendMessage(ChatColor.YELLOW + "/tf notype - " + ChatColor.AQUA + "disables selected material mode");
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
