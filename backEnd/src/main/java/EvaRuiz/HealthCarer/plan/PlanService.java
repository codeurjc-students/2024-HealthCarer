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

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Plan getPlan(Long id) {
        return checkPlanExistAndGet(id);
    }



    public Plan createPlan(Plan plan) {
        //TODO check preconditions
        return planRepository.save(plan);
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
        //TODO check preconditions
        existingPlan.setName(plan.getName());
        existingPlan.setStartDate(plan.getStartDate());
        existingPlan.setEndDate(plan.getEndDate());
        existingPlan.setDistance(plan.getDistance());
        existingPlan.setState(plan.getState());
        return planRepository.save(existingPlan);
    }
}
