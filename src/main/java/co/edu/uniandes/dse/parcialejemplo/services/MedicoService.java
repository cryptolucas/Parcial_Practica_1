package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Calendar;

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
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
	public MedicoEntity createMedico (MedicoEntity medico) throws IllegalOperationException {


		log.info("Inicia proceso de creacion del medico");


        if (!medico.getRegistro().startsWith("RM")) {
            throw new IllegalOperationException("El registro no inicia con el prefijo RM ");

        }
        
        log.info("Termina el proceso de creacion de un medico");
		return medicoRepository.save(medico);
	}





    
}
