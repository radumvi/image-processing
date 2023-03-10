package feature.processing.image;

/**
 * Class extending Complete image.
 * Has only one constructor defined and more strict
 * methods for data I/O.
 */
public class GrayscaleImage extends CompleteImage {
	
	/**
	 * The only defined constructor.
	 * Allocates the space in which the image will be stored
	 * @param height
	 * @param width
	 */
	public GrayscaleImage(int height, int width) {
		this.height = height;
		this.width = width;
		
		this.size = height * width;
		this.pixels = new int[size];
		
		information += " The resolution is "+ height + "x" + width + ".";
	}
	
	@Override
	/**
	 * Sets a pixel in the image, at the given coordinates.
	 * Throws an Exception when a illegal value is passed to it.
	 */
	public void setPixel(int value, int row, int col) throws Exception{
		if (value < 0 || value > 255) {
			throw new Exception("Illegal value of a pixel was provided");
		}
		pixels[row*width + col] = value;
	}
	
	@Override
	/**
	 * Adds pixels to the image, in natural order.
	 */
	public void addPixels(int ...values) throws Exception {
		for (int pixel : values) {
			if (pixel < 0 || pixel > 255) {
				throw new Exception("Illegal value of a pixel was provided");
			}
			pixels[cursor++] = pixel;
		}
	}
	
	/**
	 * Returns details about the image.
	 */
	public String getInformation() {
		return information;
	}
	
	{
		information += " It is a grayscale image.";
	}
}
