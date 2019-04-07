package encoder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Image {

	public int height = 0;
	public int width = 0;
	public int maxValue = 0;
	public int data[][] = null;
	String filename = null;
	String imageInfo = null;
	
	
	Image(String filename) throws FileNotFoundException, IOException {
		this.filename = filename;
		
		System.out.print("Hello!\n");
		File currentFile = new File("images/" + filename + ".pgma");
		Scanner fileScanner = new Scanner(currentFile);
		
		if (fileScanner.nextLine().equals("P2")) {
			
			if (fileScanner.next().equals("#")) {
				imageInfo = "#" + fileScanner.nextLine();
			}
			
			width = Integer.parseInt(fileScanner.next());
			height = Integer.parseInt(fileScanner.next());
			maxValue = Integer.parseInt(fileScanner.next());
			data = new int[width][height];
			
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					data[i][j] = Integer.parseInt(fileScanner.next());
				}
			}
			
		} else {
			System.out.println("\nWrong file type, please check image and try again.\n");

		}
		fileScanner.close();
		
	}
	
	Image(Image image, int nextMaxValue, boolean encoderOrDecoder, String tempName) {
		
		if (encoderOrDecoder == true) {
			this.data = image.encoder(nextMaxValue);
			this.filename = (image.filename + "_encoded_" + nextMaxValue);
		}
		else {
			this.data = image.decoder(nextMaxValue);
			this.filename = (tempName + "_decoded_" + image.maxValue);
		}
		
		this.height = image.height;
		this.width = image.width;
		this.maxValue = nextMaxValue;
		this.imageInfo = image.imageInfo;
		
	}
	
	Image(Image originalImage, Image decodedImage, int encodedRate) {
		this.data = this.createErrorImage(originalImage.data, decodedImage.data, originalImage.width, originalImage.height);
		this.height = originalImage.height;
		this.width = originalImage.width;
		this.maxValue = this.findMax(this.data, this.height, this.width);
		this.filename = (originalImage.filename + "_error_" + encodedRate);
		this.imageInfo = originalImage.imageInfo;
	}
	
	public void saveFile() throws IOException {
		FileWriter output = new FileWriter("images/" + filename + ".pgma");
		BufferedWriter writer = new BufferedWriter(output);
		
		writer.write("P2\n");
		writer.write(imageInfo + "\n");
		writer.append(String.valueOf(width) + " " + String.valueOf(height) + "\n");
		writer.append(String.valueOf(maxValue + "\n"));
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				writer.append(String.valueOf(this.data[i][j]) + " ");
			}
		}
		
		writer.close();
	}
	
	public int[][] encoder(int newMax) {
		
		int interval = (this.maxValue / newMax);
		int[][] returnData = new int[this.height][this.width];
		
		for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
            	returnData[i][j] = newEncodedValue(this.maxValue, newMax, interval, this.data[i][j]);
            }
        }
		
		return returnData;
	}
	
	public int newEncodedValue(int currentMaxValue, int newMax, int interval, int currentValue) {
		
		int newValue, i, j;
		newValue = i = j = 0;
		
		for (i = 0, j = 0; i <= currentMaxValue - interval; i = i + interval, j++) {
            if (currentValue >= i && currentValue <= i + interval) {
            	newValue = j;
                break;
            }
            if (currentValue == currentMaxValue) {
            	newValue = (newMax-1);
            	break;
            }
        }
		return newValue;
	}
	
	public int[][] decoder(int newMax) {
		
		int interval = (newMax / this.maxValue);
		int[][] returnData = new int[this.height][this.width];
		
		for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
            	returnData[i][j] = newDecodedValue(this.maxValue, interval, this.data[i][j]);
            }
        }
		
		return returnData;
	}
	
	public int newDecodedValue(int currentMaxValue, int interval, int currentValue) {
		
		int newValue, i, j;
		newValue = i = j = 0;
		
		for (i = 0, j = 0; j < currentMaxValue; i = i + interval, j++) {
			if (currentValue == j) {
				newValue = (int)(i + ((i + interval)/currentMaxValue));
                break;
            }
        }
		return newValue;
	}
	
	 public int calculateDistortion(Image original) {
		 int sumOfDif = 0;
		 for(int i = 0; i < this.height; i++){
			 for(int j = 0; j < this.width; j++){
				 sumOfDif += Math.abs((original.data[i][j] - this.data[i][j]));
			 }
		 }
		 return (sumOfDif/(this.height*this.width));
	 }
		
	public int [][] createErrorImage(int [][] original, int [][] reconstructed, int height, int width) {
		int [][] error = new int [height][width];	       
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				error[i][j] = Math.abs(original[i][j] - reconstructed[i][j]);
				}
			}
		return error;
	}
		
	public int findMax(int [][] data, int height, int width) {
		int max = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (data[i][j] > max) {
					max = data[i][j];
				}
			}
		}
		return max;
	}
	
}