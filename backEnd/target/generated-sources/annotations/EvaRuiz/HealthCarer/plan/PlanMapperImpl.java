package EvaRuiz.HealthCarer.plan;

import EvaRuiz.HealthCarer.medication.Medication;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-05T08:06:53+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class PlanMapperImpl implements PlanMapper {

    private final DatatypeFactory datatypeFactory;

    public PlanMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public PlanDTO toDTO(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String startDate = null;
        String endDate = null;
        int distance = 0;
        Enum<PlanType> state = null;
        List<Medication> medications = null;

        if ( plan.getId() != null ) {
            id = plan.getId();
        }
        name = plan.getName();
        startDate = xmlGregorianCalendarToString( calendarToXmlGregorianCalendar( plan.getStartDate() ), null );
        endDate = xmlGregorianCalendarToString( calendarToXmlGregorianCalendar( plan.getEndDate() ), null );
        distance = plan.getDistance();
        state = plan.getState();
        List<Medication> list = plan.getMedications();
        if ( list != null ) {
            medications = new ArrayList<Medication>( list );
        }

        PlanDTO planDTO = new PlanDTO( id, name, startDate, endDate, distance, state, medications );

        return planDTO;
    }

    @Override
    public Plan toDomain(PlanDTO planDTO) {
        if ( planDTO == null ) {
            return null;
        }

        Plan plan = new Plan();

        List<Medication> list = planDTO.medications();
        if ( list != null ) {
            plan.setMedications( new ArrayList<Medication>( list ) );
        }
        plan.setId( planDTO.id() );
        plan.setName( planDTO.name() );
        plan.setStartDate( xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( planDTO.startDate(), null ) ) );
        plan.setEndDate( xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( planDTO.endDate(), null ) ) );
        plan.setDistance( planDTO.distance() );
        plan.setState( planDTO.state() );

        return plan;
    }

    @Override
    public List<PlanDTO> toDTOs(Collection<Plan> plans) {
        if ( plans == null ) {
            return null;
        }

        List<PlanDTO> list = new ArrayList<PlanDTO>( plans.size() );
        for ( Plan plan : plans ) {
            list.add( toDTO( plan ) );
        }

        return list;
    }

    private String xmlGregorianCalendarToString( XMLGregorianCalendar xcal, String dateFormat ) {
        if ( xcal == null ) {
            return null;
        }

        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private XMLGregorianCalendar calendarToXmlGregorianCalendar( Calendar cal ) {
        if ( cal == null ) {
            return null;
        }

        GregorianCalendar gcal = new GregorianCalendar( cal.getTimeZone() );
        gcal.setTimeInMillis( cal.getTimeInMillis() );
        return datatypeFactory.newXMLGregorianCalendar( gcal );
    }

    private XMLGregorianCalendar stringToXmlGregorianCalendar( String date, String dateFormat ) {
        if ( date == null ) {
            return null;
        }

        try {
            if ( dateFormat != null ) {
                DateFormat df = new SimpleDateFormat( dateFormat );
                GregorianCalendar c = new GregorianCalendar();
                c.setTime( df.parse( date ) );
                return datatypeFactory.newXMLGregorianCalendar( c );
            }
            else {
                return datatypeFactory.newXMLGregorianCalendar( date );
            }
        }
        catch ( ParseException ex ) {
            throw new RuntimeException( ex );
        }
    }

    private Calendar xmlGregorianCalendarToCalendar( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis( xcal.toGregorianCalendar().getTimeInMillis() );
        return cal;
    }
}
