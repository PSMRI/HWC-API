// package com.iemr.mmu.controller.nurse.main.anc;
//
// import static org.mockito.Mockito.mock;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
//
// import
// com.iemr.mmu.controller.nurse.main.ncdScreening.NCDScreeningController;
// import com.iemr.mmu.service.ncdscreening.NCDScreeningService;
// import com.iemr.mmu.service.ncdscreening.NCDScreeningServiceImpl;
//
// @RunWith(SpringRunner.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// public class TestNCDScreeningController {
//
// private NCDScreeningController controllerMock;
// private NCDScreeningServiceImpl serviceMock;
//
// @Autowired
// private MockMvc mockMvc;
//
// @Before
// public void initialize() {
// serviceMock = mock(NCDScreeningServiceImpl .class);
// controllerMock = new NCDScreeningController();
// controllerMock.setNcdScreeningService(serviceMock);
// }
//
//
// @Test
// public void fetchNCDScreeing() {
// try {
//
// String json = "{\"benRegID\":\"1234\",\"benVisitID\":\"123\"}";
//
// mockMvc.perform(post("/nurse/fetch/ncdScreeningDetails")
// .contentType(MediaType.APPLICATION_JSON)
// .content(json)).andExpect(content().string("{\"data\":{\"response\":\"Ben
// Adherence data update
// successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
//
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// }
