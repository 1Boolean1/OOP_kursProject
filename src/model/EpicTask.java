package model;

import enums.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class EpicTask extends Task implements Serializable{
    private static final long serialVersionUID = 1L;

    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public Collection<SubTask> getSubTasksToPrint() {
        return subTasks.values();
    }

    public ArrayList<SubTask> getSubTasksArray() {
        return new ArrayList<>(subTasks.values());
    }

    public EpicTask(String epicTaskName, String epicTaskDescription) {
        super(epicTaskName, epicTaskDescription);
    }


    public void addSubTask(SubTask subTask, Integer id) {
        subTasks.put(id, subTask);
        if (getTaskStatus().equals(Status.DONE)) {
            setTaskStatus(Status.IN_PROGRESS);
        }
    }


    @Override
    public String toString() {
        return "<html>EpicTaskID: " + getTaskId() +
                "&emsp &ensp Name: " + getTaskName() +
                "&emsp &ensp Description: " + getTaskDescription() +
                "&emsp &ensp Status: " + getTaskStatus() +
                 getSubTasksToPrint() +
                "</html>";
    }

    public void changeEpicTaskStatus() {
        if (getSubTasks().isEmpty()) {
            setTaskStatus(Status.NEW);
        } else{
            int numOfSubTasksWithDone = 0;
            int numOfSubTasksWithNew = 0;
            for (SubTask subTask : getSubTasks().values()) {
                if (subTask.getTaskStatus().equals(Status.DONE)) {
                    numOfSubTasksWithDone++;
                } else if (subTask.getTaskStatus().equals(Status.NEW)) {
                    numOfSubTasksWithNew++;
                }
            }
            if (numOfSubTasksWithDone == getSubTasksArray().size()) {
                setTaskStatus(Status.DONE);
            } else if (numOfSubTasksWithNew == getSubTasksArray().size()) {
                setTaskStatus(Status.NEW);
            } else {
                setTaskStatus(Status.IN_PROGRESS);
            }
        }
    }
}
