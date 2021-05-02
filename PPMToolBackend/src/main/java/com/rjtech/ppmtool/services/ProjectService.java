package com.rjtech.ppmtool.services;

import com.rjtech.ppmtool.domain.Project;
import com.rjtech.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        //Business Logic will come here

       return projectRepository.save(project);
    }
}
