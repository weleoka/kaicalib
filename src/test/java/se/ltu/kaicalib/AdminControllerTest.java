package se.ltu.kaicalib;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.ltu.kaicalib.account.web.AdminController;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@ActiveProfiles("dev")
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void whenTestAdminCredentials_thenOk() throws Exception {
        mockMvc.perform(get("/admin/dashboard")).andExpect(status().isUnauthorized());

        mockMvc.perform(get("/admin/dashboard")
            .with(httpBasic("admin", "adminPass"))).andExpect(status().isOk());

        mockMvc.perform(get("/patron/profile")
            .with(user("admin").password("adminPass").roles("ADMIN")))
            .andExpect(status().isForbidden());
    }

    @Test
    public void whenTestUserCredentials_thenOk() throws Exception {
        mockMvc.perform(get("/patron/profile")).andExpect(status().isFound());

        mockMvc.perform(get("/patron/profile")
            .with(user("user").password("userPass").roles("USER")))
            .andExpect(status().isOk());

        mockMvc.perform(get("/admin/dashboard")
            .with(user("user").password("userPass").roles("USER")))
            .andExpect(status().isForbidden());
    }

    @Test
    public void givenAnyUser_whenGetGuestPage_thenOk() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());

        mockMvc.perform(get("/testHello")
            .with(user("user").password("userPass").roles("USER")))
            .andExpect(status().isOk());

        mockMvc.perform(get("/testHello")
            .with(httpBasic("admin", "adminPass")))
            .andExpect(status().isOk());
    }

}
