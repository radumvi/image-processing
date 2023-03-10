package feature.reading.threads;

import feature.processing.image.GrayscaleImage;
import feature.reading.helper.Buffer;

/**
 * Class used for the Producer-Consumer design pattern.
 */
public class ConsumerRunnable implements Runnable {

	private Buffer pixelBuffer;
	private GrayscaleImage grayImage;
	private final int size;
	
	/**
	 * The only defined constructor.
	 * @param 	pixelBuffer		the buffer used for communication
	 * @param 	grayImage		the image in which to set all the pixels received
	 * @param 	size			the image size
	 */
	public ConsumerRunnable (Buffer pixelBuffer, GrayscaleImage grayImage , int size) {
		this.pixelBuffer = pixelBuffer;
		this.grayImage = grayImage;
		this.size = size;
	}
	
	@Override
	/**
	 * Method that characterizes the consumer thread. 
	 * It reads from the buffer and puts the values received in the image.
	 */
	public void run() {
		int value = 0;
		grayImage.resetCursor();
		for(int i = 0; i < size; i++) {
			value = pixelBuffer.get();
			try {
				grayImage.addPixels(value);
			}
			catch(Exception e) {
				System.out.println(e);
			}
			
		}
		System.out.println("From consumer -- I have finished building the image");
	}
	
}
