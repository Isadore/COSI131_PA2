package edu.brandeis.cs.cs131.pa2.filter.concurrent;

import java.io.File;

import edu.brandeis.cs.cs131.pa2.filter.CurrentWorkingDirectory;
import edu.brandeis.cs.cs131.pa2.filter.Filter;
import edu.brandeis.cs.cs131.pa2.filter.Message;

/**
 * Implements ls command - overrides necessary behavior of ConcurrentFilter
 * 
 * @author Chami Lamelas
 *
 */
public class ListFilter extends ConcurrentFilter {

	/**
	 * command that was used to construct this filter
	 */
	private String command;

	/**
	 * Constructs an ListFilter from an exit command
	 * 
	 * @param cmd - exit command, will be "ls" or "ls" surrounded by whitespace
	 */
	public ListFilter(String cmd) {
		super();
		command = cmd;
	}

	/**
	 * Overrides ConcurrentFilter.processLine() - doesn't do anything.
	 */
	@Override
	protected String processLine(String line) {
		return null;
	}

	/**
	 * Overrides {@link ConcurrentFilter#process()} to add the files located in
	 * the current working directory to the output queue.
	 * @throws InterruptedException 
	 */
	@Override
	public void process() throws InterruptedException {
		File cwd = new File(CurrentWorkingDirectory.get());
		File[] files = cwd.listFiles();
		for (File f : files)
			this.output.writeAndWait(f.getName());
		this.output.writePoisonPill();
	}

	/**
	 * Overrides ConcurrentFilter.setPrevFilter() to not allow a
	 * {@link Filter} to be placed before {@link ListFilter} objects.
	 * 
	 * @throws InvalidCommandException - always
	 */
	@Override
	public void setPrevFilter(Filter prevFilter) {
		throw new InvalidCommandException(Message.CANNOT_HAVE_INPUT.with_parameter(command));
	}

}
