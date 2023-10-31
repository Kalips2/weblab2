package com.example.weblab2.security;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.weblab2.configuration.SecurityConfiguration;
import com.example.weblab2.controllers.rest.LabelRestController;
import com.example.weblab2.security.shared.SecurityBaseMocker;
import com.example.weblab2.services.LabelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Import(SecurityConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LabelRestController.class)
public class LabelRestControllerTest extends SecurityBaseMocker {
  @MockBean
  private LabelService labelService;

  @Autowired
  private MockMvc mockMvc;

  private final String BASE_REST_LABEL_URL = "/rest/labels";

  @Test
  @WithAnonymousUser
  public void testGetAllReturnSuccessForNonAuthorizedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_REST_LABEL_URL)
        )
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testPGetAllPaginationReturnSuccessForAdminUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_REST_LABEL_URL + "/pagination")
        )
        .andExpect(status().is(200));
  }

  @Test
  @WithMockUser(roles = {"USER", "PREMIUM"})
  public void testPGetAllPaginationReturnUnauthorizedForNonAdminUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_REST_LABEL_URL + "/pagination")
        )
        .andExpect(status().is(401));
  }

  @Test
  @WithMockUser(roles = {"USER", "PREMIUM", "ADMIN"})
  public void testGetByIdReturnSuccessForAuthenticatedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_REST_LABEL_URL + "/1")
        )
        .andExpect(status().is(200));
  }

  @Test
  @WithAnonymousUser
  public void testGetByIdReturnFailureForNonAuthenticatedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_REST_LABEL_URL + "/1")
        )
        .andExpect(status().is(401));
  }


  @Test
  @WithMockUser(roles = {"USER", "PREMIUM", "ADMIN"})
  public void testCreateReturnSuccessForAuthenticatedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(BASE_REST_LABEL_URL + "/create")
            .param("name", "Name")
            .param("coordinates", "-10.0, -10.0")
        )
        .andExpect(status().is(200));
  }

  @Test
  @WithAnonymousUser
  public void testCreateReturnFailureForNonAuthenticatedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(BASE_REST_LABEL_URL + "/create")
            .param("name", "Name")
            .param("coordinates", "-10, -10")
        )
        .andExpect(status().is(401));
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  public void testDeleteReturnSuccessForAdminUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(BASE_REST_LABEL_URL + "/delete")
            .param("id", String.valueOf(1))
        )
        .andExpect(status().is(200));
  }

  @Test
  @WithMockUser(roles = {"USER", "PREMIUM"})
  public void testDeleteReturnUnauthorizedForNonAdminUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(BASE_REST_LABEL_URL + "/delete")
            .param("id", String.valueOf(1))
        )
        .andExpect(status().is(401));
  }
}
