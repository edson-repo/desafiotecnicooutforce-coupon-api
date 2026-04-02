package br.com.desafiotecnicooutforce.coupon_api.coupon.dto;

import br.com.desafiotecnicooutforce.coupon_api.coupon.CouponEntity;
import org.springframework.stereotype.Component;

/**
 * Responsável por converter os dados entre DTO e entidade.
 */
@Component
public class CouponMapper {

    /**
     * Converte o DTO de entrada em entidade,
     * aproveitando o método de criação com regras do domínio.
     */
    public CouponEntity toEntity(CouponRequestDTO requestDTO) {
        return CouponEntity.create(
                requestDTO.getCode(),
                requestDTO.getDescription(),
                requestDTO.getDiscountValue(),
                requestDTO.getExpirationDate(),
                requestDTO.getPublished()
        );
    }

    /**
     * Converte a entidade em DTO de resposta.
     */
    public CouponResponseDTO toResponseDTO(CouponEntity entity) {
        return new CouponResponseDTO(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.getStatus(),
                entity.getPublished(),
                entity.getRedeemed()
        );
    }
}