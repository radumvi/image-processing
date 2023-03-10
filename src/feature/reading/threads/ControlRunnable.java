package feature.reading.threads;

import java.awt.image.BufferedImage;

import feature.processing.image.GrayscaleImage;
import feature.reading.helper.Buffer;

/**
 * Class used for starting all the reading threads.
 * It also sets the parameters for each thread reading the image.
 */

public class ControlRunnable implements Runnable {
	
	private BufferedImage image;
	private GrayscaleImage grayImage;
	
	/**
	 * Constructor that sets the image field.
	 * 
	 * @param image			the image to be read
	 * @param grayImage 	image in which to put all the information
	 */
	public ControlRunnable(BufferedImage image, GrayscaleImage grayImage) {
		this.image = image;
		this.grayImage = grayImage;
		
	}
	
	@SuppressWarnings("null")
	@Override
	/**
	 * Thread method that starts all other threads and divides the information into four.
	 */
	public void run() {
		// TODO Auto-generated method stub
		
		int start = 0, stop = 0;
		
		Buffer b = new Buffer();
		
		// the producers
		Thread producerThreads[] = new Thread[4];
		ProducerRunnable runnables[] = new ProducerRunnable[4];
		
		// the consumer
		Thread readingThread = new Thread(new ConsumerRunnable(b, grayImage, image.getWidth() * image.getHeight()));
		readingThread.start();
		
		// i start producer threads one by one
		for (int i = 0; i < 3; i++) {
			stop = start + image.getWidth() / 4;
			
			// setting up the thread start
			runnables[i] = new ProducerRunnable("Consumer thread no. " + i, image, b, start, stop);
			producerThreads[i] = new Thread(runnables[i]);
			start = stop;
			producerThreads[i].start();
			
			try {
				producerThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*
		 * I treat the last one different because I have to consider the case
		 * in which the height is not divisible by 4.
		 */
		producerThreads[3] = new Thread(new ProducerRunnable("Consumer thread no. 3", image, b, start, image.getHeight()));
		producerThreads[3].start();
		
		try {
			producerThreads[3].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
