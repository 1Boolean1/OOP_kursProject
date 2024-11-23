package model;

import enums.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EpicTask extends Task {

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
        return "<html>EpicTask{" +
                "EpicTaskName='" + getTaskName() + '\'' +
                ", EpicTaskDescription='" + getTaskDescription() + '\'' +
                ", EpicTaskID=" + getTaskId() +
                ", EpicTaskStatus=" + getTaskStatus() +
                "," + getSubTasksToPrint() +
                "}</html>";
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

    /*public void changeSubTaskStatus(int subTaskId) {
        if (getSubTasks().get(subTaskId).getTaskStatus().equals(Status.NEW)) {
            getSubTasks().get(subTaskId).setTaskStatus(Status.IN_PROGRESS);
            if (getTaskStatus().equals(Status.NEW)) {
                setTaskStatus(Status.IN_PROGRESS);
            }
        } else if (getSubTasks().get(subTaskId).getTaskStatus().equals(Status.IN_PROGRESS)) {
            getSubTasks().get(subTaskId).setTaskStatus(Status.DONE);
            int numOfSubTasksWithDone = 0;
            for (SubTask subTask : getSubTasks().values()) {
                if (subTask.getTaskStatus().equals(Status.DONE)) {
                    numOfSubTasksWithDone++;
                }
            }
            if (numOfSubTasksWithDone == getSubTasksArray().size()) {
                setTaskStatus(Status.DONE);
            }
        }
    }*/
}
