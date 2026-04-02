package br.com.desafiotecnicooutforce.coupon_api.coupon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO usado para receber os dados de criação do cupom.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDTO {

    @NotBlank(message = "O código do cupom é obrigatório.")
    private String code;

    @NotBlank(message = "A descrição do cupom é obrigatória.")
    private String description;

    @NotNull(message = "O valor de desconto é obrigatório.")
    @DecimalMin(value = "0.5", inclusive = true, message = "O valor mínimo de desconto é 0.5.")
    private BigDecimal discountValue;

    @NotNull(message = "A data de expiração é obrigatória.")
    private LocalDateTime expirationDate;

    /**
     * Pode vir nulo. Nesse caso, a entidade assume false por padrão.
     */
    private Boolean published;
}