package edu.brandeis.cs.cs131.pa2.filter.concurrent;


/**
 * Implements printing as a {@link ConcurrentFilter} - overrides necessary
 * behavior of ConcurrentFilter
 * 
 * @author Chami Lamelas
 *
 */
public class PrintFilter extends ConcurrentFilter {

	/**
	 * Overrides ConcurrentFilter.processLine() to just print the line to stdout.
	 */
	@Override
	protected String processLine(String line) {

		System.out.println(line);
		return null;
	}

}
