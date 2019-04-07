package encoder;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Quantization {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	
    	Image original = new Image("baboon");
    	
    	Image encodedImage12 = new Image(original, 12, true, null);
    	Image encodedImage2 = new Image(original, 2, true, null);
    	
    	Image decodedImage12 = new Image(encodedImage12, 255, false, "baboon");
    	Image decodedImage2 = new Image(encodedImage2, 255, false, "baboon");
    	
    	Image errorImage12 = new Image(original, decodedImage12, 12);
    	Image errorImage2 = new Image(original, decodedImage2, 2);
    	
    	int distortion12 = (decodedImage12.calculateDistortion(original));
    	int distortion2 = (decodedImage2.calculateDistortion(original));
    	
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