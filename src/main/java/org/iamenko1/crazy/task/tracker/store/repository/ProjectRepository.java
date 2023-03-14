package org.iamenko1.crazy.task.tracker.store.repository;

import org.iamenko1.crazy.task.tracker.store.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
