package main.command;

import main.exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException;
}
