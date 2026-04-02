package br.com.desafiotecnicooutforce.coupon_api.coupon;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void cleanDatabase() {
        couponRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar cupom com sucesso")
    void shouldCreateCouponSuccessfully() throws Exception {
        String requestBody = """
                {
                  "code": "ABC-123",
                  "description": "Cupom de desconto",
                  "discountValue": 10.5,
                  "expirationDate": "%s",
                  "published": true
                }
                """.formatted(LocalDateTime.now().plusDays(2));

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("ABC123")))
                .andExpect(jsonPath("$.description", is("Cupom de desconto")))
                .andExpect(jsonPath("$.discountValue", is(10.5)))
                .andExpect(jsonPath("$.status", is("ACTIVE")))
                .andExpect(jsonPath("$.published", is(true)))
                .andExpect(jsonPath("$.redeemed", is(false)));
    }

    @Test
    @DisplayName("Deve buscar cupom por id")
    void shouldFindCouponById() throws Exception {
        CouponEntity coupon = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("15.00"),
                LocalDateTime.now().plusDays(2),
                true
        );

        CouponEntity savedCoupon = couponRepository.save(coupon);

        mockMvc.perform(get("/coupon/{id}", savedCoupon.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedCoupon.getId().toString())))
                .andExpect(jsonPath("$.code", is("ABC123")))
                .andExpect(jsonPath("$.description", is("Cupom de teste")));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar cupom inexistente")
    void shouldReturnNotFoundWhenCouponDoesNotExist() throws Exception {
        mockMvc.perform(get("/coupon/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Cupom não encontrado.")));
    }

    @Test
    @DisplayName("Deve fazer soft delete com sucesso")
    void shouldSoftDeleteCouponSuccessfully() throws Exception {
        CouponEntity coupon = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("20.00"),
                LocalDateTime.now().plusDays(2),
                false
        );

        CouponEntity savedCoupon = couponRepository.save(coupon);

        mockMvc.perform(delete("/coupon/{id}", savedCoupon.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar conflito ao tentar deletar cupom já deletado")
    void shouldReturnConflictWhenCouponIsAlreadyDeleted() throws Exception {
        CouponEntity coupon = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("20.00"),
                LocalDateTime.now().plusDays(2),
                false
        );

        CouponEntity savedCoupon = couponRepository.save(coupon);

        mockMvc.perform(delete("/coupon/{id}", savedCoupon.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/coupon/{id}", savedCoupon.getId()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("O cupom já foi deletado.")));
    }
}