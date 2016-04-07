package gameTools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Put This Class into your Rescource folder packaged in the jar of your app.
 * calling getURL that way will default to that folder as "root"
 * @author ganter
 */
public class ResourceLoaderSRC {
    
    private static final ResourceLoaderSRC rl = new ResourceLoaderSRC();
    
    public static URL getURL(String fileName){
        URL url = rl.getClass().getResource(fileName);
        if (url == null) System.out.println("resource "+fileName+" not found");
        return url;
    }
    
    public static BufferedImage getImage(String fileName){
        try {
            return ImageIO.read(getURL("graphics/" + fileName));
        } catch (IOException | NullPointerException ex) {
            System.out.println("image "+fileName+" not found");
        }
        return null;
    }
    
}

