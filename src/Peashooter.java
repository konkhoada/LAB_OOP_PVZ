import java.awt.event.ActionEvent;
import javax.swing.*;

public class Peashooter extends Plant {

    public Timer shootTimer;


    public Peashooter(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setHealth(300);
        this.setImg(new ImageIcon(this.getClass().getResource("images/plants/peashooter.gif")));
        shootTimer = new Timer(2000, (ActionEvent e) -> { //TIME 
            //System.out.println("SHOOT");
            if (getGp().getLaneZombies().get(y).size() > 0) {
                getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }

}
