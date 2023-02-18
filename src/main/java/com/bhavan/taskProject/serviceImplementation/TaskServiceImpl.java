package com.bhavan.taskProject.serviceImplementation;

import com.bhavan.taskProject.controller.TaskController;
import com.bhavan.taskProject.entity.Task;
import com.bhavan.taskProject.entity.Users;
import com.bhavan.taskProject.exception.APIException;
import com.bhavan.taskProject.exception.TaskNotFound;
import com.bhavan.taskProject.exception.UserNotFound;
import com.bhavan.taskProject.payload.TaskDto;
import com.bhavan.taskProject.repository.TaskRepository;
import com.bhavan.taskProject.repository.UserRepository;
import com.bhavan.taskProject.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public TaskDto saveTask(long userid, TaskDto taskDto) {
        Users user=userRepository.findById(userid).orElseThrow(
                ()->new UserNotFound(String.format("User Id %d not found",userid)));
        Task task=modelMapper.map(taskDto,Task.class);
        task.setUsers(user);
        //after setting the user ,we are storing the data in db
        Task savedTask=taskRepository.save(task);
        return modelMapper.map(savedTask,TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(long userid) {
        userRepository.findById(userid).orElseThrow(
                ()->new UserNotFound(String.format("User Id %d not found",userid)));
        List<Task>tasks=taskRepository.findAllByUsersId(userid);
        return tasks.stream().map(task -> modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTask(long userid, long taskid) {
        Users users=userRepository.findById(userid).orElseThrow(
                ()->new UserNotFound(String.format("User Id %d not found",userid)));
        Task task=taskRepository.findById(taskid).orElseThrow(
                ()->new TaskNotFound(String.format("Task Id %d not found",taskid)));
        if(users.getId() != task.getUsers().getId()){
            throw new APIException(String.format("Task Id %d is not belongs to User ID %d",taskid,userid));
        }
        return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public void deleteTask(long userid, long taskid) {
        Users users=userRepository.findById(userid).orElseThrow(
                ()->new UserNotFound(String.format("User Id %d not found",userid)));
        Task task=taskRepository.findById(taskid).orElseThrow(
                ()->new TaskNotFound(String.format("Task Id %d not found",taskid)));
        if(users.getId() != task.getUsers().getId()){
            throw new APIException(String.format("Task Id %d is not belongs to User ID %d",taskid,userid));
        }
        taskRepository.deleteById(taskid);///delete the task

    }
}
