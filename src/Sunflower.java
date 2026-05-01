import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Created by Quang on 4/27/2026.
 */
public class Sunflower extends Plant {

    private Timer sunProduceTimer;

    public Sunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        this.setImg(new ImageIcon(this.getClass().getResource("images/plants/sunflower.gif")));
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
            Sun sta = new Sun(getGp(), 60 + x * 100, 110 + y * 120, 130 + y * 120);
            getGp().getActiveSuns().add(sta);
            getGp().add(sta, Integer.valueOf(1));
        });
        sunProduceTimer.start();
    }

}
