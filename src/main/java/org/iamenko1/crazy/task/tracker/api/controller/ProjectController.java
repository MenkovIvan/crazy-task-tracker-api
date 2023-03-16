package org.iamenko1.crazy.task.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.iamenko1.crazy.task.tracker.factory.ProjectDtoFactory;
import org.iamenko1.crazy.task.tracker.store.repository.ProjectRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectDtoFactory projectDtoFactory;

}
