package feature.processing.image;

/**
 * 
 * Interface that enforces basic I/O data operations and
 * 2 measures for size.
 *
 */
public interface ImageInterface {
	/**
	 * Returns the height of the image.
	 */
	public int getHeight();
	/**
	 * Returns the length of the image.
	 */
	public int getWidth();
	
	/**
	 * Sets the pixel at the coordinates given.
	 */
	public void setPixel(int value, int row, int col) throws Exception;
	/**
	 * Returns the pixel at the coordinates given.
	 */
	public int getPixel(int row, int col);
}
