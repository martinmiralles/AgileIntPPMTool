package com.martin.ppmtool.services;

import com.martin.ppmtool.domain.Backlog;
import com.martin.ppmtool.domain.ProjectTask;
import com.martin.ppmtool.repositories.BacklogRepository;
import com.martin.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //All ProjectTasks to be added to a specific project, and not be null (that it exists)
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //A Backlog can only exist if the Project exists
        //Set the Backlog to a ProjectTask
        projectTask.setBacklog(backlog);

        //we want our project sequence to be sequential, ex. IDPRO-1 IDPRO-2
        Integer BacklogSequence = backlog.getPTSequence();

        //Update Backlog Sequence
        BacklogSequence++;

        //Sequence is then set
        backlog.setPTSequence((BacklogSequence));

        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL priority, when priority is null
        if(projectTask.getPriority()==null){//In the future, we need the projectTask.getPriority()==0 to handle the form
            projectTask.setPriority(3);
        }

        //INITIAL status, when status is null
        if(projectTask.getStatus()==""||projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
