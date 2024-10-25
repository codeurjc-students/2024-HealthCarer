package EvaRuiz.HealthCarer.medication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-25T08:53:29+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class MedicationMapperImpl implements MedicationMapper {

    @Override
    public MedicationDTO toDTO(Medication medication) {
        if ( medication == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String boxImage = null;
        String pillImage = null;
        float stock = 0.0f;
        String instructions = null;
        float dose = 0.0f;

        if ( medication.getId() != null ) {
            id = medication.getId();
        }
        name = medication.getName();
        boxImage = medication.getBoxImage();
        pillImage = medication.getPillImage();
        stock = medication.getStock();
        instructions = medication.getInstructions();
        dose = medication.getDose();

        MedicationDTO medicationDTO = new MedicationDTO( id, name, boxImage, pillImage, stock, instructions, dose );

        return medicationDTO;
    }

    @Override
    public Medication toDomain(MedicationDTO medicationDTO) {
        if ( medicationDTO == null ) {
            return null;
        }

        Medication medication = new Medication();

        medication.setId( medicationDTO.id() );
        medication.setName( medicationDTO.name() );
        medication.setBoxImage( medicationDTO.boxImage() );
        medication.setPillImage( medicationDTO.pillImage() );
        medication.setStock( medicationDTO.stock() );
        medication.setInstructions( medicationDTO.instructions() );
        medication.setDose( medicationDTO.dose() );

        return medication;
    }

    @Override
    public List<MedicationDTO> toDTOs(Collection<Medication> medications) {
        if ( medications == null ) {
            return null;
        }

        List<MedicationDTO> list = new ArrayList<MedicationDTO>( medications.size() );
        for ( Medication medication : medications ) {
            list.add( toDTO( medication ) );
        }

        return list;
    }
}
