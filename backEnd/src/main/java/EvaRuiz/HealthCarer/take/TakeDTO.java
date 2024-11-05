package EvaRuiz.HealthCarer.take;


import java.util.Calendar;

public class TakeDTO {
    private Long id;
    private Calendar timestamp;
    private Long[] medications;
    private Long userId;

    public TakeDTO() {
    }

    public TakeDTO(Long id, Calendar timestamp, Long[] medications, Long userId) {
        this.id = id;
        this.timestamp = timestamp;
        this.medications = medications;
        this.userId = userId;
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

    public Long[] getMedications() {
        return medications;
    }

    public void setMedications(Long[] medications) {
        this.medications = medications;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
