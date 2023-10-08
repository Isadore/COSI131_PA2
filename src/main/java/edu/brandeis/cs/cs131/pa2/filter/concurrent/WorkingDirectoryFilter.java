package edu.brandeis.cs.cs131.pa2.filter.concurrent;

import edu.brandeis.cs.cs131.pa2.filter.CurrentWorkingDirectory;
import edu.brandeis.cs.cs131.pa2.filter.Filter;
import edu.brandeis.cs.cs131.pa2.filter.Message;

/**
 * Implements pwd command - overrides necessary behavior of ConcurrentFilter
 * 
 * @author Chami Lamelas
 *
 */
public class WorkingDirectoryFilter extends ConcurrentFilter {

	/**
	 * command that was used to construct this filter
	 */
	private String command;

	/**
	 * Constructs a pwd filter.
	 * @param cmd cmd is guaranteed to either be "pwd" or "pwd" surrounded by whitespace
	 */
	public WorkingDirectoryFilter(String cmd) {
		super();
		command = cmd;
	}

	/**
	 * Overrides {@link ConcurrentFilter#process()} by adding
	 * {@link SequentialREPL#currentWorkingDirectory} to the output queue
	 * @throws InterruptedException 
	 */
	@Override
	public void process() throws InterruptedException {
		this.output.writeAndWait(CurrentWorkingDirectory.get());
		this.output.writePoisonPill();
	}

	/**
	 * Overrides ConcurrentFilter.processLine() - doesn't do anything.
	 */
	@Override
	protected String processLine(String line) {
		return null;
	}

	/**
	 * Overrides equentialFilter.setPrevFilter() to not allow a {@link Filter} to be
	 * placed before {@link WorkingDirectoryFilter} objects.
	 * 
	 * @throws InvalidCommandException - always
	 */
	@Override
	public void setPrevFilter(Filter prevFilter) {
		throw new InvalidCommandException(Message.CANNOT_HAVE_INPUT.with_parameter(command));
	}
}
