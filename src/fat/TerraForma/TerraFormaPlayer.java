package fat.TerraForma;


import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class TerraFormaPlayer {
	private Player player;
	private World world;
	private int floorHeight = 65;
	private int toolHeight = 10;
	private int toolRadius = 5;
	private double toolSlope = 2;
	private int curTool = 5;
	private boolean isEnabled = true;
	private boolean userRadMode = false;
	private boolean useSelectedMatType = false;
	private Material selectedMatType = Material.SANDSTONE;
	private byte selectedMatData  = 0;
	private TerrainTransform tTransform = new TerrainTransform(this);
	private List<Block> sight;

	public TerraFormaPlayer(Player player){
		this.player = player;

		player.sendMessage("TerraForma player created");
	}

	/* There's no need for this as far as I can tell -hav
	//update player in the hashmap
	public void updatePlayer(Player player) {
		log.info("Updating player " + player.getName() + "...");
		this.player = player;
	}
	*/
	//
	//when a right click block event triggers 
	public void playerClickedBlock(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();
		Material selectedMatTypetemp = clickedBlock.getType();
		byte selectedMatDatatemp = clickedBlock.getData();
		this.selectedMatType = selectedMatTypetemp;
		this.selectedMatData = selectedMatDatatemp;
		this.player.sendMessage(ChatColor.AQUA + "New material type: " + ChatColor.GOLD + this.selectedMatType);
	}
	
	//when a left click air event triggers 
	public void playerLeftClickedAir(PlayerInteractEvent event){
		Block block = player.getTargetBlock(null, 150);
		this.floorHeight = block.getY() + 1;
		player.sendMessage(ChatColor.AQUA + "Floor height set to " + ChatColor.GOLD + this.floorHeight);		
	}

	//when a right click air event triggers 
	public void playerClickedAir(PlayerInteractEvent event) {
		Block block = player.getTargetBlock(null, 150);
		Material type = block.getType();
		byte data = block.getData();
		int xpos = block.getX();
		int ypos = block.getY();
		int zpos = block.getZ();
		this.sight = player.getLineOfSight(null, 40);
		this.world = block.getWorld();
		
		//ItemStack inHand = player.getItemInHand();

		//this.player.sendMessage(ChatColor.YELLOW + "data: " + data);

		/*TODO add in permissions checking
		Permission perm = plugin.getPerm();
				
		if(perm.has(player, plugin.makePlateau)){
			//player.sendMessage(ChatColor.RED + "you have permission");
		}
		*/
		if (useSelectedMatType == true){
			type = selectedMatType;
			data = selectedMatData;
			//this.player.sendMessage(ChatColor.YELLOW + "Selected Material: " + selectedMatType + ":" + selectedMatData);
		}
		//if terrain tools is enabled, perform the current tool transform.
		if (isEnabled == true){
			switch (curTool){
			case(1): //create hollow cylinder
				tTransform.drawHollowCylinder(xpos, ypos, zpos, type, data);
				break;
			case(2): //create plateau
				tTransform.drawPlateau(xpos, ypos, zpos, type, data);
				break;
			case(3): //create solid cylinder
				tTransform.drawCylinder(xpos, ypos, zpos, type, data);
				break;
			case(4): //draw a line to the target block from player
				tTransform.drawLineToTarget(xpos, ypos, zpos, type, data);
				break;
			case(5): //create a hill
				tTransform.drawHill(xpos, ypos, zpos, type, data);
				break;
			case(6): //create a valley
				tTransform.drawValley(xpos, ypos, zpos, Material.AIR, (byte) 0);
				break;
			case(7): //create upside down hill
				tTransform.drawValley(xpos, ypos, zpos, type, data);
				break;
			case(8): //levels terrain
				tTransform.level(xpos, ypos, zpos, type, data);
				break;
			}
			
		}
		
	}	
	
	public void platform(){
		Location loc = this.player.getLocation();
		tTransform.placePlatform(loc.getBlock(), this.selectedMatType, this.selectedMatData);
		
	}
	
	//set variables	
	public void setToolHeight(int th){
		this.toolHeight = th;	
	}
	
	public void setToolRadius(int tr){
		this.toolRadius = tr;	
	}
	
	public void setToolSlope(double ts){
		this.toolSlope = ts;
	}
	
	//set current tool
	public void setCurTool(int tool){
		this.curTool = tool;
	}
	
	//set floor height
		public void setFloorHeight(int fh){
			this.floorHeight = fh;
	}
	
	//get floor height
	public int getFloorHeight(){
		return this.floorHeight;
	}
	
	//get current tool
	public int getCurTool(){
		return this.curTool;
	}
	
	//get current world
	public World getWorld(){
		return this.world;
	}
	
	//return toolHeight
	public int getToolHeight(){
		return this.toolHeight;
	}

	//return toolRadius
	public int getToolRadius(){
		return this.toolRadius;
	}
	
	//return toolSlope
	public double getToolSlope(){
		return this.toolSlope;
	}
	
	//return line of sight
	public List<Block> getSight(){
		return this.sight;
	}
	
	//set selected material type & data
	public void setSelectedMaterial(Material type, byte data){
		this.selectedMatType = type;
		this.selectedMatData = data;
	}
	
	//returns selected material type
	public Material getSelectedMatType(){
		return this.selectedMatType;
	}
	
	//returns selected material data
	public byte getSelectedMatData(){
		return this.selectedMatData;
	}
	
	public void enableSelectedType(){
		this.useSelectedMatType = true;
	}
	
	public void disableSelectedType(){
		this.useSelectedMatType = false;
	}
	
	public Boolean getUseSelectedMatType(){
		return useSelectedMatType;
	}
	
	public void enableUserRadMode(){
		this.userRadMode = true;
	}
	
	public void disableUserRadMode(){
		this.userRadMode = false;
	}
	
	public Boolean getUserRadMode(){
		return userRadMode;
	}
	
	//enable terrain tools
	public void enable(){
		this.isEnabled = true;
		
	}
	//disable terrain tools
	public void disable(){
		this.isEnabled = false;
	}
	public Boolean isEnabled(){
		return this.isEnabled;
	}
	
	public void sendPlayerMessage(String message){
		
		this.player.sendMessage(message);
	}
	
	
	
}
