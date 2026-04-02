package br.com.desafiotecnicooutforce.coupon_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Contrato genérico base para os repositories da aplicação.
 *
 * T representa a entidade.
 * ID representa o tipo do identificador da entidade.
 */
@NoRepositoryBean
public interface IGenericRepository<T, ID> extends JpaRepository<T, ID> {
}