package edu.brandeis.cs.cs131.pa2.filter.concurrent;


/**
 * Implements head command - overrides necessary behavior of ConcurrentFilter
 * 
 * @author Chami Lamelas
 *
 */
public class HeadFilter extends ConcurrentFilter {

	/**
	 * number of lines read so far
	 */
	private int numRead;

	/**
	 * number of lines passed to output via head
	 */
	private static int LIMIT = 10;

	/**
	 * Constructs a head filter.
	 */
	public HeadFilter() {
		super();
		numRead = 0;
	}

	/**
	 * Overrides {@link ConcurrentFilter#process()} to only add up to 10 lines to
	 * the output queue.
	 * @throws InterruptedException 
	 */
	@Override
	public void process() throws InterruptedException {
		while (running && numRead < LIMIT) {
			if(!input.isEmpty()) {
				numRead++;
				String line = input.readAndWait();
				if(line == null) {
					running = false;
				} else {
					output.writeAndWait(line);
				}
			}
		}
		running = false;
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
