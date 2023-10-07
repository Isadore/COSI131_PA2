package edu.brandeis.cs.cs131.pa2.filter.concurrent;

import edu.brandeis.cs.cs131.pa2.filter.Message;

/**
 * Implements grep command - includes parsing grep command by overriding
 * necessary behavior of SequentialFilter.
 * 
 * @author Chami Lamelas
 *
 */
public class GrepFilter extends ConcurrentFilter {

	/**
	 * holds the grep query
	 */
	private String query;

	/**
	 * constructs GrepFilter given grep command
	 * 
	 * @param cmd cmd is guaranteed to either be "grep" or "grep" followed by a
	 *            space.
	 * @throws InvalidCommandException if query parameter was not provided
	 */
	public GrepFilter(String cmd) {

		// find index of space, if there isn't a space that means we got just "grep" =>
		// grep needs a parameter so throw IAE with the appropriate message
		int spaceIdx = cmd.indexOf(" ");
		if (spaceIdx == -1) {
			throw new InvalidCommandException(Message.REQUIRES_PARAMETER.with_parameter(cmd));
		}

		// we have a space, query will be trimmed string after space
		query = cmd.substring(spaceIdx + 1).trim();
	}

	/**
	 * Overrides  ConcurrentFilter.processLine() - only returns lines to
	 * {@link ConcurrentFilter#process()} that contain the query parameter specified
	 * in the command passed to the constructor.
	 */
	@Override
	protected String processLine(String line) {

		// only have SequentialFilter:process() add lines to the output queue that
		// include the query string
		if (line.contains(query)) {
			return line;
		}

		return null;
	}

}
