import java.awt.Rectangle;
import java.net.URL;
import javax.swing.ImageIcon;

public class ElectroPea extends Pea {

    public ElectroPea(GamePanel parent, int lane, int startX) {
        super(parent, lane, startX);
        
        // Load electro pea image
        try {
            this.setImg(new ImageIcon(this.getClass().getResource("images/electropea.png")).getImage());
        } catch (Exception e) {
            System.out.println("ERROR: Could not load electro pea image, using fallback");
            try {
                this.setImg(new ImageIcon(this.getClass().getResource("images/pea.png")).getImage());
            } catch (Exception e2) {
                System.out.println("ERROR: Could not load fallback pea image either");
            }
        }
    }

    @Override
    public void advance() {
        // 2. Tạo vùng va chạm cho viên đạn (28x28)
        Rectangle pRect = new Rectangle(getPosX(), 130 + getMyLane() * 120, 28, 28);
        
        // Lấy danh sách Zombie ở lane hiện tại
        var laneZombies = gp.getLaneZombies().get(getMyLane());

        // 3. Duyệt ngược để xử lý va chạm
        for (int i = laneZombies.size() - 1; i >= 0; i--) {
            Zombie z = laneZombies.get(i);
            
            // Use consistent zombie hitbox size (80px width like other peas)
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + getMyLane() * 120, 80, 120);

            if (pRect.intersects(zRect)) {
                // Gây sát thương
                z.setHealth(z.getHealth() - 300);
                
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIED BY ELECTRICITY");
                    laneZombies.remove(i);
                    GamePanel.setProgress(10);
                }

                // Xóa viên đạn ngay sau khi trúng
                gp.getLanePeas().get(getMyLane()).remove(this);
                return; 
            }
        }

        // 4. Di chuyển sang phải (+15)
        setPosX(getPosX() + 15);
        
        // Tự xóa nếu ra khỏi màn hình để tránh lag máy
        if (getPosX() > 1000) {
            gp.getLanePeas().get(getMyLane()).remove(this);
        }
    }
}