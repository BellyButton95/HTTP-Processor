package com.example.sumup;

public class Task{

    private final String name;
    private final String command;
    private final String[] requires;


    public Task(String name, String command, String[] requires){
        this.name = name;
        this.command = command;
        this.requires = requires;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String[] getRequires() {
        return requires;
    }


}