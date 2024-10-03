package EvaRuiz.HealthCarer.take;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Take {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Calendar timestamp;

    public Take() {
    }

    public Take(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public interface BasicAtt {}
}
