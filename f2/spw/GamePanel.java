package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.IOException;
import java.io.File;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	public BufferedImage bg;
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();


	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
		try{
			//bg = ImageIO.read(new File("f2/spw/Background.png"));
			loadImage();
		}
		catch(IOException e){
			
		}
		
	}
	 private void loadImage() throws IOException{
        bg = ImageIO.read(new File("f2/spw/Background.png"));
    } 

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big.drawImage(bg, 0, 0, 400, 600, null);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("Score : %08d", reporter.getScore()), 280, 20);
		big.drawString(String.format("Nuclear : %01d", reporter.getNuclear()), 20, 20);
		big.drawString(String.format("Fire : %01d", reporter.getNumfire()), 150, 20);
		big.drawString(String.format("Killed : %01d", reporter.getenedie()), 150, 40);
		big.drawString(String.format("High Score : %08d", reporter.getMainscore()), 250, 40);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
