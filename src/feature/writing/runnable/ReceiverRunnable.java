package feature.writing.runnable;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PipedInputStream;

/**
 * Class used for the thread receiving information through the pipe.
 */
public class ReceiverRunnable implements Runnable {
	private PipedInputStream input;
	private BufferedImage image;
	
	private final int rowLength;
	
	/**
	 * Constructor.
	 * 
	 * @param input 	pipe input instance
	 * @param image		image in which to put the data
	 */
	public ReceiverRunnable(PipedInputStream input, BufferedImage image) {
		this.input = input;
		this.image = image;
		this.rowLength = image.getWidth();
	}
	
	/**
	 * Thread methods which reads from the input.
	 * It receives the data divided in 4 sections.
	 */
	public void run() {
        try {
            int start = 0;
            int stop = 0;
            int data;
            Color myColor;
            
            for (int k = 0; k < 3; k++) {
            	stop = start + image.getHeight() / 4;
            	for (int i = start; i < stop; i++) {
            		for (int j = 0; j < rowLength; j++) {
            			data = input.read();
            			// building the gray pixel
            			myColor = new Color(data, data, data); 
            			// I set it in the final image
            			image.setRGB(i, j, myColor.getRGB());
            		}
            	}
            	start = stop;
            	System.out.println("Finished receiving the segment " + k);
            	System.out.println("");
            }
            
            // k = 3;
            /*
             * I treat the last part differently because I don't know that
             * the height is divisible by 4.
             */
            stop = image.getHeight();
        	for (int i = start; i < stop; i++) {
        		for (int j = 0; j < rowLength; j++) {
        			data = input.read();
        			myColor = new Color(data, data, data); 
        			image.setRGB(i, j, myColor.getRGB());
        		}
        	}
        	
        	System.out.println("Finished receiving the segment " + 3);
        	System.out.println("");
        	
        } catch (IOException e) {
        }
    }
}
