package EvaRuiz.HealthCarer.plan;


import java.util.Calendar;

public class PlanDTO {
    private Long id;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private PlanType type;
    private int distance;
    private Long[] medications;
    private Long[] users;

    public PlanDTO() {
    }

    public PlanDTO(Long id, Long[] users, int distance, Long[] medications, PlanType type, Calendar endDate, Calendar startDate, String name) {
        this.id = id;
        this.users = users;
        this.distance = distance;
        this.medications = medications;
        this.type = type;
        this.endDate = endDate;
        this.startDate = startDate;
        this.name = name;
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

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Long[] getMedications() {
        return medications;
    }

    public void setMedications(Long[] medications) {
        this.medications = medications;
    }

    public Long[] getUsers() {
        return users;
    }

    public void setUsers(Long[] users) {
        this.users = users;
    }
}
