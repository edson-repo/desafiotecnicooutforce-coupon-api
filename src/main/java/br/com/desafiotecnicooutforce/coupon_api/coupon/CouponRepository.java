package br.com.desafiotecnicooutforce.coupon_api.coupon;

import br.com.desafiotecnicooutforce.coupon_api.repository.IGenericRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

/**
 * Contrato de persistência do cupom.
 */
public interface CouponRepository extends IGenericRepository<CouponEntity, UUID> {

    /**
     * Retorna apenas os cupons ativos ou desativados.
     */
    List<CouponEntity> findAllByStatus(CouponStatus status);

}