package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;


public class Gun extends Sprite{
	public static final int Y_TO_DIE = 60;
	
	private int step = 40; // SpeedOfShooting
	private boolean alive = true;
	
	public Gun(int x, int y) {
		super(x, y,  5,50);
            
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(x,y-10, width, height);
              //  g.fillRect(x+6,y, width, height);
              //  g.fillRect(x+20,y+25, width, height-20);
              //  g.fillRect(x-20,y+25, width, height-20);
              //  g.fillOval(x-12,y+20,width,height-45);
              //  g.fillOval(x+12,y+20,width,height-45);
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
 
