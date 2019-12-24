package com.martin.ppmtool.services;

import com.martin.ppmtool.domain.Project;
import com.martin.ppmtool.exceptions.ProjectIdException;
import com.martin.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project)
    {
        //LOGIC
        try
        {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
}
