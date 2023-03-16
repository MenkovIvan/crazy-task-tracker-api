package org.iamenko1.crazy.task.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.iamenko1.crazy.task.tracker.api.dto.ProjectDto;
import org.iamenko1.crazy.task.tracker.api.exception.BadRequestException;
import org.iamenko1.crazy.task.tracker.factory.ProjectDtoFactory;
import org.iamenko1.crazy.task.tracker.store.entity.ProjectEntity;
import org.iamenko1.crazy.task.tracker.store.repository.ProjectRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectDtoFactory projectDtoFactory;

    public static final String CREATE_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestBody ProjectDto projectDto){

        projectRepository
                .findByName(projectDto.getName())
                .ifPresent(projectEntity -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", projectDto.getName()));
                });

        ProjectEntity projectEntity = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(projectDto.getName())
                        .build()
        );

        return projectDtoFactory.makeProjectDto(projectEntity);
    }
}
