package org.iamenko1.crazy.task.tracker.factory;

import org.iamenko1.crazy.task.tracker.api.dto.TaskStateDto;
import org.iamenko1.crazy.task.tracker.store.entity.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity){

        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ordinal(entity.getOrdinal())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
