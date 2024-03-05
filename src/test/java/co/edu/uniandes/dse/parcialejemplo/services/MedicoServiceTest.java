package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Calendar;
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
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)

public class MedicoServiceTest {

    @Autowired
	private MedicoService medicoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<MedicoEntity> medicoList = new ArrayList<>();

    private List<EspecialidadEntity> especialidadList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}


    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
		
	}


    private void insertData() {
       
        EspecialidadEntity especialidad1 = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad1);
        EspecialidadEntity especialidad2 = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad2);
        especialidadList.add(especialidad1);
        especialidadList.add(especialidad2);
        
    
        for (int i = 0; i < 3; i++) {
            // Crear y persistir datos ficticios para médicos
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(medicoEntity);
            medicoEntity.setEspecialidades(especialidadList);
            medicoList.add(medicoEntity);

    
        }

    }

    @Test
	void testCreateMedico() throws EntityNotFoundException, IllegalOperationException {

		MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
		newEntity.setEspecialidades(especialidadList);
		newEntity.setNombre("Raul");
        newEntity.setApellido("Gomez");
        newEntity.setRegistro("RM938330");

		MedicoEntity result = medicoService.createMedico(newEntity);
		assertNotNull(result);

		MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId()); // Busca medico con el nuevo ID

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getApellido(), entity.getApellido());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getRegistro(), entity.getRegistro());
		
	}

    @Test
	void testCreateMedicoWithInvalidRegistro() {
		assertThrows(IllegalOperationException.class, ()->{
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setEspecialidades(especialidadList);
            newEntity.setRegistro("5353");
			medicoService.createMedico(newEntity);
		});
	}








    
}
