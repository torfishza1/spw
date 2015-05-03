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
	private ArrayList<Gun> shootings = new ArrayList<Gun>();
	private ArrayList<PickNuclear> pickNuclears = new ArrayList<PickNuclear>();
	private ArrayList<ShootNuclear> nuclears = new ArrayList<>();
        
	private SpaceShip v;	
	private ShootNuclear u ;
	private Timer timer;
	
	private int nucount=0;
	private long score = 0;
	private long mainscore = 0;
	private int d = 0;
	private int enedie = 0;
	private double difficulty = 0.1;
	private double difficulty2 = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(60, new ActionListener() {
			
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
		
		gp.sprites.add(e);
		enemies.add(e);
		

	}
	private void generateItem(){
		Item f = new Item((int)(Math.random()*400), 10);
		PickNuclear pn = new PickNuclear((int)(Math.random()*400), 10);
		gp.sprites.add(f);
		items.add(f);
		gp.sprites.add(pn);
		pickNuclears.add(pn);

	}


	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
			//generateItem();
		}
		if(Math.random() < difficulty2){
			//generateEnemy();
			generateItem();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			//if(timer.isRunning==1){
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				//score += 100;
				//checkcase();
			
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
		Iterator<PickNuclear> pickNuclear_s = pickNuclears.iterator();
			while(pickNuclear_s.hasNext()){
				PickNuclear pn = pickNuclear_s.next();
				pn.proceed();
			
			 if(!pn.isAlive()){
				pickNuclear_s.remove();
				gp.sprites.remove(pn);
				score += 0;

			}   
		}  
		Iterator<Gun> s_iter = shootings.iterator();
                while(s_iter.hasNext()){
                    Gun s = s_iter.next();
                    s.proceed();
                }
        Iterator<ShootNuclear> u_iter = nuclears.iterator();
                while(u_iter.hasNext()){
                    ShootNuclear nu = u_iter.next();
                    nu.proceed();
                    
                }
		
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double gr;
		Rectangle2D.Double pr;
		Rectangle2D.Double sr;
		for(Enemy e : enemies){
			er = e.getRectangle();

			for(Gun s : shootings ){
            gr = s.getRectangle();
            if(gr.intersects(er)){  
              gp.sprites.remove(e);
              score += 100;
				checkcase();
              e.enemydie();
              enedie++;
              return;
                                }
                        }

			for(ShootNuclear nu : nuclears ){
                                sr = nu.getRectangle();
                                if(sr.intersects(er)){  
                                    gp.sprites.remove(e);
                                    e.enemydie();
                                    score += 100;
									checkcase();
                                    enedie++;
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
				//checkcase();
				nucount++;
				gp.sprites.remove(f);
              	f.enemydie();
				//die();
				//restartG();
				return;
			}

		}
		for(PickNuclear pn : pickNuclears){
			pr = pn.getRectangle();
			if(pr.intersects(vr)){
				//score += 10000;
				//checkcase();
				nucount++;
				gp.sprites.remove(pn);
              	pn.enemydie();
				//die();
				return;
			}

		}
		
		
	}
	public void checkcase(){
	if(score/8000>=1&&score%8000==0)
		nucount++;
	
	}
	public int getNuclear(){
	return nucount;
	}
	public void nuClear(){
		if(nucount>=1){
			nucount--;
			firenu();
			//gp.big.setBackground(Color.RED);
			for(Enemy e : enemies){
			e.enemydie();
			enedie++;
			}
		}
	}

	public int getNumfire(){
		return d ;
	}
	public long getMainscore(){
		return mainscore ;
	}
	public int getenedie(){
		return enedie;
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

	//public boolean running(){
	//	return timer.isRunning();
	//}

	void RestartGame(){
		if(timer.isRunning()!=true){
		if(mainscore <= score)
			mainscore = score ;
		//firenu();
		for(Enemy e : enemies){
			gp.sprites.remove(e);
            e.enemydie();
            
        }
		for(Item f : items){
			gp.sprites.remove(f);
            f.enemydie();
        }
        for(PickNuclear pn : pickNuclears){
        	gp.sprites.remove(pn);
            pn.enemydie();
        }
		
		d = 0;
		score = 0;
		enedie =0;
		nucount =0;
		difficulty = 0.1;
		timer.stop();
		timer.restart();
		timer.start();
		score = 0;
	}
}
	
	void controlEngine(KeyEvent e){
		//Enemy d = new Enemy ();
		switch (e.getKeyCode()){
		case KeyEvent.VK_P:
		//	for(Enemy e : enemies){
			//while (timer.isRunning()==true) {
			timer.stop();	
			break;
		
		case KeyEvent.VK_R:
		//	for(Enemy e : enemies){
			//while (timer.isRunning()==true) {

			RestartGame();	
			break;
		
		case KeyEvent.VK_T:
			timer.start();
			break;
		//if(timer.isRunning == 0)
		//	timer.start();
		case KeyEvent.VK_SPACE:
			fire();
			d++;
			break;

		case KeyEvent.VK_N:
			nuClear();

			
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
	 public void fire(){
            Gun s = new Gun((v.x) + (v.width/2) - 3 , v.y);
            gp.sprites.add(s);
            shootings.add(s);
        }
     public void firenu(){
            ShootNuclear nu = new ShootNuclear((v.x) + (v.width/2) - 3 , v.y);
            gp.sprites.add(nu);
            nuclears.add(nu);
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
