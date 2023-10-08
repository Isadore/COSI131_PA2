package edu.brandeis.cs.cs131.pa2.filter.concurrent;

import java.util.ArrayList;
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
		
		List<Job> jobs = new ArrayList<Job>();

		Scanner consoleReader = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		
		main:
		while (true) {
			for(int i = 0; i < jobs.size(); i++) {
				Job j = jobs.get(i);
				// pause console loop if non-concurrent job is running
				if(j.running() && !j.concurrent) continue main;
				// remove completed jobs from array
				if(!j.running()) jobs.remove(i);
			}
			System.out.print(Message.NEWCOMMAND);

			// read user command, if its just whitespace, skip to next command
			String cmd = consoleReader.nextLine();
			String trimmed = cmd.trim();
			if (trimmed.isEmpty()) {
				continue;
			}

			// exit the REPL if user specifies it
			if (trimmed.equals("exit")) {
				break;
			}
			
			// print active background jobs
			if (trimmed.equals("repl_jobs")) {
				for(Job j : jobs) {
					if(j.concurrent && j.running())
						System.out.println("\t" + j.id + ". " + j.command);
				}
				continue;
			}
			
			// interrupt specified background threads
			if(trimmed.matches("^kill\\s+\\d+$")) {
				int id = Integer.parseInt(trimmed.replaceAll("kill\\s+", ""));
				for(Job j : jobs)
					if(j.id == id) j.kill();
				continue;
			}

			try {
				// check for concurrency flag & at the end of the string
				boolean isConcurrent = cmd.matches(".+\\s+&$");
				// parse command into sub commands, then into Filters, add final PrintFilter if
				// necessary, and link them together - this can throw IAE so surround in
				// try-catch so appropriate Message is printed (will be the message of the IAE)
				List<ConcurrentFilter> filters = ConcurrentCommandBuilder.createFiltersFromCommand(cmd.replaceAll("\\s+&$", ""));
				// all process on each of the filters to have them execute
				Thread[] threads = new Thread[filters.size()];
				for(int i = 0; i < threads.length; i++) {
					threads[i] = new Thread(filters.get(i));
					threads[i].start();
				}
				int id = jobs.size() > 0 ? jobs.get(jobs.size() - 1).id + 1 : 1;
				jobs.add(new Job(threads, cmd, id, isConcurrent));
			} catch (InvalidCommandException e) {
				System.out.print(e.getMessage());
			}
		}
		System.out.print(Message.GOODBYE);
		consoleReader.close();

	}

}
