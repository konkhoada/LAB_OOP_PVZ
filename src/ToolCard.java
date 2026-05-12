import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class ToolCard extends JPanel implements MouseListener {

    private Image img;
    private int imgWidth = 64;
    private int imgHeight = 64;

    public ToolCard(Image img) {
        setSize(64, 64);
        this.img = img;
        setOpaque(false);
        addMouseListener(this);
    }

    public void setImage(Image newImg) {
        this.img = newImg;
        repaint();
    }

    public void setImageSize(int width, int height) {
        this.imgWidth = width;
        this.imgHeight = height;
        setSize(width, height);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, 0, 0, imgWidth, imgHeight, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
