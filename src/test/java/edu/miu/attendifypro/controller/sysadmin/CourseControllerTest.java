package edu.miu.attendifypro.controller.sysadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendifypro.config.ContextUser;
import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.CourseService;
import edu.miu.attendifypro.service.MessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ContextUser contextUser;

    @MockBean
    private MessagingService messagingService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<CourseResponse> courseList;
    private CourseResponse courseResponse;
    private Page<CourseResponse> coursePage;
    private CourseCreateRequest courseCreateRequest;
    private CourseUpdateRequest courseUpdateRequest;

    @BeforeEach
    public void setup() {
        courseResponse = new CourseResponse();
        courseList = Collections.singletonList(courseResponse);
        coursePage = new PageImpl<>(courseList);
        courseCreateRequest = new CourseCreateRequest();
        courseUpdateRequest = new CourseUpdateRequest();
    }

    @Test
    public void getAllCoursesTest() throws Exception {
        ServiceResponse<List<CourseResponse>> serviceResponse = ServiceResponse.of(courseList,AppStatusCode.S20000);

        when(courseService.getAllCourses()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        mockMvc.perform(get("/sys-admin/course/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data[0]").exists());
    }

    @Test
    public void getCoursesTest() throws Exception {
        ServiceResponse<Page<CourseResponse>> serviceResponse = ServiceResponse.of(coursePage,AppStatusCode.S20000);

        when(courseService.getCoursePage(any(PageRequest.class))).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        mockMvc.perform(get("/sys-admin/course")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data.content[0]").exists());
    }

    @Test
    public void getCourseTest() throws Exception {
        ServiceResponse<CourseResponse> serviceResponse = ServiceResponse.of(courseResponse,AppStatusCode.S20005);

        when(courseService.getCourse(anyLong())).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        mockMvc.perform(get("/sys-admin/course/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void createCourseTest() throws Exception {
        ServiceResponse<CourseResponse> serviceResponse = ServiceResponse.of(courseResponse,AppStatusCode.S20001);

        when(courseService.createCourse(any(CourseCreateRequest.class))).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        mockMvc.perform(post("/sys-admin/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void updateCourseTest() throws Exception {
        ServiceResponse<CourseResponse> serviceResponse = ServiceResponse.of(courseResponse,AppStatusCode.S20002);

        when(courseService.updateCourse(anyLong(), any(CourseUpdateRequest.class))).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        mockMvc.perform(put("/sys-admin/course/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void deleteCourseTest() throws Exception {
        ServiceResponse<Boolean> serviceResponse = ServiceResponse.of(true,AppStatusCode.S20003);

        when(courseService.deleteCourse(anyLong())).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        mockMvc.perform(delete("/sys-admin/course/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data", is(true)));
    }
}
