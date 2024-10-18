package EvaRuiz.HealthCarer.medication;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-18T11:17:05+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class MedicationMapperImpl implements MedicationMapper {

    @Override
    public MedicationDTO toDTO(Medication medication) {
        if ( medication == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String boxImage = null;
        String pillImage = null;
        float stock = 0.0f;
        String instructions = null;
        float dose = 0.0f;
        List<User> users = null;
        List<Plan> plans = null;

        id = medication.getId();
        name = medication.getName();
        boxImage = medication.getBoxImage();
        pillImage = medication.getPillImage();
        stock = medication.getStock();
        instructions = medication.getInstructions();
        dose = medication.getDose();
        List<User> list = medication.getUsers();
        if ( list != null ) {
            users = new ArrayList<User>( list );
        }
        List<Plan> list1 = medication.getPlans();
        if ( list1 != null ) {
            plans = new ArrayList<Plan>( list1 );
        }

        MedicationDTO medicationDTO = new MedicationDTO( id, name, boxImage, pillImage, stock, instructions, dose, users, plans );

        return medicationDTO;
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
        List<User> list = medicationDTO.users();
        if ( list != null ) {
            medication.setUsers( new ArrayList<User>( list ) );
        }
        List<Plan> list1 = medicationDTO.plans();
        if ( list1 != null ) {
            medication.setPlans( new ArrayList<Plan>( list1 ) );
        }

        return medication;
    }
}
