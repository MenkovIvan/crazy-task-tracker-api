package org.iamenko1.crazy.task.tracker.factory;

import org.iamenko1.crazy.task.tracker.api.dto.TaskDto;
import org.iamenko1.crazy.task.tracker.store.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity){

        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
