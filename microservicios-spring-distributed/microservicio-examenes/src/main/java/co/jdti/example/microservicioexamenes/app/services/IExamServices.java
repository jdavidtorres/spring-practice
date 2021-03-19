package co.jdti.example.microservicioexamenes.app.services;

import co.jdti.example.commons.exam.models.entities.ExamEntity;
import co.jdti.example.commons.exam.models.entities.SubjectEntity;
import co.jdti.example.microserviciocommons.services.ICommonServices;

import java.util.List;

public interface IExamServices extends ICommonServices<ExamEntity> {

    List<ExamEntity> findByName(String term);

    List<SubjectEntity> findAllSubjects();
}
