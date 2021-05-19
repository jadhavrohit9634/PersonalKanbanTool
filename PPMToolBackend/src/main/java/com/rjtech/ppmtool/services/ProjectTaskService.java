package com.rjtech.ppmtool.services;

import com.rjtech.ppmtool.domain.Backlog;
import com.rjtech.ppmtool.domain.Project;
import com.rjtech.ppmtool.domain.ProjectTask;
import com.rjtech.ppmtool.exceptions.ProjectNotFoundException;
import com.rjtech.ppmtool.repositories.BacklogRepository;
import com.rjtech.ppmtool.repositories.ProjectRepository;
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

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        //Exceptions:  Project not found
        try {
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
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL PRIORITY when priority is null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            //INITIAL STATUS when status is null
            if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }

    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        Project project = projectRepository.findByProjectIdentifier(backlog_id);
        if(project == null){
            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        //make sure we are searching on an existing backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");
        }
        //make sure that our task exist
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task with ID: '" + pt_id + "' not found");
        }

        //make sure that the backlog/project id  in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task with ID: '" + pt_id + "' does not exist in project: " + backlog_id);
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){

        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTaskRepository.delete(projectTask);
    }

}
