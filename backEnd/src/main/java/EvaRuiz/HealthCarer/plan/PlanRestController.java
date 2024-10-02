package EvaRuiz.HealthCarer.plan;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanRestController {

    @Autowired
    private PlanService planService;

    public interface PlanView extends Plan.BasicAtt {
    }

    @GetMapping("/")
    @JsonView(PlanView.class)
    public ResponseEntity<List<Plan>> getPlans() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/{id}")
    @JsonView(PlanView.class)
    public ResponseEntity<Plan> getOnePlan(@PathVariable long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }

    @PostMapping("/")
    @JsonView(Plan.BasicAtt.class)
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan newPlan = planService.createPlan(plan);
        return ResponseEntity.ok(newPlan);
    }

    @PutMapping("/")
    @JsonView(Plan.BasicAtt.class)
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan plan) {
        Plan updatedPlan = planService.updatePlan(plan);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Plan> deletePlan(@PathVariable long id) {
        Plan plan = planService.getPlan(id);
        planService.deletePlan(plan);
        return ResponseEntity.ok(plan);
    }
}
