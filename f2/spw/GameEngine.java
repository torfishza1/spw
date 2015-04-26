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
	private ArrayList<Gun> shootings = new ArrayList<>();
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
		Iterator<Gun> s_iter = shootings.iterator();
                while(s_iter.hasNext()){
                    Gun s = s_iter.next();
                    s.proceed();
                }

		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double gr;
		for(Enemy e : enemies){
			er = e.getRectangle();

			for(Gun s : shootings ){
            gr = s.getRectangle();
            if(gr.intersects(er)){  
              gp.sprites.remove(e);
              e.enemydie();
              return;
                                }
                        }

			if(er.intersects(vr)){
				die();
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
		/* for(Gun s : shootings ){
            gr = s.getRectangle();
            if(gr.intersects(er)){  
              gp.sprites.remove(e);
              e.enemydie();
              return;
                                }
                        }
	
	*/
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
			break;
		
		case KeyEvent.VK_T:
			timer.start();
			break;
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
		case KeyEvent.VK_SPACE:
            //gp.big.setBackground(Color.BLACK);
			fire();
			//d++;
			break;
		}
	}
	 public void fire(){
            Gun s = new Gun((v.x) + (v.width/2) - 3 , v.y);
            gp.sprites.add(s);
            shootings.add(s);
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
