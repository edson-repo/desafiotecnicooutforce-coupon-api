package br.com.desafiotecnicooutforce.coupon_api.exception;

/**
 * Exceção lançada quando tentam deletar um cupom já deletado.
 */
public class CouponAlreadyDeletedException extends BusinessException {

    public CouponAlreadyDeletedException(String message) {
        super(message);
    }
}