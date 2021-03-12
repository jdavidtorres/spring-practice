package co.jdti.example.microservicioexamenes.app.services;

import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import co.jdti.example.microserviciocommons.models.entities.SubjectEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microservicioexamenes.app.models.repositories.IExamRepository;
import co.jdti.example.microservicioexamenes.app.models.repositories.ISubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamServicesImpl extends CommonServicesImpl<ExamEntity, IExamRepository> implements IExamServices {

    @Autowired
    private ISubjectRepository iSubjectRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExamEntity> findByName(String term) {
        return iRepository.findByName(term);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectEntity> findAllSubjects() {
        return iSubjectRepository.findAll();
    }
}
