package br.com.desafiotecnicooutforce.coupon_api.coupon.dto;

import br.com.desafiotecnicooutforce.coupon_api.coupon.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO usado para devolver os dados do cupom na resposta da API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDTO {

    private UUID id;
    private String codigo;
    private String descricao;
    private BigDecimal valorDesconto;
    private LocalDateTime dataExpiracao;
    private CouponStatus status;
    private Boolean publicado;
    private Boolean utilizado;
}