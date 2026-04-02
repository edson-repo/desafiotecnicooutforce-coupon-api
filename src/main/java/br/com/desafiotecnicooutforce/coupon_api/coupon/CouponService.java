package br.com.desafiotecnicooutforce.coupon_api.coupon;

import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponMapper;
import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponRequestDTO;
import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponResponseDTO;

import br.com.desafiotecnicooutforce.coupon_api.exception.CouponNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Camada de serviço do cupom.
 *
 * Aqui ficam os fluxos da aplicação, enquanto as regras principais
 * de negócio continuam concentradas na entidade.
 */
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponService(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    /**
     * Cria e salva um novo cupom.
     */
    public CouponResponseDTO create(CouponRequestDTO requestDTO) {
        CouponEntity coupon = couponMapper.toEntity(requestDTO);
        CouponEntity savedCoupon = couponRepository.save(coupon);

        return couponMapper.toResponseDTO(savedCoupon);
    }

    /**
     * Busca um cupom ativo pelo id.
     */
    public CouponResponseDTO findById(UUID id) {
        CouponEntity coupon = couponRepository.findActiveById(id)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado."));

        return couponMapper.toResponseDTO(coupon);
    }

    /**
     * Faz o soft delete do cupom.
     */
    public void deleteById(UUID id) {
        CouponEntity coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado."));

        coupon.softDelete();
        couponRepository.save(coupon);
    }
}