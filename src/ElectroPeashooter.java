import java.awt.event.ActionEvent;
import javax.swing.*;

public class ElectroPeashooter extends Plant {

    private Timer shootTimer;

    public ElectroPeashooter(GamePanel parent, int x, int y) {
    super(parent, x, y);
    
    // LỆNH QUAN TRỌNG: Load ảnh GIF cho cây
    java.net.URL imgUrl = this.getClass().getResource("images/plants/electropeashooter.gif");
    System.out.println("Kết quả nạp ảnh cây: " + imgUrl);

    if (imgUrl != null) {
        this.setImg(new ImageIcon(imgUrl));
    } else {
        // Nếu hiện dòng này trong terminal nghĩa là sai đường dẫn/tên file
        System.out.println("LỖI: Không tìm thấy file electropeashooter.gif!");
    }
    
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