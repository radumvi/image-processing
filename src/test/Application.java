package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.imageio.ImageIO;

import feature.input.UserInput;
import feature.processing.image.GrayscaleImage;
import feature.processing.operations.GrayscaleImageProcessor;
import feature.reading.threads.ControlRunnable;
import feature.writing.runnable.ReceiverRunnable;
import feature.writing.runnable.TransmitterRunnable;

/**
 * Class that contains the main method.
 */
public class Application {

	public static void main(String[] args){
		
		//used for measuring time
		float startTime, endUserInputTime, endReadingTime, 
				endProcessingTime, endWritingTime;
		
		// files used for reading/writing the image
		File inputFile, outputFile;
		 
		// image API for reading from/writing to binary files
		BufferedImage imageInput = null;
		BufferedImage imageOutput = null;
		
		// instances of classes for pipe communication
		final PipedOutputStream pipeOutput = new PipedOutputStream();
        PipedInputStream pipeInput = null;
        
		try {
			pipeInput = new PipedInputStream(pipeOutput);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		startTime = System.nanoTime();
		
		UserInput form = new UserInput();
		// getting the user input
		if (!form.getInput()) {
			return;
		}
		
		endUserInputTime = System.nanoTime();
		
		inputFile = new File(form.getInputFilename());
		outputFile = new File(form.getOutputFilename());
		
		try {
			imageInput = ImageIO.read(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("The image could not be read");
			System.out.println("Execution ended");
			return;
		}
		
		// instance of my class for images
		GrayscaleImage myImage = new GrayscaleImage(imageInput.getHeight(), imageInput.getWidth());
		
		// starting the reading stage
		Thread readingThread = new Thread(new ControlRunnable(imageInput, myImage));
		readingThread.start();
		
		// waiting for the reading to be over
		try {
			readingThread.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		endReadingTime = System.nanoTime();
		
		// instance of my processing class
		GrayscaleImageProcessor processor = new GrayscaleImageProcessor(myImage);
		
		System.out.println("\nProcessing has started");
		
		// deciding if stretching or shrinking should be executed
		if (form.getOption() == 1) {
			processor.stretch();
		}
		else {
			processor.shrink(form.getGlobalMin(), form.getGlobalMax());
		}
		
		endProcessingTime = System.nanoTime();
		System.out.println("Processing has ended");
		
		// getting the result and printing some information
		GrayscaleImage image2 = processor.getResutlt();
		System.out.println("\n" + image2.getInformation() + "\n");
		
		imageOutput = new BufferedImage(imageInput.getWidth(), imageInput.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		// beginning the writing stage
		Thread transmitter = new Thread(new TransmitterRunnable(pipeOutput, image2));
		Thread receiver = new Thread(new ReceiverRunnable(pipeInput, imageOutput));
		
		transmitter.start();
		receiver.start();
		
		// waiting for the writing to complete
		try {
			receiver.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		endWritingTime = System.nanoTime();	
		try {
			ImageIO.write(imageOutput, "png", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// printing execution time
		System.out.println("Execution time for each step:");
		System.out.println("");
		System.out.println("Getting user input \t" + (endUserInputTime - startTime) / 1000000 + "ms");
		System.out.println("Reading the image \t" + (endReadingTime - endUserInputTime) / 1000000 + "ms");
		System.out.println("Processing the image \t" + (endProcessingTime - endReadingTime) / 1000000 + "ms");
		System.out.println("Writing the image \t" + (endWritingTime - endProcessingTime) / 1000000 + "ms");
	}
}
