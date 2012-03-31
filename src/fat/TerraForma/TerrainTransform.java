package fat.TerraForma;


//import java.util.Random;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class TerrainTransform {
	
	private TerraFormaPlayer player;
	private List<Block> blockList = new ArrayList<Block>();	
	
	public TerrainTransform(TerraFormaPlayer player){
		
		this.player = player;	
	}
		
	//makes a hollow cylinder
	public void drawHollowCylinder(int xpos, int ypos, int zpos, Material type, byte data) {
			
		int height = this.player.getToolHeight();
		int radius = this.player.getToolRadius();
		int f = 1 - radius;
		int ddF_x = 1;
		int ddF_y = -2 * radius;
		int x = 0;
		int stretch = 2;
		
			
		//set the 4 outside cardinal blocks
		for (int i = 1; i <= height; i++){
			setBlock(xpos, ypos + i, zpos + radius, type, data);
			setBlock(xpos, ypos + i, zpos - radius, type, data);
			setBlock(xpos + (stretch * radius), ypos + i, zpos, type, data);
			setBlock(xpos - (stretch * radius), ypos + i, zpos, type, data);
		}
		
		//draw around the edges
		while (x < radius) {
			if (f >= 0) {
				radius--;
				ddF_y += 2;
				f += ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			//loop for adding height
			for (int i = 1; i <= height; i++){
				setBlock(xpos - x, ypos + i, zpos + radius, type, data);
				setBlock(xpos - x, ypos + i, zpos - radius, type, data);
				setBlock(xpos - (stretch * radius), ypos + i, zpos + x, type, data);
				setBlock(xpos - (stretch * radius), ypos + i, zpos - x, type, data);
				//waiting(10);
				setBlock(xpos + x, ypos + i, zpos + radius, type, data);
				setBlock(xpos + x, ypos + i, zpos - radius, type, data);
				setBlock(xpos + (stretch * radius), ypos + i, zpos + x, type, data);
				setBlock(xpos + (stretch * radius), ypos + i, zpos - x, type, data);
			}
		}		
	}
		
	//makes a solid cylinder * from Bresenham's algorithm
	public void drawCylinder(int xpos, int ypos, int zpos, Material type, byte data) {
		
		int height = this.player.getToolHeight();
		int radius = this.player.getToolRadius();
		int f = 1 - radius;
		int ddF_x = 1;
		int ddF_y = -2 * radius;
		int x = 0;
		
		//set the 4 outside cardinal blocks
		setBlock(xpos, ypos, zpos + radius, type, data);
		setBlock(xpos, ypos, zpos - radius, type, data);
		setBlock(xpos + radius, ypos, zpos, type, data);
		setBlock(xpos - radius, ypos, zpos, type, data);
		 
		// fill in the 0 gap
		for (int i = 1; i <= height; i++){
			lineTo(xpos - radius, zpos, xpos + radius, zpos, ypos + i, type, data);
		}
		
		//draw lines to the edges
		while (x < radius) {
			if (f >= 0) {
				radius--;
				ddF_y += 2;
				f += ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			//loop for adding height
			for (int i = 1; i <= height; i++){
				lineTo(xpos - x, zpos + radius, xpos + x, zpos + radius, ypos + i, type, data);
				lineTo(xpos - x, zpos - radius, xpos + x, zpos - radius, ypos + i, type, data);
				lineTo(xpos - radius, zpos + x, xpos + radius, zpos + x, ypos + i, type, data);
				lineTo(xpos - radius, zpos - x, xpos + radius, zpos - x, ypos + i, type, data);
			}
		}
	}
	
	//makes a single solid cylinder
	public void drawSingleCylinder(int xpos, int ypos, int zpos, Material type, byte data) {
			
		int radius =  this.player.getToolRadius();
		int f = 1 - radius;
		int ddF_x = 1;
		int ddF_y = -2 * (int) radius;
		int x = 0;
			
		//set the 4 outside cardinal blocks
		setBlock(xpos, ypos, zpos + radius, type, data);
		setBlock(xpos, ypos, zpos - radius, type, data);
		setBlock(xpos + radius, ypos, zpos, type, data);
		setBlock(xpos - radius, ypos, zpos, type, data);
			 
		// fill in the 0 gap
		lineTo(xpos - radius, zpos, xpos + radius, zpos, ypos, type, data);
			
		//draw lines to the edges
		while (x < radius) {
			if (f >= 0) {
				radius--;
				ddF_y += 2;
				f += ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			lineTo(xpos - x, zpos + radius, xpos + x, zpos + radius, ypos, type, data);
			lineTo(xpos - x, zpos - radius, xpos + x, zpos - radius, ypos, type, data);
			lineTo(xpos - radius, zpos + x, xpos + radius, zpos + x, ypos, type, data);
			lineTo(xpos - radius, zpos - x, xpos + radius, zpos - x, ypos, type, data);
		}
	}
	
	//makes a plateau only of blocks at the same level within a square 
	public void drawPlateau(int xpos, int ypos, int zpos, Material type, byte data){
		
		for(int i = 1; i <= this.player.getToolHeight(); i++){
			for (int j = 0; j < this.player.getToolRadius(); j++){
				for (int k = 0; k < this.player.getToolRadius(); k++){
					setBlock(this.player.getWorld().getBlockAt(xpos + j, ypos + i, zpos + k), this.player.getWorld().getBlockAt(xpos + j, ypos, zpos + k).getType(), this.player.getWorld().getBlockAt(xpos + j, ypos, zpos + k).getData());
				}
			}
		}		
	}
	
	//creates a hill.
	public void level(int xpos, int ypos, int zpos, Material type, byte data){
		for(int y = 0; y <= (ypos - this.player.getFloorHeight()); y++){
			drawSingleCylinder(xpos, ypos - y, zpos, type, data);
		}
		for(int y = 1; y < (this.player.getToolHeight() + 1); y++){
			drawSingleCylinder(xpos, ypos + y, zpos, Material.AIR, (byte) 0);
		}
	}
	
	//draws a line of blocks from the player to 2 blocks under the block they are looking at.
	public void drawLineToTarget(int xpos, int ypos, int zpos, Material type, byte data){
		for(int i = 0; i < this.player.getSight().size(); i++){
			setBlock(this.player.getSight().get(i).getX(), this.player.getSight().get(i).getY() - 2, this.player.getSight().get(i).getZ(), type, data);
		}
	}
	
	//Deprecated method, creates a hill.
	public void drawHillDeprecated(int xpos, int ypos, int zpos, Material type, byte data){
		//TODO add user options for randomized terrain
		//Random randNumber = new Random();
		double tempRadius = this.player.getToolRadius();
		int orgRadius = (int) tempRadius;
		double slope = this.player.getToolSlope();
		int height = this.player.getToolHeight();
		for(int y = 1; y <= height ; y++){
			//based on quadratic curve formula
			tempRadius = 5 * (Math.sqrt(Math.abs(y - height) / slope));
			//based on some random math i came up with
			//tempRadius = Math.sqrt(y / slope) - y + height;
			//TODO add user options for randomized terrain
			//tempRadius = Math.sqrt(y / slope) - y + height- randNumber.nextInt(2) + randNumber.nextInt(2);
			this.player.setToolRadius((int)tempRadius);
			if (tempRadius <= 0){ break; } //if radius is < 0 don't draw anything else.
			else if (tempRadius > orgRadius){
				this.player.setToolRadius(orgRadius);
				drawSingleCylinder(xpos, ypos + y, zpos, type, data);
			}else {
				drawSingleCylinder(xpos, ypos + y, zpos, type, data);
			}
		}
		this.player.setToolRadius(orgRadius);
	}
	
	//creates a hill using containsBlocks method
	//will not override existing blocks underneath.
	public void drawHill(int xpos, int ypos, int zpos, Material type, byte data){
		//TODO add user options for randomized terrain?
		//Random randNumber = new Random();
		double tempRadius = this.player.getToolRadius();
		int orgRadius = (int) tempRadius;
		double slope = this.player.getToolSlope();
		double height = this.player.getToolHeight();
		double floor = this.player.getFloorHeight();
		Boolean userRadMode = this.player.getUserRadMode();
		for(double y = floor; y <= (ypos+1) ; y++){
			if(y > (height + floor)){
				break;
			}

			//the bread & butter of the method, the hill shape.
			tempRadius = slope * Math.acos((2 * (y - floor - (height / 2)) / height));
			
			this.player.setToolRadius((int)tempRadius);
			//if radius is < 0 don't draw anything else.
			if (tempRadius <= 0){
				break;
			}else if (tempRadius > orgRadius && userRadMode == true){
				
				/* 
				 * TODO
				 * How should user defined radius be handled inside the hill method?
				 * Should it be left up to the tempRadius algorithm and ignored?
				 * Or should tempRadius be reset to original if tempRadius is larger than the original radius?
				 * If this is not the case, this check is not needed.
				 * 
				 */
				
				this.player.setToolRadius(orgRadius);
				
				containsBlocks(xpos, (int) y, zpos);
				Iterator<Block> it = this.blockList.iterator();
				while(it.hasNext()){
					setBlock(it.next(), type, data);
				}
			}
			else {
				containsBlocks(xpos, (int) y, zpos);
				Iterator<Block> it = this.blockList.iterator();
				while(it.hasNext()){
					setBlock(it.next(), type, data);
				}
			}
			}
		this.blockList.clear();
		this.player.setToolRadius(orgRadius);		
	}
	
	//creates a valley.
	public void drawValley(int xpos, int ypos, int zpos, Material type, byte data){
		/*
		 * old valley code
		 * 
		Random randNumber = new Random();
		int tempRadius = this.player.getToolRadius();
		int orgRadius = tempRadius;
		type = Material.AIR;
		for(int i = 0; i <= this.player.getToolHeight() ; i++){
			this.player.setToolRadius(tempRadius);
			tempRadius -= randNumber.nextInt(3);
			if (tempRadius == 0){ break; }
			drawSingleCylinder(xpos, ypos - i, zpos, type, data); //what is data for air??
		}
		this.player.setToolRadius(orgRadius);
		*/
		//TODO add user options for randomized terrain
		// Random randNumber = new Random();
		double tempRadius = this.player.getToolRadius();
		int orgRadius = (int) tempRadius;
		double slope = this.player.getToolSlope();
		int height = this.player.getToolHeight();
		type = Material.AIR;
		for (int y = 0; y <= height; y++) {
			// based on quadratic curve formula
			tempRadius = 5 * (Math.sqrt(Math.abs(y - height) / slope));
			// based on some random math i came up with
			// tempRadius = Math.sqrt(y / slope) - y + height;
			// TODO add user options for randomized terrain
			// tempRadius = Math.sqrt(y / slope) - y + height- randNumber.nextInt(2) +
			// randNumber.nextInt(2);
			this.player.setToolRadius((int) tempRadius);
			if (tempRadius <= 0) {
				break;
			} // if radius is < 0 don't draw anything else.
			else if (tempRadius > orgRadius) {
				this.player.setToolRadius(orgRadius);
				drawSingleCylinder(xpos, ypos - y, zpos, type, data);
			} else {
				drawSingleCylinder(xpos, ypos - y, zpos, type, data);
			}
		}
		this.player.setToolRadius(orgRadius);
		
	}
	
	//draws a line between two points
	public void lineTo(int x0, int y0, int x1, int y1, int ypos, Material type, byte data) {
		int dx = Math.abs(x1 - x0), sx = x0 < x1 ? 1 : -1;
		int dy = Math.abs(y1 - y0), sy = y0 < y1 ? 1 : -1;
		int err = (dx > dy ? dx : -dy)/2;
		while (true) {
			setBlock(x0, ypos, y0, type, data);
			if (x0 == x1 && y0 == y1) break;
			int e2 = err;
			if (e2 > -dx) { err -= dy; x0 += sx; }
			if (e2 < dy) { err += dx; y0 += sy; }
			
		}
	}

	//sets blocks
	private void setBlock(Block block, Material type, byte data){
		block.setType(type);
		block.setData(data);
	}
	
	//sets blocks
	private void setBlock(int x, int y, int z, Material type, byte data){
		
		this.player.getWorld().getBlockAt(x, y, z).setType(type);
		this.player.getWorld().getBlockAt(x, y, z).setData(data);
	}
	
	// checks to see if the block is surrounded on all 4 sides, else return false.
	public Boolean hasBlocksNextTo(int x, int y, int z){
		
		if(this.player.getWorld().getBlockAt(x + 1, y, z).getType() != Material.AIR && this.player.getWorld().getBlockAt(x - 1, y, z).getType() != Material.AIR && this.player.getWorld().getBlockAt(x, y, z + 1).getType() != Material.AIR && this.player.getWorld().getBlockAt(x, y, z - 1).getType() != Material.AIR){
			//this.player.sendPlayerMessage("xyz: " + x + y + z);
			return true;
		}else{
			return false;
		}
	}
	
	public void containsBlocks(int xpos, int ypos, int zpos) {
		
		/*TODO
		 * This method might be more useful if it checked for the surface of blocks connected
		 * to the clicked block within a boundary shape, instead of iterating through all the blocks. 
		 */
		
		int radius =  this.player.getToolRadius();
		int f = 1 - radius;
		int ddF_x = 1;
		int ddF_y = -2 * (int) radius;
		int x = 0;
			
		
		//checks the 4 outside cardinal blocks, if != Air push to array?
		if(this.player.getWorld().getBlockAt(xpos + radius, ypos, zpos).getType() == Material.AIR){
			this.blockList.add(this.player.getWorld().getBlockAt(xpos + radius, ypos, zpos));
			//this.blockContainer[0] = this.player.getWorld().getBlockAt(xpos + radius, ypos, zpos);
		}else if(this.player.getWorld().getBlockAt(xpos - radius, ypos, zpos).getType() == Material.AIR){
			this.blockList.add(this.player.getWorld().getBlockAt(xpos - radius, ypos, zpos));
			//this.blockContainer[1] = this.player.getWorld().getBlockAt(xpos - radius, ypos, zpos);
		}else if(this.player.getWorld().getBlockAt(xpos, ypos, zpos + radius).getType() == Material.AIR){
			this.blockList.add(this.player.getWorld().getBlockAt(xpos, ypos, zpos + radius));
			//this.blockContainer[2] = this.player.getWorld().getBlockAt(xpos, ypos, zpos + radius);
		}else if(this.player.getWorld().getBlockAt(xpos, ypos, zpos - radius).getType() == Material.AIR){
			this.blockList.add(this.player.getWorld().getBlockAt(xpos, ypos, zpos - radius));
			//this.blockContainer[3] = this.player.getWorld().getBlockAt(xpos, ypos, zpos - radius);
		}			 
		// fill in the 0 gap
		hasBlocksInLine(xpos - radius, zpos, xpos + radius, zpos, ypos);
		
		
		//draw lines to the edges
		while (x < radius) {
			if (f >= 0) {
				radius--;
				ddF_y += 2;
				f += ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			hasBlocksInLine(xpos - x, zpos + radius, xpos + x, zpos + radius, ypos);
			hasBlocksInLine(xpos - x, zpos - radius, xpos + x, zpos - radius, ypos);
			hasBlocksInLine(xpos - radius, zpos + x, xpos + radius, zpos + x, ypos);
			hasBlocksInLine(xpos - radius, zpos - x, xpos + radius, zpos - x, ypos);
		}

	}
	
	
	//checks a line between two points
		public void hasBlocksInLine(int x0, int y0, int x1, int y1, int ypos) {
			int dx = Math.abs(x1 - x0), sx = x0 < x1 ? 1 : -1;
			int dy = Math.abs(y1 - y0), sy = y0 < y1 ? 1 : -1;
			int err = (dx > dy ? dx : -dy)/2;

			while (true) {
				if(this.player.getWorld().getBlockAt(x0, ypos, y0).getType() == Material.AIR){
					this.blockList.add(this.player.getWorld().getBlockAt(x0, ypos, y0));
				}
				if (x0 == x1 && y0 == y1) break;
				int e2 = err;
				if (e2 > -dx) { err -= dy; x0 += sx; }
				if (e2 < dy) { err += dx; y0 += sy; }
				
			}
			
		}
		
		//
		//waits a specified time. Could be used later on for randomization elements. 
		public static void waiting (int n){
	        
	        long t0, t1;

	        t0 =  System.currentTimeMillis();

	        do{
	            t1 = System.currentTimeMillis();
	        }
	        while ((t1 - t0) < (n * 1000));
	    }
}
