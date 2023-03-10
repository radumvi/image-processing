package feature.processing.operations;

/**
 * Abstract class used for image processing.
 * It has 2 methods for processing individual pixels.
 * Holds the name of the operation to be executed.
 */
public abstract class SimpleImageProcessor {
	protected int minValue;
	protected int maxValue;
	
	protected static final String operationName = "histogram operation:";
	/**
	 * Applies the mathematical formula for a pixel for stretching.
	 */
	public abstract int computeStretch(int value);
	/**
	 * Applies the mathematical formula for a pixel for shrinking.
	 */
	public abstract int computeShrink(int value, int globalMin, int globalMax);

	/**
	 * Returns the name of the operation executing.
	 */
	public static String getOperationName() {
		return operationName;
	}
}
