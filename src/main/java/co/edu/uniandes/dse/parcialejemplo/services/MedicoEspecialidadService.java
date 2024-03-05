package co.edu.uniandes.dse.parcialejemplo.services;


import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MedicoEspecialidadService {
    
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;


    @Transactional
	public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {


		log.info("Inicia proceso de agregar una especialidad a un medico");
        
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("medico no encontrado");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("especialidad no encontrada");

		especialidadEntity.get().getMedicos().add(medicoEntity.get());

		log.info("termina el proceso de agregar una especialidad a un medico");
        
		return especialidadEntity.get();
	}

}
