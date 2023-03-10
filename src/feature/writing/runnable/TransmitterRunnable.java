package feature.writing.runnable;

import java.io.IOException;
import java.io.PipedOutputStream;

import feature.processing.image.GrayscaleImage;

/**
 * Class used for the thread sending information through the pipe.
 */
public class TransmitterRunnable implements Runnable {
	
	private PipedOutputStream output;
	private GrayscaleImage image;
	private final int rowLength;
	
	/**
	 * Constructor.
	 * @param output	the pipe output
	 * @param image		the image to be sent
	 */
	public TransmitterRunnable(PipedOutputStream output, GrayscaleImage image) {
		this.output = output;
		this.image = image;
		this.rowLength = image.getHeight();
	}
	
	@Override
	/**
	 * Thread methods which writes to output.
	 * It sends the data divided in 4 sections.
	 */
	public void run() {
		// markers for the image interval
		int start = 0;
		int stop = 0;
		
		for (int k = 0; k < 3; k++) {
			
			stop = start + image.getHeight() / 4;
			for (int i = start; i < stop; i++) {
				for (int j = 0; j < rowLength; j++) {
					try {
						output.write(image.getPixel(i, j));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Finished sending segment no. " + k);
			start = stop;
 		}
		
		// k = 3;
        /*
         * I treat the last part differently because I don't know that
         * the height is divisible by 4.
         */
		
		stop = image.getHeight();
		for (int i = start; i < stop; i++) {
			for (int j = 0; j < rowLength; j++) {
				try {
					output.write(image.getPixel(i, j));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Finished sending segment no. " + 3);
	}
	
}
