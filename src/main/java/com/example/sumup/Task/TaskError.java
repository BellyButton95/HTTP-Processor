package com.example.sumup.Task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskError extends Task {

    private final String errorMsg;

    public TaskError(String errorMsg){
        super(errorMsg, errorMsg, null);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    @Override
    public boolean isError(){
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getName() {
        return errorMsg;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getCommand() {
        return errorMsg;
    }
}
