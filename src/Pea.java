import java.awt.*;
import java.net.URL;
import javax.swing.ImageIcon;

public class Pea {
    
    private int posX;
    protected GamePanel gp;
    private int myLane;
    private Image img;

    public Pea(GamePanel parent, int lane, int startX) {
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
        
        // Load pea image
        try {
            this.img = new ImageIcon(this.getClass().getResource("images/pea.png")).getImage();
        } catch (Exception e) {
            System.out.println("ERROR: Could not load pea image: " + e.getMessage());
        }
    }

    public void setImg(Image img) { this.img = img; }
    public Image getImg() { return img; }

    public void advance() {
        Rectangle pRect = new Rectangle(posX, 130 + myLane * 120, 28, 28);
        
        // Iterate backwards to avoid ConcurrentModificationException
        for (int i = gp.getLaneZombies().get(myLane).size() - 1; i >= 0; i--) {
            Zombie z = gp.getLaneZombies().get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + myLane * 120, 80, 120);
            if (pRect.intersects(zRect)) {
                z.setHealth(z.getHealth() - 300);
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIED");
                    gp.getLaneZombies().get(myLane).remove(i);
                    GamePanel.setProgress(10);
                }
                gp.getLanePeas().get(myLane).remove(this);
                return; // Exit after hitting one zombie
            }
        }
        
        posX += 15;
        
        // Remove pea if it goes off-screen
        if (posX > 1000) {
            gp.getLanePeas().get(myLane).remove(this);
        }
        
        // Remove pea if it goes off-screen
        if (posX > 1000) {
            gp.getLanePeas().get(myLane).remove(this);
        }
    }

    public int getPosX() {
        return posX;
    }

    protected void setPosX(int posX) {
        this.posX = posX;
    }

    public int getMyLane() {
        return myLane;
    }

    public void setMyLane(int myLane) {
        this.myLane = myLane;
    }
}
