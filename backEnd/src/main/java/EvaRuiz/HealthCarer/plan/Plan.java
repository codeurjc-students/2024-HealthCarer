package EvaRuiz.HealthCarer.plan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private int distance;
    private Enum<PlanType> state;

    public Plan() {
    }

    public Plan(String name, Calendar startDate, Calendar endDate, int distance) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Enum<PlanType> getState() {
        return state;
    }

    public void setState(Enum<PlanType> state) {
        this.state = state;
    }

    public interface BasicAtt {}
}
