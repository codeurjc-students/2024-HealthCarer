package EvaRuiz.HealthCarer;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.take.Take;
import EvaRuiz.HealthCarer.take.TakeRestController;
import EvaRuiz.HealthCarer.take.TakeService;
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

import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TakeRestControllerTests {

    @Mock
    private TakeService takeService;
    private Take take;
    private List<Take> takeList;
    private ObjectWriter ow;

    @InjectMocks
    private TakeRestController takeController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        take = new Take(Calendar.getInstance());
        takeList = List.of(take);
        mockMvc = MockMvcBuilders.standaloneSetup(takeController).build();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    @AfterEach
    void tearDown() {
        take = null;
        takeList = null;
    }

    @Test
    @DisplayName("GET /takes")
    public void testGetPlans() throws Exception {
        when(takeService.findAll()).thenReturn(takeList);
        mockMvc.perform(get("/api/takes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(takeList)))
                .andExpect(status().isOk());
        verify(takeService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /takes/{id}")
    public void testGetPlanById() throws Exception {
        when(takeService.getTake(1L)).thenReturn(take);
        mockMvc.perform(get("/api/takes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(take)))
                .andExpect(status().isOk());
        verify(takeService, times(1)).getTake(1L);
    }

    @Test
    @DisplayName("POST /takes")
    public void testPostPlan() throws Exception {
        when(takeService.createTake(any(Take.class))).thenReturn(take);
        mockMvc.perform(post("/api/takes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(take)))
                .andExpect(status().isOk());
        verify(takeService, times(1)).createTake(any(Take.class));
    }

    @Test
    @DisplayName("PUT /takes")
    public void testPutPlan() throws Exception {
        when(takeService.updateTake(any(Take.class))).thenReturn(take);
        mockMvc.perform(put("/api/takes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(take)))
                .andExpect(status().isOk());
        verify(takeService, times(1)).updateTake(any(Take.class));
    }

    @Test
    @DisplayName("DELETE /takes/{id}")
    public void testDeletePlan() throws Exception {
        when(takeService.getTake(1L)).thenReturn(take);
        mockMvc.perform(delete("/api/takes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(take)))
                .andExpect(status().isOk());
        verify(takeService, times(1)).deleteTake(take);
    }

}
