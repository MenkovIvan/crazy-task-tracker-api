package org.iamenko1.crazy.task.tracker.store.repository;

import org.iamenko1.crazy.task.tracker.store.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
