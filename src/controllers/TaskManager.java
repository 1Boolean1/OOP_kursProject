package controllers;

import enums.Status;
import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager{
    int addNewTask(Task task);

    int addNewEpicTask(EpicTask epicTask);

    Integer addNewSubTask(SubTask subTask, int id);

    ArrayList<Task> printAllTasks();

    ArrayList<Task> printTasksByStatus(Status status);

    void deleteById(Integer id);

    void updateTaskStatus(Integer taskId);

    Task getTask(Integer taskId);

    Task getEpicTask(Integer taskId);

    Task getSubTask(Integer taskId);

    void deleteAllTasks();
}
