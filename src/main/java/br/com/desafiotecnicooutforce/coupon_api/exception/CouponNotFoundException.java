package br.com.desafiotecnicooutforce.coupon_api.exception;

/**
 * Exceção lançada quando o cupom não é encontrado.
 */
public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(String message) {
        super(message);
    }
}