package edu.brandeis.cs.cs131.pa2.filter.concurrent;

/**
 * Implements uniq command - overrides necessary behavior of ConcurrentFilter
 * 
 * @author Chami Lamelas
 *
 */
public class UniqFilter extends ConcurrentFilter {

	/**
	 * Stores previous line
	 */
	private String prevLine;

	/**
	 * Constructs a uniq filter.
	 */
	public UniqFilter() {
		super();
		prevLine = null;
	}

	/**
	 * Overrides ConcurrentFilter.processLine() - only returns lines to
	 * {@link ConcurrentFilter#process()} that are not repetitions of the previous
	 * line.
	 */
	@Override
	protected String processLine(String line) {
		String output = null;
		if (prevLine == null || !prevLine.equals(line)) {
			output = line;
		}

		prevLine = line;
		return output;
	}

}
