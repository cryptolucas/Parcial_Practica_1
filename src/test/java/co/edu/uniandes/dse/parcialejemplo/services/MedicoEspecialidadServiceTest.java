package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ MedicoEspecialidadService.class, EspecialidadService.class })
public class MedicoEspecialidadServiceTest {

    @Autowired
	private MedicoEspecialidadService medicoEspecialidadService;

	@Autowired
	private EspecialidadService especialidadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();


	private List<EspecialidadEntity> especialidadList = new ArrayList<>();
    private List<MedicoEntity> medicoList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
	}


    private void insertData() {
       
        MedicoEntity medico1 = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medico1);
        MedicoEntity medico2 = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medico2);
        medicoList.add(medico1);
        medicoList.add(medico2);
        
    
        for (int i = 0; i < 3; i++) {
            // Crear y persistir datos ficticios para médicos
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(especialidadEntity);
			especialidadEntity.setMedicos(medicoList);
            especialidadList.add(especialidadEntity);

    
        }

    }



    @Test
	void testEspecialidad() throws EntityNotFoundException, IllegalOperationException {

		EspecialidadEntity newEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
		newEspecialidad.setMedicos(medicoList);
        especialidadService.createEspecialidad(newEspecialidad);
		

		EspecialidadEntity especialidadEntity = medicoEspecialidadService.addEspecialidad(newEspecialidad.getMedicos().get(0).getId(), 
        newEspecialidad.getId());
        
		assertNotNull(especialidadEntity);

		assertEquals(especialidadEntity.getId(), newEspecialidad.getId());
		assertEquals(especialidadEntity.getDescripcion(), newEspecialidad.getDescripcion());
		assertEquals(especialidadEntity.getNombre(), newEspecialidad.getNombre());
		

	
	}


    @Test
	void testAddEspecialidadWithEmptyMedico() {
		assertThrows(EntityNotFoundException.class, ()->{
			EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
            medicoEspecialidadService.addEspecialidad(0L, newEntity.getId());
			
		});
	}


    @Test
	void testAddEspecialidadWithEmptyEspecialidad() {
		assertThrows(EntityNotFoundException.class, ()->{
            medicoEspecialidadService.addEspecialidad(medicoList.get(0).getId(), 0L);
			
		});
	}






    
}
