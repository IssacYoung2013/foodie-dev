package com.issac.designpattern.behavioral.command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Stuff {
    private List<Command> commandList = new ArrayList<>();

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public void executeCommands() {
        for (Command command : commandList) {
            command.execute();
        }
        commandList.clear();
    }
}
