package com.martin.ppmtool.services;

import com.martin.ppmtool.domain.Project;
import com.martin.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project)
    {
        //Logic

        return projectRepository.save(project);
    }

}
