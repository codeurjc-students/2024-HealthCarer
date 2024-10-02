package EvaRuiz.HealthCarer;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.plan.PlanRestController;
import EvaRuiz.HealthCarer.plan.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Calendar;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanRestControllerTests {

    @Mock
    private PlanService planService;
    private Plan plan;
    private List<Plan> planList;
    private ObjectWriter ow;

    @InjectMocks
    private PlanRestController planController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        Calendar start = Calendar.getInstance();
        start.set(2024, Calendar.OCTOBER, 1);
        Calendar end = Calendar.getInstance();
        end.set(2024, Calendar.OCTOBER, 30);
        plan = new Plan("Plan1", start, end, 30);
        planList = List.of(plan);
        mockMvc = MockMvcBuilders.standaloneSetup(planController).build();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    @AfterEach
    void tearDown() {
        plan = null;
        planList = null;
    }

    @Test
    @DisplayName("GET /plans")
    public void testGetPlans() throws Exception {
        when(planService.findAll()).thenReturn(planList);
        mockMvc.perform(get("/api/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(planList)))
                .andExpect(status().isOk());
        verify(planService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /plans/{id}")
    public void testGetPlanById() throws Exception {
        when(planService.getPlan(1L)).thenReturn(plan);
        mockMvc.perform(get("/api/plans/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(plan)))
                .andExpect(status().isOk());
        verify(planService, times(1)).getPlan(1L);
    }

    @Test
    @DisplayName("POST /plans")
    public void testPostPlan() throws Exception {
        when(planService.createPlan(any(Plan.class))).thenReturn(plan);
        mockMvc.perform(post("/api/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(plan)))
                .andExpect(status().isOk());
        verify(planService, times(1)).createPlan(any(Plan.class));
    }

    @Test
    @DisplayName("PUT /plans")
    public void testPutPlan() throws Exception {
        when(planService.updatePlan(any(Plan.class))).thenReturn(plan);
        mockMvc.perform(put("/api/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(plan)))
                .andExpect(status().isOk());
        verify(planService, times(1)).updatePlan(any(Plan.class));
    }

    @Test
    @DisplayName("DELETE /plans/{id}")
    public void testDeletePlan() throws Exception {
        when(planService.getPlan(1L)).thenReturn(plan);
        mockMvc.perform(delete("/api/plans/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(plan)))
                .andExpect(status().isOk());
        verify(planService, times(1)).deletePlan(plan);
    }
}

