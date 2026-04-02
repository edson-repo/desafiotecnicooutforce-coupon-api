package br.com.desafiotecnicooutforce.coupon_api.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade que representa o cupom no sistema.
 *
 * Além de mapear a tabela, ela já concentra as regras principais
 * de criação do cupom, deixando a lógica mais próxima do domínio.
 */
@Entity
@Table(name = "tb_coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private Boolean published;

    @Column(nullable = false)
    private Boolean redeemed;

    @Column
    private LocalDateTime deletedAt;

    /**
     * Método de criação do cupom já aplicando as regras do domínio.
     */
    public static CouponEntity create(
            String code,
            String description,
            BigDecimal discountValue,
            LocalDateTime expirationDate,
            Boolean published
    ) {
        String sanitizedCode = sanitizeCode(code);
        validateCode(sanitizedCode);
        validateDiscountValue(discountValue);
        validateExpirationDate(expirationDate);

        CouponEntity coupon = new CouponEntity();
        coupon.setId(UUID.randomUUID());
        coupon.setCode(sanitizedCode);
        coupon.setDescription(description != null ? description.trim() : null);
        coupon.setDiscountValue(discountValue);
        coupon.setExpirationDate(expirationDate);
        coupon.setStatus(CouponStatus.ACTIVE);
        coupon.setPublished(published != null ? published : Boolean.FALSE);
        coupon.setRedeemed(Boolean.FALSE);
        coupon.setDeletedAt(null);

        return coupon;
    }

    /**
     * Prepara o cupom para exclusão lógica.
     * A validação mais detalhada será tratada no fluxo de serviço.
     */
    public void softDelete() {
        this.status = CouponStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    /**
     * Remove qualquer caractere que não seja letra ou número.
     */
    private static String sanitizeCode(String code) {
        if (code == null) {
            return null;
        }

        return code.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * O código final precisa ter exatamente 6 caracteres alfanuméricos.
     */
    private static void validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("O código do cupom é obrigatório.");
        }

        if (code.length() != 6) {
            throw new IllegalArgumentException("O código do cupom deve ter exatamente 6 caracteres alfanuméricos.");
        }
    }

    /**
     * O desconto mínimo permitido é 0.5.
     */
    private static void validateDiscountValue(BigDecimal discountValue) {
        if (discountValue == null) {
            throw new IllegalArgumentException("O valor de desconto é obrigatório.");
        }

        if (discountValue.compareTo(new BigDecimal("0.5")) < 0) {
            throw new IllegalArgumentException("O valor mínimo de desconto é 0.5.");
        }
    }

    /**
     * Não permite criar cupom com data de expiração no passado.
     */
    private static void validateExpirationDate(LocalDateTime expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("A data de expiração é obrigatória.");
        }

        if (expirationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de expiração não pode estar no passado.");
        }
    }
}