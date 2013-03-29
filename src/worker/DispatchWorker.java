package worker;

import commands.Command;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/26/13
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchWorker extends SwingWorker<Void, Command> {

    private Queue<Command> commandQueue = new LinkedList<Command>();

    public void submitAction(Command command) {

        synchronized (commandQueue) {
            commandQueue.add(command);
            commandQueue.notifyAll();

        }
    }

    @Override
    protected Void doInBackground() {


        while (!isCancelled()) {
            synchronized (commandQueue) {
                while (commandQueue.isEmpty() && (!isCancelled())) {
                    try {
                        commandQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }

                publish(commandQueue.poll());
            }

        }

        return null;
    }

    @Override
    protected void process(List<Command> commands) {

        for (Command command : commands) {
            command.execute();
        }
    }
}

