package feature.processing.image;

/**
 * Class that implements the ImageInterface and adds
 * the data structure that is used to store the data.
 */
public class ImageArray implements ImageInterface {
	protected int pixels[];
	protected int cursor;
	
	protected int height;
	protected int width;
	protected int size;

	@Override
	/**
	 * Sets a value in the array at the coordinates given.
	 * Uses a mapping between the elements of the array and the 
	 * row and column values.
	 */
	public void setPixel(int value, int row, int col) throws Exception {
		pixels[row*width + col] = value;
	}
	
	/**
	 * Adds pixels to the image in order from (0, 0)
	 * line by line, left to right.
	 */
	public void addPixels(int ...values) throws Exception {
		for (int pixel : values) {
			pixels[cursor++] = pixel;
		}
	}

	@Override
	/**
	 * Returns the pixel at the coordinates given.
	 */
	public int getPixel(int row, int col) {
		return pixels[row*width + col];
	}

	@Override
	/**
	 * Returns the height of the image.
	 */
	public int getHeight() {
		return height;
	}

	@Override
	/**
	 * Returns the width of the image.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the size of the image. (width * height)
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Resets the cursor used for adding pixels in order.
	 */
	public void resetCursor() {
		cursor = 0;
	}
}
