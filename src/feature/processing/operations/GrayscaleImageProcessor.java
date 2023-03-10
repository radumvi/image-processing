package feature.processing.operations;

import feature.processing.image.GrayscaleImage;

/**
 * Class extending SimpleImageProcessor.
 * It contains 2 instances of the GrayscaleImage class.
 * 
 */
public class GrayscaleImageProcessor extends SimpleImageProcessor {
	
	private GrayscaleImage inputImage;
	private GrayscaleImage outputImage;
	
	private final static String exactOperation;
	
	/**
	 * The only defined constructor.
	 * @param image the image to be processed
	 */
	public GrayscaleImageProcessor(GrayscaleImage image) {
		this.inputImage = image;
		this.outputImage = new GrayscaleImage(inputImage.getHeight(), inputImage.getWidth());
		
		minValue = inputImage.getMin();
		maxValue = inputImage.getMax();
	}
	
	/**
	 * Method that stretches the histogram the input image.
	 * It writes to the output image.
	 * Changes one bit at a time.
	 * @return the result from the processing operation
	 */
	public GrayscaleImage stretch() {
		outputImage.resetCursor();
		for (int i = 0; i < inputImage.getHeight(); i++) {
			for (int j = 0; j < inputImage.getWidth(); j++) {
				int val = inputImage.getPixel(i, j);
				val = computeStretch(val);
				
				try {
					outputImage.addPixels(val);
				} catch (Exception e) {
					return null;
				}
			}
		}
		
		return outputImage;
	}
	
	/**
	 * Method that shrinks the histogram the input image.
	 * It writes to the output image.
	 * Changes one bit at a time.
	 * @return the result from the processing operation
	 */
	public GrayscaleImage shrink(int globalMin, int globalMax) {
		outputImage.resetCursor();
		for (int i = 0; i < inputImage.getHeight(); i++) {
			for (int j = 0; j < inputImage.getWidth(); j++) {
				int val = inputImage.getPixel(i, j);
				val = computeShrink(val, globalMin, globalMax);
				System.out.println(val);
				
				
				try {
					outputImage.addPixels(val);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
					//e.printStackTrace();
				}
			}
		}
		
		return outputImage;
	}
	
	/**
	 * Getter for the output image
	 */
	public GrayscaleImage getResutlt() {
		return outputImage;
	}
	
	@Override
	/**
	 * Implements formula for individual pixel value when 
	 * stretching the histogram.
	 */
	public int computeStretch(int value) {
		float result = 0;
		
		result = ((float)(value - minValue)) / ((float)(maxValue - minValue));
		result *= 255;
		
		return (int) result;
	}

	@Override
	/**
	 * Implements formula for individual pixel value when 
	 * shrinking the histogram.
	 */
	public int computeShrink(int value, int globalMin, int globalMax) {
		// TODO Auto-generated method stub
		float result = 0;
		
		result = ((float) (globalMax - globalMin))/ ((float) (maxValue - minValue));
		result *= (value - minValue);
		result += globalMin;
		
		return (int) result;
	}

	/**
	 * Returns the specific name of the operation executed.
	 */
	public static String getExactOperation() {
		return exactOperation;
	}
	static
	{
		exactOperation = SimpleImageProcessor.getOperationName() + " stretching/shrinking";
	}
}
