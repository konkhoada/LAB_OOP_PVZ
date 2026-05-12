import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class ResourceLoader {

    public static ImageIcon loadIcon(String resourcePath) {
        if (resourcePath == null) {
            throw new IllegalArgumentException("Resource path cannot be null");
        }
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }

        java.net.URL url = ResourceLoader.class.getResource(resourcePath);
        if (url != null) {
            return new ImageIcon(url);
        }

        File file = new File("src" + resourcePath);
        if (file.exists()) {
            return new ImageIcon(file.getPath());
        }

        file = new File("resources" + resourcePath);
        if (file.exists()) {
            return new ImageIcon(file.getPath());
        }

        System.err.println("ResourceLoader: cannot find resource " + resourcePath);
        return new ImageIcon();
    }

    public static Image loadImage(String resourcePath) {
        return loadIcon(resourcePath).getImage();
    }
}
