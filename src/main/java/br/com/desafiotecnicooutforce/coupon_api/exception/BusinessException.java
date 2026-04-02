package br.com.desafiotecnicooutforce.coupon_api.exception;

/**
 * Exceção usada para violações de regra de negócio.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}