package com.martin.ppmtool.repositories;

import com.martin.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository <ProjectTask, Long> {
}
