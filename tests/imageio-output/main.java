import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class main {

    // TODO: needs qualitative tests
    
    public static void main(String args[]) throws IOException {

		System.out.println("ImageIO exercise");
        File fileHandle = null;
        Random rand = new Random();

        // Create an temporary file
        try {
            fileHandle = File.createTempFile("random-ish", ".png");
        } catch (IOException ee) {
            ee.printStackTrace();
            throw new AssertionError("File.createTempFile IOException");
        }

        // Image dimensions
        int width = 640;
        int height = 320;

        // Create buffered image object
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        //create random image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = rand.nextInt() * 256; //alpha
                int r = rand.nextInt() * 256; //red
                int g = rand.nextInt() * 256; //green
                int b = rand.nextInt() * 256; //blue
                int p = (a << 24) | (r << 16) | (g << 8) | b; //pixel
                img.setRGB(x, y, p);
            }
        }

        // Write image to temporary file
        try {
            ImageIO.write(img, "png", fileHandle);
        } catch (IOException ee) {
            ee.printStackTrace();
            throw new AssertionError("ImageIO.write IOException");
        }
        
		Checkers.theEnd(0);
    }
}
