package com.example.sumup.Task;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Task {

    private String name;
    private String command;
    private String[] requires;

    public Task() { }

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String[] getRequires() {
        return requires;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o == this){
            return true;
        }
        if(o instanceof Task){
            Task task = (Task) o;
            boolean nameCommandSame = name.equals(task.name) && command.equals(task.command);
            if(requires == null && task.requires == null){
                return nameCommandSame;
            }
            if(requires == null || task.requires == null){
                return false;
            }
            return nameCommandSame
                    && requires.length == task.requires.length
                    && Arrays.asList(requires).containsAll(Arrays.asList(task.requires));
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + name.hashCode();
        hash = 31 * hash + command.hashCode();
        if(requires != null){
            for(String task : requires){
                hash = 31 * hash + task.hashCode();
            }
        }
        return hash;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isError(){ return false; }
}