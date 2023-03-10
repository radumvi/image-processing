package feature.reading.threads;

import java.awt.image.BufferedImage;

import feature.reading.helper.Buffer;

/**
 * 
 * This class is used for the reading threads.
 * They are supposed to only read a quarter of
 * the image.
 *
 */
public class ProducerRunnable implements Runnable {
	private String name;
	private BufferedImage image;
	private Buffer pixelBuffer;
	private int startRow;
	private int stopRow;
	
	private final int rowLength;
	
	/**
	 * The only non-default constructor.
	 * 
	 * @param 	image 		the image that is to be read
	 * @param 	pixelBuffer the buffer to be used
	 * @param 	startRow 	the starting row
	 * @param 	stopRow 	the end row
	 */
	public ProducerRunnable(String name, BufferedImage image, Buffer pixelBuffer, int startRow, int stopRow) {
		this.name = name;
		this.image = image;
		this.pixelBuffer = pixelBuffer;
		this.startRow = startRow;
		this.stopRow = stopRow;
		
		// setting the row length, given the metadata
		this.rowLength = image.getWidth();
	}

	@Override
	/**
	 * Thread method that sends pixels from the image that are in
	 * the given row interval.
	 */
	public void run() {
		System.out.println(name + " has started");
		System.out.println("Reading lines " + startRow + " - " + (stopRow - 1));
			for (int i = startRow; i < stopRow; i++) {
				for (int j = 0; j < rowLength; j++) {
					
					// masking the output pixel
					// because the image is gray-scale, I only need the value of one color
					// In this case, I am using the BLUE value
					pixelBuffer.put(image.getRGB(i, j) & 0xff);
				}
			}
		try {
			// for console output effect
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
