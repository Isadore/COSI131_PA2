package edu.brandeis.cs.cs131.pa2.filter.concurrent;

/**
 * @author Isadore Haviland
 * Command line job thread and property manager
 */
public class Job {
	
	public final String command;
	public final int id;
	public final boolean concurrent;
	private final Thread[] threads;
	private boolean stopping = false;
	
	public Job(Thread[] threads, String cmd, int id, boolean concurrent) {
		this.command = cmd;
		this.id = id;
		this.concurrent = concurrent;
		this.threads = threads;
	}
	
	/**
	 * @return True if threads are active, False if threads are complete or have been interrupted
	 */
	public boolean running() {
		return threads[threads.length - 1].isAlive() && !stopping;
	}
	
	/**
	 * Interrupts all threads, signals threads to end execution.
	 */
	public void kill() {
		for(Thread t : threads) t.interrupt();
		stopping = true;
	}
	
}
