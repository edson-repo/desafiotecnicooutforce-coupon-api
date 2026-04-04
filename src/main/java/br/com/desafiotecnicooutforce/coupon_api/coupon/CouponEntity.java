package br.com.desafiotecnicooutforce.coupon_api.coupon;

import br.com.desafiotecnicooutforce.coupon_api.exception.BusinessException;
import br.com.desafiotecnicooutforce.coupon_api.exception.CouponAlreadyDeletedException;
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
 * As regras principais ficam aqui para manter a lógica
 * mais próxima do domínio.
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
    private String codigo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorDesconto;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private Boolean publicado;

    @Column(nullable = false)
    private Boolean utilizado;

    @Column
    private LocalDateTime deletedAt;

    /**
     * Cria um novo cupom já aplicando as regras de negócio da criação.
     */
    public static CouponEntity create(
            String codigo,
            String descricao,
            BigDecimal valorDesconto,
            LocalDateTime dataExpiracao,
            Boolean publicado
    ) {
        String sanitizedCode = sanitizacaoCodigo(codigo);
        validarCodigo(sanitizedCode);
        validateDescription(descricao);
        validarValorDesconto(valorDesconto);
        validarDataExpeiracao(dataExpiracao);

        CouponEntity coupon = new CouponEntity();
        coupon.setId(UUID.randomUUID());
        coupon.setCodigo(sanitizedCode);
        coupon.setDescricao(descricao.trim());
        coupon.setValorDesconto(valorDesconto);
        coupon.setDataExpiracao(dataExpiracao);
        coupon.setStatus(CouponStatus.ACTIVE);
        coupon.setPublicado(publicado != null ? publicado : Boolean.FALSE);
        coupon.setUtilizado(Boolean.FALSE);
        coupon.setDeletedAt(null);

        return coupon;
    }

    /**
     * Aplica o soft delete no cupom.
     * Se ele já estiver deletado, a regra bloqueia uma nova exclusão.
     */
    public void softDelete() {
        if (isDeleted()) {
            throw new CouponAlreadyDeletedException("O cupom já foi deletado.");
        }

        this.status = CouponStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    /**
     * Indica se o cupom já foi deletado logicamente.
     */
    public boolean isDeleted() {
        return this.deletedAt != null || CouponStatus.DELETED.equals(this.status);
    }

    /**
     * Remove qualquer caractere que não seja letra ou número.
     */
    private static String sanitizacaoCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        return codigo.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * O código final precisa ter exatamente 6 caracteres.
     */
    private static void validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new BusinessException("O código do cupom é obrigatório.");
        }

        if (codigo.length() != 6) {
            throw new BusinessException("O código do cupom deve ter exatamente 6 caracteres alfanuméricos.");
        }
    }

    /**
     * A descrição é obrigatória.
     */
    private static void validateDescription(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new BusinessException("A descrição do cupom é obrigatória.");
        }
    }

    /**
     * O desconto mínimo permitido é 0.5.
     */
    private static void validarValorDesconto(BigDecimal valorDesconto) {
        if (valorDesconto == null) {
            throw new BusinessException("O valor de desconto é obrigatório.");
        }

        if (valorDesconto.compareTo(new BigDecimal("0.5")) < 0) {
            throw new BusinessException("O valor mínimo de desconto é 0.5.");
        }
    }

    /**
     * Não permite cupom com data de expiração no passado.
     */
    private static void validarDataExpeiracao(LocalDateTime dataExpiracao) {
        if (dataExpiracao == null) {
            throw new BusinessException("A data de expiração é obrigatória.");
        }

        if (dataExpiracao.isBefore(LocalDateTime.now())) {
            throw new BusinessException("A data de expiração não pode estar no passado.");
        }
    }
}