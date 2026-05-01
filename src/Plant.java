import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;

public abstract class Plant {

    private int health = 200;

    private int x;
    private int y;

    private GamePanel gp;

    private ImageIcon imgIcon; // Changed from Image to ImageIcon for GIF animation support

    public Plant(GamePanel parent, int x, int y) {
        this.x = x;
        this.y = y;
        gp = parent;
    }
    
    // Changed to accept ImageIcon instead of Image
    public void setImg(ImageIcon imgIcon) {
        this.imgIcon = imgIcon;
        // Ensure GIF animation starts
        if (imgIcon != null && imgIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // Force animation to start for GIFs
            imgIcon.setImageObserver(null);
        }
    }

    // Overloaded method for backward compatibility
    public void setImg(Image img) {
        this.imgIcon = new ImageIcon(img);
    }

    // Return ImageIcon for GIF animation support
    public ImageIcon getImgIcon() {
        return imgIcon;
    }
    
    // Return Image for backward compatibility
    public Image getImg() {
        return imgIcon != null ? imgIcon.getImage() : null;
    }
    
    public void stop() {
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }
}
