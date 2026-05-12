import java.awt.event.ActionEvent;
import javax.swing.*;

public class ElectroPeashooter extends Plant {

    private Timer shootTimer;

    public ElectroPeashooter(GamePanel parent, int x, int y) {
    super(parent, x, y);
    setHealth(300);
        this.setImg(ResourceLoader.loadImage("/images/plants/electropeashooter.gif"));
    shootTimer = new Timer(2000, (ActionEvent e) -> {
        if (getGp().getLaneZombies().get(y).size() > 0) {
            getGp().getLanePeas().get(y).add(new ElectroPea(getGp(), y, 103 + this.getX() * 100));
        }
    });
    shootTimer.start();
    }
    @Override
    public void stop() {
        shootTimer.stop(); // Dừng bắn khi cây bị xóa hoặc game kết thúc
    }
}