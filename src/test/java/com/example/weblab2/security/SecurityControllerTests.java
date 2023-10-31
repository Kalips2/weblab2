package com.example.weblab2.security;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.weblab2.configuration.SecurityConfiguration;
import com.example.weblab2.controllers.mvc.SecurityController;
import com.example.weblab2.security.shared.SecurityBaseMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Import(SecurityConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SecurityController.class)
public class SecurityControllerTests extends SecurityBaseMocker {

  @Autowired
  private MockMvc mockMvc;
  private final String BASE_SECURITY_URL = "/premium";


  @Test
  @WithMockUser(roles = "PREMIUM")
  public void testPremiumPageSuccessForPremiumUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_SECURITY_URL)
        )
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void testPremiumPageFailedForOrdinaryUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_SECURITY_URL)
        )
        .andExpect(status().is(401));
  }

  @Test
  @WithAnonymousUser
  public void testPremiumPageFailedForUnauthorizedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_SECURITY_URL)
        )
        .andExpect(status().is(401));
  }
}
