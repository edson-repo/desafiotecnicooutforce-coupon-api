package br.com.desafiotecnicooutforce.coupon_api.coupon;

import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistência do cupom.
 *
 * A ideia aqui é manter a aplicação dependente desta interface,
 * e não diretamente do Spring Data.
 */
public interface CouponRepository {

    /**
     * Salva um cupom no banco.
     */
    CouponEntity save(CouponEntity coupon);

    /**
     * Busca um cupom ativo pelo id.
     * Esse método será o mais usado nos fluxos normais da aplicação.
     */
    Optional<CouponEntity> findActiveById(UUID id);

    /**
     * Busca um cupom pelo id, incluindo registros já deletados.
     * Esse método será útil no fluxo de delete para saber
     * se o cupom já foi excluído antes.
     */
    Optional<CouponEntity> findById(UUID id);

    /**
     * Busca apenas cupons que ainda não passaram por soft delete.
     */
    Optional<CouponEntity> findByIdAndDeletedAtIsNull(UUID id);

}