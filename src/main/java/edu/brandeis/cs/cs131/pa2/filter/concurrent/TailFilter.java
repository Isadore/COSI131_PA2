package edu.brandeis.cs.cs131.pa2.filter.concurrent;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements tail command - overrides necessary behavior of ConcurrentFilter
 * 
 * @author Chami Lamelas
 *
 */
public class TailFilter extends ConcurrentFilter {

	/**
	 * number of lines passed to output via tail
	 */
	private static int LIMIT = 10;

	/**
	 * line buffer
	 */
	private List<String> buf;

	/**
	 * Constructs a tail filter.
	 */
	public TailFilter() {
		super();
		buf = new LinkedList<String>();
	}

	/**
	 * Overrides {@link ConcurrentFilter#process()} to only add up to 10 lines to
	 * the output queue.
	 * @throws InterruptedException 
	 */
	@Override
	public void process() throws InterruptedException {

		// until the input is empty, add line to end of buffer if buffer reached LIMIT,
		// remove the head (LinkedList makes this O(1)), could also use Queue/Deque
		// removing the head removes the oldest line seen so far - this way buf will
		// hold the last 10 lines of the input (or as many lines were in the input if
		// the input had < 10 lines)
		while (running || !input.isEmpty()) {
			String line = input.readAndWait();
			if(line == null) {
				running = false;
			} else {
				buf.add(line);
				if (buf.size() > LIMIT) {
					buf.remove(0);
				}
			}
		}

		// once we're done with the input (and have identified the last 10 lines), add
		// them to the output in the order in which they appeared in the input
		while (!buf.isEmpty())
			output.writeAndWait(buf.remove(0));
		output.writePoisonPill();
	}

	/**
	 * Overrides ConcurrentFilter.processLine() - doesn't do anything.
	 */
	@Override
	protected String processLine(String line) {
		return null;
	}

}
