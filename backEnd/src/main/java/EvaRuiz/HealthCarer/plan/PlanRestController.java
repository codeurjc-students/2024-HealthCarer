package EvaRuiz.HealthCarer.plan;

import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/plans")
public class PlanRestController {

    @Autowired
    private PlanService planService;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private MedicationService medicationService;

    @PostConstruct
    public void init() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 10);
        planService.createPlan(new Plan("Plan 1", startDate, endDate, PlanType.ACTIVE, 10,  new ArrayList<Medication>()));

    }

    public interface PlanView extends Plan.BasicAtt {
    }

    @GetMapping("/")
    public Collection<PlanDTO> getPlans() {
        return planMapper.toDTOs(planService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> getOnePlan(@PathVariable long id) {

        Plan plan = planService.getPlan(id);
        if (plan != null) {
            return ResponseEntity.ok(planMapper.toDTO(plan));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/")
    public ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO planDTO) {
        Plan plan = planMapper.toDomain(planDTO);
        planService.createPlan(plan);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(plan.getId()).toUri();
        return ResponseEntity.created(location).body(planMapper.toDTO(plan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable long id, @RequestBody PlanDTO newPlanDTO) {
        Plan plan = planService.getPlan(id);
        Plan newPlan = planMapper.toDomain(newPlanDTO);
        newPlan.setId(plan.getId());
        newPlan = planService.replacePlan(newPlan);
        return ResponseEntity.ok(planMapper.toDTO(newPlan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlanDTO> deletePlan(@PathVariable long id) {
        Plan plan = planService.getPlan(id);
        planService.deletePlan(plan);
        return ResponseEntity.ok(planMapper.toDTO(plan));
    }
}
