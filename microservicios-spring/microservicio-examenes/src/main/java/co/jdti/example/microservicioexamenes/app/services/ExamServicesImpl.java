package co.jdti.example.microservicioexamenes.app.services;

import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microservicioexamenes.app.models.repositories.IExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamServicesImpl extends CommonServicesImpl<ExamEntity, IExamRepository> implements IExamServices {

    @Override
    @Transactional(readOnly = true)
    public List<ExamEntity> findByName(String term) {
        return iRepository.findByName(term);
    }
}
