package co.jdti.example.microserviciousuarios.app.services;

import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microserviciousuarios.app.models.repositories.IStudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentServicesImpl extends CommonServicesImpl<StudentEntity, IStudentRepository> implements IStudentServices {

}
