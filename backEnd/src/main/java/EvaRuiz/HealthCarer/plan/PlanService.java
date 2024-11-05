package EvaRuiz.HealthCarer.plan;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    private Plan checkPlanExistAndGet(Long id) {
        Optional<Plan> plan = planRepository.findById(id);
        if (plan.isPresent()) {
            return plan.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found");
        }
    }

    private void checkPlan(Plan plan) {
        if (plan.getName() == null || plan.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan name is required");
        }
        if (plan.getStartDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan start date is required");
        }
        if (plan.getEndDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan end date is required");
        }
        if (plan.getDistance() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan distance must be positive");
        }
        if (plan.getState() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan state is required");
        }
        if (plan.getMedications() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan must have at least one medication");
        }
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Plan getPlan(Long id) {
        return checkPlanExistAndGet(id);
    }

    public void createPlan(Plan plan) {
        checkPlan(plan);

        if(plan.getMedications() != null){
            plan.getMedications().forEach(medication -> {
                medication.addPlan(plan);
            });
        }
        planRepository.save(plan);
    }

    public void deletePlan(Plan plan) {
        if (plan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found");
        }
        if (!plan.getMedications().isEmpty()){
            plan.setMedications(null);
        }
        planRepository.delete(plan);
    }

    public Plan replacePlan(Plan plan) {
        Plan existingPlan = checkPlanExistAndGet(plan.getId());
        checkPlan(plan);
        existingPlan.setName(plan.getName());
        existingPlan.setStartDate(plan.getStartDate());
        existingPlan.setEndDate(plan.getEndDate());
        existingPlan.setDistance(plan.getDistance());
        existingPlan.setState(plan.getState());
        existingPlan.setMedications(plan.getMedications());
        return planRepository.save(existingPlan);
    }
}
