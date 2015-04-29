package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;


public class ShootNuclear extends Sprite{
	public static final int Y_TO_DIE = 60;
	
	private int step = 60; // SpeedOfShooting
	private boolean alive = true;
	
	public ShootNuclear(int x, int y) {
		super(x, y,  500,500);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
                g.fillOval(x-230,y-300,width,height);
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	

}
 
