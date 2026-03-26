package be.bookstore_backend;

import be.bookstore_backend.config.AuthTokenFilter;
import be.bookstore_backend.config.JwtUtils;
import be.bookstore_backend.config.SecurityConfig;
import be.bookstore_backend.controller.OrderController;
import be.bookstore_backend.service.OrderService;
import be.bookstore_backend.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthTokenFilter authTokenFilter;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser
    public void testGetOrderHistory() throws Exception {
        Mockito.when(orderService.getUserOrderHistory(Mockito.anyString())).thenReturn(Collections.emptyList());
        
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }
}
