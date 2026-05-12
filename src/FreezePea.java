import java.awt.*;
import java.net.URL;
import javax.swing.ImageIcon;

public class FreezePea extends Pea {

    public FreezePea(GamePanel parent, int lane, int startX) {
        super(parent, lane, startX);
        
        // Load freeze pea image
        try {
            this.setImg(ResourceLoader.loadImage("/images/freezepea.png"));
        } catch (Exception e) {
            System.out.println("ERROR: Could not load freeze pea image");
        }
    }

    @Override
    public void advance() {
        Rectangle pRect = new Rectangle(getPosX(), 130 + getMyLane() * 120, 28, 28);
        
        // Iterate backwards to avoid ConcurrentModificationException
        for (int i = gp.getLaneZombies().get(getMyLane()).size() - 1; i >= 0; i--) {
            Zombie z = gp.getLaneZombies().get(getMyLane()).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + getMyLane() * 120, 80, 120);
            if (pRect.intersects(zRect)) {
                z.setHealth(z.getHealth() - 20);
                z.slow();
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIED BY FREEZE");
                    gp.getLaneZombies().get(getMyLane()).remove(i);
                    GamePanel.setProgress(10);
                }
                gp.getLanePeas().get(getMyLane()).remove(this);
                return; // Exit after hitting one zombie
            }
        }
        
        setPosX(getPosX() + 15);
        
        // Remove pea if it goes off-screen
        if (getPosX() > 1000) {
            gp.getLanePeas().get(getMyLane()).remove(this);
        }
    }

}
