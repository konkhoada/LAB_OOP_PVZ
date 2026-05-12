import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame {

    enum PlantType {
        None,
        Sunflower,
        Peashooter,
        FreezePeashooter,
        ElectroPeashooter
    }

    //PlantType activePlantingBrush = PlantType.None;

    public GameWindow() {
        System.out.println("=== GameWindow() constructor started ===");
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        System.out.println("Creating GamePanel...");
        GamePanel gp = new GamePanel(sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, Integer.valueOf(0));
        System.out.println("GamePanel added successfully");

        PlantCard sunflower = new PlantCard(ResourceLoader.loadImage("/images/cards/card_sunflower.png"));
        sunflower.setLocation(110, 8);
        sunflower.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Sunflower);
        });
        getLayeredPane().add(sunflower, Integer.valueOf(3));

        PlantCard peashooter = new PlantCard(ResourceLoader.loadImage("/images/cards/card_peashooter.png"));
        peashooter.setLocation(175, 8);
        peashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Peashooter);
        });
        getLayeredPane().add(peashooter, Integer.valueOf(3));

        PlantCard freezepeashooter = new PlantCard(ResourceLoader.loadImage("/images/cards/card_freezepeashooter.png"));
        freezepeashooter.setLocation(240, 8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.FreezePeashooter);
        });
        getLayeredPane().add(freezepeashooter, Integer.valueOf(3));

        PlantCard electroCard = new PlantCard(ResourceLoader.loadImage("/images/cards/card_electropeashooter.png"));
        electroCard.setLocation(305, 8); 
        electroCard.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.ElectroPeashooter);
        });
        getLayeredPane().add(electroCard, Integer.valueOf(3));
        
        // Add Shovel card at top-right corner minus 150 pixels
        ShovelCard shovelCard = new ShovelCard(
            ResourceLoader.loadImage("/images/GameTool/Shovel/Shovelpot.png"),
            ResourceLoader.loadImage("/images/GameTool/Shovel/Pot.png"),
            ResourceLoader.loadImage("/images/GameTool/Shovel/Shovel.png"),
            this,
            getLayeredPane(),
            gp
        );
        shovelCard.setLocation(500, -100);
        getLayeredPane().add(shovelCard, Integer.valueOf(3));
        
        getLayeredPane().add(sun, Integer.valueOf(2));
        System.out.println("Setting window properties...");
        setResizable(false);
        System.out.println("Setting visible...");
        setVisible(true);
        System.out.println("=== GameWindow() constructor completed successfully ===");
    }

    public GameWindow(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0, 0);
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu, Integer.valueOf(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }

    static GameWindow gw;

    public static void begin() {
        try {
            System.out.println("=== GameWindow.begin() called ===");
            System.out.println("Disposing old GameWindow...");
            gw.dispose();
            System.out.println("Old window disposed, creating new GameWindow...");
            gw = new GameWindow();
            System.out.println("New GameWindow created successfully");
        } catch (Exception e) {
            System.err.println("Error starting game:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        gw = new GameWindow(true);
    }

}
