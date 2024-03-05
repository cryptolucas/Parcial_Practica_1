package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository;


    

    @Transactional
	public EspecialidadEntity createEspecialidad (EspecialidadEntity especialidad) throws IllegalOperationException {


		log.info("Inicia proceso de crear una especialidad");


        if (especialidad.getDescripcion().length() < 10) {

            throw new IllegalOperationException("La descripcion no es lo suficientemente amplia");

        }
        
        log.info("Termina el proceso de crear una especialidad");
		return especialidadRepository.save(especialidad);
	}

    
}
