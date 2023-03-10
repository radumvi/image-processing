package feature.processing.image;

/**
 * Class extending ImageArray, adds 2 methods for
 * finding the minimum and the maximum value in the array.
 */
public class CompleteImage extends ImageArray {
	
	protected String information;
	
	/**
	 * Returns the smallest value in the image.
	 */
	public int getMin() {
		int min = 256;
		for (int i = 0; i < size; i++) {
			if (pixels[i] < min) {
				min = pixels[i];
			}
		}
		return min;
	}
	
	/**
	 * Returns the greatest value in the image.
	 */
	public int getMax() {
		int max = 0;
		for (int i = 0; i < size; i++) {
			if (pixels[i] > max) {
				max = pixels[i];
			}
		}
		return max;
	}
	
	{
		information = "It is an image.";
	}
}
