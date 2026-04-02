package br.com.desafiotecnicooutforce.coupon_api.coupon;

import br.com.desafiotecnicooutforce.coupon_api.repository.IGenericRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistência do cupom.
 */
public interface CouponRepository extends IGenericRepository<CouponEntity, UUID> {

    /**
     * Busca um cupom pelo id apenas se ele ainda não foi deletado.
     */
    Optional<CouponEntity> findByIdAndDeletedAtIsNull(UUID id);
}