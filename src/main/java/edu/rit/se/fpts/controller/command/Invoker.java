package edu.rit.se.fpts.controller.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

	private final List<Command> history;

	private int index;

	public Invoker() {
		history = new ArrayList<Command>();
		index = -1;
	}

	public void executeCommand(Command command) {
		command.execute();
		if (index >= 0 && index < history.size() - 1) {
			history.set(++index, command);
			history.subList(index + 1, history.size()).clear();
		} else {
			history.add(command);
			index++;
		}
	}

	public void undoCommand() {
		if (index >= 0)
			history.get(index--).undo();
	}

	public void redoCommand() {
		if (index < history.size() - 1)
			history.get(++index).execute();
	}
}
