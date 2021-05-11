package com.rjtech.ppmtool.services;

import com.rjtech.ppmtool.domain.Backlog;
import com.rjtech.ppmtool.domain.Project;
import com.rjtech.ppmtool.exceptions.ProjectIdException;
import com.rjtech.ppmtool.repositories.BacklogRepository;
import com.rjtech.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveProject(Project project) {
        //Business Logic will come here
        try {
            String projectIdentifier = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(projectIdentifier);

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
            }
            return projectRepository.save(project);
        } catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exist");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null) {
            throw  new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw  new ProjectIdException("Cannot delete project with ID '" + projectId.toUpperCase() + "'. This project does not exist");
        } else {
            projectRepository.delete(project);
        }
    }

    public Project updateProject(Project updatedProject){
        updatedProject.setProjectIdentifier(updatedProject.getProjectIdentifier().toUpperCase());

        Project oldProject = projectRepository.findByProjectIdentifier(updatedProject.getProjectIdentifier());

        if(oldProject == null){
            throw  new ProjectIdException("Cannot update project with ID '" + updatedProject.getProjectIdentifier().toUpperCase() + "'. This project does not exist");
        }
        updatedProject.setId(oldProject.getId());
        return projectRepository.save(updatedProject);
    }
}
