package co.jdti.example.microservicioexamenes.app.services;

import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microservicioexamenes.app.models.repositories.IExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamServicesImpl extends CommonServicesImpl<ExamEntity, IExamRepository> implements IExamServices {
}
