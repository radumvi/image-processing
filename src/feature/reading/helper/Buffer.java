package feature.reading.helper;

/**
 * Class used for communication between Producers and Consumer threads.
 */
public class Buffer {
	private int number = -1;
	private boolean available = false;
	
	/**
	 * Method that gets the value stored so long as it is available.
	 */
	public synchronized int get() {
		while (!available) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		available = false;
		notifyAll();
		return number;
	}
	
	/**
	 * Method that sets the value stored only if the previous value has
	 * been dealt with.
	 */
	
	public synchronized void put (int number) {
		while (available) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.number = number;
		available = true;
		notifyAll();
	}
}
