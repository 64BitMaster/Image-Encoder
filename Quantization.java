package encoder;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Quantization {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	
    	// Create new image that reads from baboon.pgma
    	Image original = new Image("baboon");
    	
    	// Encode original image with new max value
    	Image encodedImage12 = new Image(original, 12, true, null);
    	Image encodedImage2 = new Image(original, 2, true, null);
    	
    	// Decode encoded image with original photos max value
    	Image decodedImage12 = new Image(encodedImage12, original.maxValue, false, "baboon");
    	Image decodedImage2 = new Image(encodedImage2, original.maxValue, false, "baboon");
    	
    	// Create error image to show differences between original and decoded
    	Image errorImage12 = new Image(original, decodedImage12, 12);
    	Image errorImage2 = new Image(original, decodedImage2, 2);
    	
    	// Find distortion created during encoding and decoding
    	int distortion12 = (decodedImage12.calculateDistortion(original));
    	int distortion2 = (decodedImage2.calculateDistortion(original));
    	
    	// Save images in .pgma format
    	encodedImage12.saveFile();
    	encodedImage2.saveFile();
    	decodedImage12.saveFile();
    	decodedImage2.saveFile();
    	errorImage12.saveFile();
    	errorImage2.saveFile();
    	
    	System.out.println("12:" + distortion12);
    	System.out.println("2:" + distortion2);
    	System.out.println("Bits per symbol for 12 encoding: " + (int) (Math.log(distortion12)/Math.log(2) + 1));
    	System.out.println("Bits per symbol for 2 encoding: " + (int) (Math.log(distortion2)/Math.log(2) + 1));
    	
    }
 
}
