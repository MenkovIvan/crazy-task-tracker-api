package org.iamenko1.crazy.task.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.iamenko1.crazy.task.tracker.api.dto.AckDto;
import org.iamenko1.crazy.task.tracker.api.dto.ProjectDto;
import org.iamenko1.crazy.task.tracker.api.exception.BadRequestException;
import org.iamenko1.crazy.task.tracker.api.exception.NotFoundException;
import org.iamenko1.crazy.task.tracker.factory.ProjectDtoFactory;
import org.iamenko1.crazy.task.tracker.store.entity.ProjectEntity;
import org.iamenko1.crazy.task.tracker.store.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional
@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectDtoFactory projectDtoFactory;

    public static final String FETCH_PROJECT = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects/{project_id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    /**
     * Получение списка проектов по началу названия
     * example: http://localhost:8080/api/projects?prefix_name=First
     * @param optionalPrefixName //начало названия проекта
     * @return если пустой - весь список проектов, или проекты которые начинаются с этой строки
     */
    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetchProjects(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> ! prefixName.trim().isEmpty());

        /*if (optionalPrefixName.isPresent()) {
            projectStream = projectRepository.streamAllByNameStartsWithIgnoreCase(optionalPrefixName.get())
        } else{
            projectStream = projectRepository.streamAll();
        }*/
        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    /**
     * Добавление проекта
     * example: http://localhost:8080/api/projects
     * @param projectDto
     * @return ProjectDto
     */
    @PostMapping(CREATE_PROJECT)    //для примера для передачи использутся dto в RequestBody
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

    /**
     * Изменение проекта
     * example: http://localhost:8080/api/projects/4?name=Forth Project
     * @param projectId
     * @param name
     * @return ProjectDto
     */
    @PatchMapping(EDIT_PROJECT)     //для примера для передачи использутся поле в запросе + поле в RequestParam
    public ProjectDto editPatch(
            @PathVariable("project_id") Long projectId,
            @RequestParam String name) {

        if (name.trim().isEmpty()) {
            throw new BadRequestException("Name can't be empty");
        }

        ProjectEntity projectEntity = getProjectOrThrowException(projectId);

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(
                        anotherProject -> {
                            throw new BadRequestException(
                                    String.format(
                                            "Project \"%s\" already exists.",
                                            name
                                    )
                            );
                        }
                );

        projectEntity.setName(name);

        projectEntity = projectRepository.saveAndFlush(projectEntity);

        return projectDtoFactory.makeProjectDto(projectEntity);
    }

    /**
     * Удаление проекта
     * DELETE http://localhost:8080/api/projects/2
     * @param projectId // id project for delete
     * @return AckDto (boolean answer)
     */
    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id") Long projectId) {

        getProjectOrThrowException(projectId);

        projectRepository
                .deleteById(projectId);

        return AckDto.makeDefault(true);
    }

    private ProjectEntity getProjectOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Project with \"%s\" doesn't exist",
                                        projectId
                                )
                        )
                );
    }
}
