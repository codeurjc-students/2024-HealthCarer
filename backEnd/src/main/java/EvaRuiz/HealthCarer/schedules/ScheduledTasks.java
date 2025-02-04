package EvaRuiz.HealthCarer.schedules;

import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.repository.TreatmentRepository;
import EvaRuiz.HealthCarer.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ScheduledTasks {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private EmailServiceImpl emailService;



    @Scheduled(fixedRate = 5000)
    @Transactional
    public void checkIntakeDates() {
        List<Treatment> treatments = treatmentRepository.findAll();
        for (Treatment treatment : treatments) {
            LocalDateTime sendMail = treatment.checkIntakeDates();
            if (sendMail != null) {
                String subject = "Hora de tomar la medicacion";
                StringBuilder text = new StringBuilder("Debes tomar la siguiente medicacion: ");
                for (Medication medication : treatment.getMedications()) {
                    text.append(medication.getName()).append(" ");
                    text.append(medication.getDose()).append("mg ");
                }
                emailService.sendSimpleMessage(treatment.getUser().getEmail(), subject, text.toString());
                treatment.setStartDate(Date.from(sendMail.atZone(Calendar.getInstance().getTimeZone().toZoneId()).toInstant()));
                treatmentRepository.save(treatment);
            }
        }
    }

}
