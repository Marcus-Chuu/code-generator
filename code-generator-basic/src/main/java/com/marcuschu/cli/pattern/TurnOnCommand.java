package com.marcuschu.cli.pattern;

/**
 * 打开命令
 */
public class TurnOnCommand implements Command {

    private Device device;

    public TurnOnCommand(Device device) {
        this.device = device;
    }

    public void execute() {
        device.turnOn();
    }

}
