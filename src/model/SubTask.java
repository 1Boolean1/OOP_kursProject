package model;

import java.io.Serializable;

public class SubTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public SubTask(String subTaskName, String subTaskDescription) {
        super(subTaskName, subTaskDescription);
        //this.epicId = epicId;
    }


    @Override
    public String toString() {
        return "<br>---->SubTaskId: " + getTaskId() +
                "&emsp &ensp Name: " + getTaskName() +
                "&emsp &ensp Description: " + getTaskDescription() +
                "&emsp &ensp Status: " + getTaskStatus();
    }
}