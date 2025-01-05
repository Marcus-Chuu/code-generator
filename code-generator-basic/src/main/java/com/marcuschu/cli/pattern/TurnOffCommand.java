package com.marcuschu.cli.pattern;

/**
 * 关闭命令
 */
public class TurnOffCommand implements Command {

    private Device device;

    public TurnOffCommand(Device device) {
        this.device = device;
    }

    public void execute() {
        device.turnOff();
    }

}
