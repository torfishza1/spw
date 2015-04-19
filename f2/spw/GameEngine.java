package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Item> items = new ArrayList<Item>();
	private SpaceShip v;	
	
	private Timer timer;
	
	private int nuclear_count=0;
	private long score = 0;
	private double difficulty = 0.2;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		Item f = new Item((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
		gp.sprites.add(f);
		items.add(f);

	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}
		  Iterator<Item> item_s = items.iterator();
		while(item_s.hasNext()){
			Item f = item_s.next();
			f.proceed();
			
			  if(!f.isAlive()){
				item_s.remove();
				gp.sprites.remove(f);
				score += 0;
			}   
		}  
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				//restartG();
				return;
			}

		}
		for(Item f : items){
			er = f.getRectangle();
			if(er.intersects(vr)){
				score += 10000;
				//die();
				//restartG();
				return;
			}
			
		}
	}
	
	public void checkcase(){
		if(score/100000>=1&&score%100000==0)
			nuclear_count++;
	}
	public int getNuclear(){
		return nuclear_count;
	}
	public void die(){
		timer.stop();
	}

	/* public void restartG(){
		die();
		timer.restart();
		for(Enemy e : enemies){
			e.enemydie();
		}
	} */

	public boolean running(){
		return timer.isRunning();
	}


	
	void controlEngine(KeyEvent e){
		//Enemy d = new Enemy ();
		switch (e.getKeyCode()){
		case KeyEvent.VK_P:
		//	for(Enemy e : enemies){
			//while (timer.isRunning()==true) {
			timer.stop();	
			
		
		case KeyEvent.VK_T:
			timer.start();
		//if(timer.isRunning == 0)
		//	timer.start();
		}
	}
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
		case KeyEvent.VK_DOWN:
			v.movey(1);
			break;
		case KeyEvent.VK_UP:
			v.movey(-1);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		controlEngine(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
