package be.bookstore_backend;

import be.bookstore_backend.config.AuthTokenFilter;
import be.bookstore_backend.config.JwtUtils;
import be.bookstore_backend.config.SecurityConfig;
import be.bookstore_backend.controller.CartController;
import be.bookstore_backend.service.CartService;
import be.bookstore_backend.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@Import(SecurityConfig.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private AuthTokenFilter authTokenFilter;

    @MockitoBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void testGetCart() throws Exception {
        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testAddToCart() throws Exception {
        CartController.CartItemRequest request = new CartController.CartItemRequest();
        request.setBookId(1L);
        request.setQuantity(2);

        mockMvc.perform(post("/api/cart")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser
    public void testUpdateCartItemQuantity() throws Exception {
        CartController.UpdateQuantityRequest request = new CartController.UpdateQuantityRequest();
        request.setQuantity(5);

        mockMvc.perform(put("/api/cart/{itemId}", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testRemoveFromCart() throws Exception {
        mockMvc.perform(delete("/api/cart/{itemId}", 1L)
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testClearCart() throws Exception {
        mockMvc.perform(delete("/api/cart")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCheckout() throws Exception {
        mockMvc.perform(post("/api/cart/checkout")
                .with(csrf()))
                .andExpect(status().isOk());
    }
}
