package edu.rit.se.fpts.controller.command;

public interface Command {

	public void execute();

	public void undo();
}
