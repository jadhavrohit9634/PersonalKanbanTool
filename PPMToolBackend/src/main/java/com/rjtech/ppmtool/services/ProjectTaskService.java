package com.rjtech.ppmtool.services;

import com.rjtech.ppmtool.domain.Backlog;
import com.rjtech.ppmtool.domain.ProjectTask;
import com.rjtech.ppmtool.repositories.BacklogRepository;
import com.rjtech.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //Exceptions:  Project not found

        //PTs to be added to a specific project. project != null, Backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the backlog to the project task
        projectTask.setBacklog(backlog);
        //we want our project task sequence to be of the form TEST1-1, TEST1-2
        Integer BacklogSequence = backlog.getPTSequence();
        //Update the backlog sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        //Add sequence to project task
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL PRIORITY when priority is null
        if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
            projectTask.setPriority(3);
        }
        //INITIAL STATUS when status is null
        if(projectTask.getStatus() == null || projectTask.getStatus() == ""){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
