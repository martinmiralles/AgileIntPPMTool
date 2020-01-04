package com.martin.ppmtool.services;

import com.martin.ppmtool.domain.Backlog;
import com.martin.ppmtool.domain.Project;
import com.martin.ppmtool.domain.User;
import com.martin.ppmtool.exceptions.ProjectIdException;
import com.martin.ppmtool.repositories.BacklogRepository;
import com.martin.ppmtool.repositories.ProjectRepository;
import com.martin.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username)
    {
        //LOGIC
        try
        {
            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null)
            {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null)
            {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }
        catch (Exception e)
        {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()+"' already exists");
        }

    }

    public Project findProjectByIdentifier(String projectId)
    {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null)
        {
            throw new ProjectIdException("Project ID does not exist");
        }

        //If successful and a project if found
        return project;
    }

    public Iterable<Project> findAllProjects()
    {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier (String projectId)
    {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null)
        {
            throw new ProjectIdException("Cannot delete Project with the ID '" + projectId + "'.  This project does not exist");
        }

        projectRepository.delete(project);
    }
}
