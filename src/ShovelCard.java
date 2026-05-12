import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ShovelCard extends JPanel implements MouseListener, MouseMotionListener {

    private Image currentImg;
    private Image shovelpotImg;
    private Image potImg;
    private Image shovelCursorImg;
    private int imgWidth = 256;
    private int imgHeight = 256;
    private boolean isActivated = false;
    private JFrame parentFrame;
    private JLabel shovelLabel;
    private JLayeredPane layeredPane;
    private GamePanel gamePanel; // Để access colliders

    public ShovelCard(Image shovelpot, Image pot, Image shovelCursor, JFrame parent, JLayeredPane pane, GamePanel gp) {
        setSize(imgWidth, imgHeight);
        this.shovelpotImg = shovelpot;
        this.potImg = pot;
        this.shovelCursorImg = shovelCursor;
        this.currentImg = shovelpot;
        this.parentFrame = parent;
        this.layeredPane = pane;
        this.gamePanel = gp;
        setOpaque(false);
        addMouseListener(this);

        // Create label for shovel image (320x320 = 64*5)
        shovelLabel = new JLabel();
        shovelLabel.setIcon(new ImageIcon(shovelCursorImg.getScaledInstance(320, 320, Image.SCALE_SMOOTH)));
        shovelLabel.setSize(320, 320);
        shovelLabel.setVisible(false);

        // FIX: shovelLabel nằm ở DRAG_LAYER (lớp trên cùng) nên nó sẽ
        // "nuốt" hết mouse events, khiến layeredPane không nhận được
        // mouseMoved → xẻng bị đứng yên.
        // Giải pháp: đăng ký listener trực tiếp lên shovelLabel để nó
        // forward events cho ShovelCard xử lý.
        shovelLabel.addMouseMotionListener(this);
        shovelLabel.addMouseListener(this);
    }

    public void setImageSize(int width, int height) {
        this.imgWidth = width;
        this.imgHeight = height;
        setSize(width, height);
        repaint();
    }

    @Override
    public boolean contains(int x, int y) {
        // Only consider clicks within the image bounds
        return x >= 0 && x <= imgWidth && y >= 0 && y <= imgHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImg != null) {
            g.drawImage(currentImg, 0, 0, imgWidth, imgHeight, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Chỉ xử lý click từ ShovelCard gốc (lần click đầu kích hoạt xẻng)
        // hoặc từ shovelLabel (lần click thứ hai để dùng xẻng).
        // Bỏ qua các click không thuộc phạm vi ảnh gốc khi chưa kích hoạt.
        if (!isActivated) {
            if (e.getX() < 0 || e.getX() > imgWidth || e.getY() < 0 || e.getY() > imgHeight) {
                return;
            }
        }

        // Chuyển tọa độ chuột về tọa độ của LayeredPane
        Point clickPoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), layeredPane);

        if (!isActivated) {
            // Kích hoạt xẻng
            isActivated = true;
            currentImg = potImg; // Đổi ảnh thẻ thành cái chậu trống

            if (!layeredPane.isAncestorOf(shovelLabel)) {
                // Đặt xẻng ở lớp cao nhất để luôn hiển thị trên cùng
                layeredPane.add(shovelLabel, JLayeredPane.DRAG_LAYER);
            }
            shovelLabel.setVisible(true);

            // Ẩn con trỏ chuột thực
            BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Cursor invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "invisible");
            parentFrame.setCursor(invisibleCursor);

            // Lắng nghe trên layeredPane (bắt sự kiện khi chuột ra ngoài shovelLabel)
            layeredPane.addMouseMotionListener(this);
            layeredPane.addMouseListener(this);

            // Cập nhật vị trí ngay lập tức tại điểm click
            updateShovelPosition(e);

            repaint();
        } else {
            // KHI XẺNG ĐANG ĐƯỢC CẦM VÀ NGƯỜI DÙNG CLICK LẦN NỮA:

            // Tạm ẩn xẻng để "nhìn xuyên" xuống lớp dưới
            shovelLabel.setVisible(false);

            // Duyệt qua tất cả colliders để tìm plant tại vị trí click
            boolean plantFound = false;
            for (int i = 0; i < 45; i++) {
                // Tính toán vị trí collider
                int colliderX = 44 + (i % 9) * 100;
                int colliderY = 109 + (i / 9) * 120;
                int colliderWidth = 100;
                int colliderHeight = 120;
                
                // Kiểm tra xem click có trong phạm vi collider không
                if (clickPoint.x >= colliderX && clickPoint.x <= colliderX + colliderWidth &&
                    clickPoint.y >= colliderY && clickPoint.y <= colliderY + colliderHeight) {
                    
                    // Lấy collider từ GamePanel
                    Collider[] colliders = gamePanel.getColliders();
                    if (colliders != null && colliders[i] != null && colliders[i].assignedPlant != null) {
                        colliders[i].removePlant();
                        System.out.println("Đã đào cây tại vị trí " + i);
                        plantFound = true;
                        break;
                    }
                }
            }
            
            if (!plantFound) {
                System.out.println("Không có cây để đào tại vị trí click");
            }

            // Sau khi xử lý xong, giải phóng xẻng
            deactivateShovel();
        }
    }

    // Tách hàm cập nhật vị trí để dùng chung
    private void updateShovelPosition(MouseEvent e) {
        if (isActivated && shovelLabel != null) {
            // Chuyển đổi tọa độ từ component nguồn sang tọa độ của layeredPane
            // (hoạt động đúng dù e.getComponent() là layeredPane hay shovelLabel)
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), layeredPane);

            // Căn lề để tâm cái xẻng nằm ở đầu con trỏ (160 là nửa kích thước 320)
            int x = p.x - 160;
            int y = p.y - 160;
            shovelLabel.setLocation(x, y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isActivated) {
            updateShovelPosition(e);
            
            // Chuyển tọa độ về layeredPane
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), layeredPane);
            
            // Tạm ẩn shovelLabel để "nhìn xuyên"
            shovelLabel.setVisible(false);
            
            // Tìm component ở vị trí hiện tại
            Component target = SwingUtilities.getDeepestComponentAt(layeredPane, p.x, p.y);
            
            // TODO: Thêm logic highlight plant nếu Plant extends Component
            // Hiện tại Plant là abstract class độc lập, không extend Component
            // Nên không thể detect bằng instanceof từ getDeepestComponentAt
            
            // Hiện lại shovelLabel
            shovelLabel.setVisible(true);
        }
    }

    // Hàm hủy kích hoạt để code sạch hơn
    private void deactivateShovel() {
        isActivated = false;
        currentImg = shovelpotImg;
        shovelLabel.setVisible(false);
        parentFrame.setCursor(Cursor.getDefaultCursor());

        // Xóa listener khỏi layeredPane
        layeredPane.removeMouseMotionListener(this);
        layeredPane.removeMouseListener(this);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}