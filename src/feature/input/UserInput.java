package feature.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class used for getting user input.
 * Only for image processing.
 */
public final class UserInput {
	private String inputFilename;
	private String outputFilename;
	private int option;
	private int globalMax;
	private int globalMin;
	
	/**
	 * Method that writes to console and gets the user's response.
	 * Only checks if the responses are valid. Does not check if a 
	 * file exists or if it is corrupted.
	 * 
	 * Gets the following:
	 * 		input and output files'name
	 * 		option 1 - for stretching
	 * 			   2 - for shrinking
	 * 		parameters for image shrinking
	 * 
	 * @return operation success
	 */
	public boolean getInput() {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter the absolute path of the image that you would like to process:");
		
		try {
			inputFilename = reader.readLine();
		} catch (IOException e2) {
			return false;
		}
		
		System.out.println("Enter the absolute path of the processed image:");
		
		try {
			outputFilename = reader.readLine();
		} catch (IOException e2) {
			return false;
		}
		
		System.out.println("Please enter the index of your desired processing method");
		System.out.println("1. Stretch the image's histogram");
		System.out.println("2. Shrink the images's histogram");
			
		try {
			String optionString = reader.readLine();
			option = Integer.parseInt(optionString);
		} catch (IOException | NumberFormatException e) {
			System.out.println("Your option is not valid");
			return false;
		}
		
		if (option != 1 && option !=2) {
			
			System.out.println("Your option has no corresponding processing operation");
			return false;
		}
		
		if (option == 1) {
			return true;
		}
		else {
			System.out.println("Enter the minimum value of all the pixels in the image:");
			System.out.println("It should be in the [0, 255] range");
			System.out.println("Recommended : 75");
			
			try {
				String optionString = reader.readLine();
				globalMin = Integer.parseInt(optionString);
			} catch (IOException e2) {
				System.out.println("Your option is not valid");
				return false;
			}
			
			if (globalMin < 0 || globalMin > 255) {
				System.out.println("The value that you have provided is not in the range");
				return false;
			}
			
			
			System.out.println("Enter the maximum value of all the pixels in the image:");
			System.out.println("It should be in the [0, 255] range");
			System.out.println("Recommended : 175");
			
			try {
				String optionString = reader.readLine();
				globalMax = Integer.parseInt(optionString);
			} catch (IOException e2) {
				System.out.println("Your option is not valid");
				return false;
			}
			
			if (globalMax < 0 || globalMax > 255 || globalMax <= globalMin) {
				System.out.println("The value that you have provided is not in the range");
				return false;
			}			
		}
		
		return true;
	}
	
	/**
	 * Getter for input file name.
	 * @return
	 */
	public String getInputFilename() {
		return inputFilename;
	}
	/**
	 * Getter for output file name.
	 * @return
	 */
	public String getOutputFilename() {
		return outputFilename;
	}
	/**
	 * Getter for histogram manipulation option.
	 * @return
	 */
	public int getOption() {
		return option;
	}
	/**
	 * Getter for upper bound parameter for histogram shrinking.
	 * @return
	 */
	public int getGlobalMax() {
		return globalMax;
	}
	/**
	 * Getter for lower bound parameter for histogram shrinking.
	 * @return
	 */
	public int getGlobalMin() {
		return globalMin;
	}
}
