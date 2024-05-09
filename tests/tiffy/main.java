import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class main {

	private static int writeTIFF(File tiffFile, BufferedImage image) {
        try {
        
            // Instantiate TIFF ImageWriter
            ImageWriter writer = ImageIO.getImageWritersByFormatName("TIFF").next();

            // Set TIFF compression
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionType("LZW");
                System.out.println("writeTIFF: Compression is LZW.");
            }

            // Create ImageOutputStream
            ImageOutputStream ios = ImageIO.createImageOutputStream(tiffFile);
            writer.setOutput(ios);

            // Write the image
            writer.write(null, new javax.imageio.IIOImage(image, null, null), param);

            // Cleanup
            ios.close();
            writer.dispose();
            System.out.println("writeTIFF: TIFF file created successfully.");

        } catch (IOException e) {
        
        	System.out.println("*** ERROR, writeTIFF: IOException occured");
            e.printStackTrace();
            return 1;
            
        }
        
        return 0;
    }
    
    private static int readTIFF(File tiffFile, int expType, int expWidth, int expHeight, BufferedImage expImage) {
        try {
        
            // Create ImageInputStream
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(tiffFile);

            // Instantiate TIFF ImageReader
            ImageReader reader = ImageIO.getImageReadersByFormatName("TIFF").next();
            reader.setInput(imageInputStream);

            // Validate image parameters.
            int numImages = reader.getNumImages(true);
            if (reader.getNumImages(true) != 1) {
            	System.out.printf("*** ERROR, readTIFF: Number of images in TIFF should be 1, observed %d\n", numImages);
            	return 1;
            }
           int obsWidth = reader.getWidth(0);
            if (obsWidth != expWidth) {
            	System.out.printf("*** ERROR, readTIFF: TIFF image width should be %d, observed %d\n", expWidth, obsWidth);
            	return 1;
            }
            int obsHeight = reader.getHeight(0);
            if (obsHeight != expHeight) {
            	System.out.printf("*** ERROR, readTIFF: TIFF image height should be %d, observed %d\n", expHeight, obsHeight);
            	return 1;
            }
            
            // Validate image data.
            BufferedImage obsImage = reader.read(0);
		    for (int y = 0; y < expHeight; y++) {
		        for (int x = 0; x < expWidth; x++) {
		            if (obsImage.getRGB(x, y) != expImage.getRGB(x, y)) {
				    	System.out.printf("*** ERROR, readTIFF: TIFF image pixel at (%d, %d) should be %d, observed %d\n",
				    	                  x, y, expImage.getRGB(x, y), obsImage.getRGB(x, y));
				    	return 1;
		            }	            
		        }
		    }

            // Close resources
            reader.dispose();
            imageInputStream.close();
            System.out.println("readTIFF: TIFF file read successfully.");
            
        } catch (IOException e) {
        
        	System.out.println("*** ERROR, readTIFF: IOException occured");
            e.printStackTrace();
            return 1;
            
       }
       
       return 0;	
    }

    public static void main(String[] args) {
        File tiffFile = new File("test.tiff");
    	int errorCount = 0;
    	int height = 100;
    	int width = 100;
    	
    	// Create a buffered image.
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Set pixels in a checkerboard pattern.
                if ((x / 50 + y / 50) % 2 == 0) {
                    image.setRGB(x, y, 0xFFFFFF); // White
                } else {
                    image.setRGB(x, y, 0x000000); // Black
                }
            }
        }
        
        // Write buffered image.
        errorCount += writeTIFF(tiffFile, image);
        assert errorCount == 0;
        
        // Read buffered image.
        errorCount += readTIFF(tiffFile, BufferedImage.TYPE_INT_RGB, width, height, image);
        assert errorCount == 0;
        
        // Delete file.
        tiffFile.delete();
	}

}

