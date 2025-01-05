package com.marcuschu.cli.pattern;

/**
 * 被遥控的设备
 */
public class Device {

    private String name;

    public Device(String name) {
        this.name = name;
    }

    public void turnOn() {
        System.out.println(name + " is on");
    }

    public void turnOff() {
        System.out.println(name + " is off");
    }


}
