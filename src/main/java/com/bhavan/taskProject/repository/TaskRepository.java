package com.bhavan.taskProject.repository;

import com.bhavan.taskProject.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUsersId(long userid);
}
