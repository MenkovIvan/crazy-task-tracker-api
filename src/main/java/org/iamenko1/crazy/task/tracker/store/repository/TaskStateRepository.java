package org.iamenko1.crazy.task.tracker.store.repository;

import org.iamenko1.crazy.task.tracker.store.entity.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
