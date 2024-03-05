package co.edu.uniandes.dse.parcialejemplo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;



@Data
@Entity

public class EspecialidadEntity {

    @Id
    private Long id;

    private String nombre;
    private String descripcion;

    @PodamExclude
	@ManyToMany(mappedBy = "especialidades")
	private List<MedicoEntity> medicos = new ArrayList<>();

    
}
