package org.iamenko1.crazy.task.tracker.factory;

import org.iamenko1.crazy.task.tracker.api.dto.ProjectDto;
import org.iamenko1.crazy.task.tracker.store.entity.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity){

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
