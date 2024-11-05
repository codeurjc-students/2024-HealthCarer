package EvaRuiz.HealthCarer.take;

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
public class TakeMapperImpl implements TakeMapper {

    private final DatatypeFactory datatypeFactory;

    public TakeMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public TakeDTO toDTO(Take take) {
        if ( take == null ) {
            return null;
        }

        long id = 0L;
        String timestamp = null;
        List<Medication> medications = null;

        if ( take.getId() != null ) {
            id = take.getId();
        }
        timestamp = xmlGregorianCalendarToString( calendarToXmlGregorianCalendar( take.getTimestamp() ), null );
        List<Medication> list = take.getMedications();
        if ( list != null ) {
            medications = new ArrayList<Medication>( list );
        }

        TakeDTO takeDTO = new TakeDTO( id, timestamp, medications );

        return takeDTO;
    }

    @Override
    public Take toDomain(TakeDTO takeDTO) {
        if ( takeDTO == null ) {
            return null;
        }

        Take take = new Take();

        take.setId( takeDTO.id() );
        take.setTimestamp( xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( takeDTO.timestamp(), null ) ) );
        List<Medication> list = takeDTO.medications();
        if ( list != null ) {
            take.setMedications( new ArrayList<Medication>( list ) );
        }

        return take;
    }

    @Override
    public List<TakeDTO> toDTOs(Collection<Take> takes) {
        if ( takes == null ) {
            return null;
        }

        List<TakeDTO> list = new ArrayList<TakeDTO>( takes.size() );
        for ( Take take : takes ) {
            list.add( toDTO( take ) );
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
