package edu.brandeis.cs.cs131.pa2.filter.concurrent;

import java.util.List;
import java.util.Scanner;

import edu.brandeis.cs.cs131.pa2.filter.Message;

/**
 * The main implementation of the REPL loop (read-eval-print loop). It reads
 * commands from the user, parses them, executes them and displays the result.
 */
public class ConcurrentREPL {

	/**
	 * pipe string
	 */
	static final String PIPE = "|";

	/**
	 * redirect string
	 */
	static final String REDIRECT = ">";

	/**
	 * The main method that will execute the REPL loop
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {

		Scanner consoleReader = new Scanner(System.in);
		System.out.print(Message.WELCOME);

		while (true) {
			System.out.print(Message.NEWCOMMAND);

			// read user command, if its just whitespace, skip to next command
			String cmd = consoleReader.nextLine();
			if (cmd.trim().isEmpty()) {
				continue;
			}

			// exit the REPL if user specifies it
			if (cmd.trim().equals("exit")) {
				break;
			}

			try {
				// parse command into sub commands, then into Filters, add final PrintFilter if
				// necessary, and link them together - this can throw IAE so surround in
				// try-catch so appropriate Message is printed (will be the message of the IAE)
				List<ConcurrentFilter> filters = ConcurrentCommandBuilder.createFiltersFromCommand(cmd);

				// call process on each of the filters to have them execute
				for (ConcurrentFilter filter : filters) {
//					new Thread(filter).start();
					filter.process();
				}
			} catch (InvalidCommandException e) {
				System.out.print(e.getMessage());
			}
		}
		System.out.print(Message.GOODBYE);
		consoleReader.close();

	}

}
