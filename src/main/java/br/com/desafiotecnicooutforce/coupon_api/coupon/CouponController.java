package br.com.desafiotecnicooutforce.coupon_api.coupon;

import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponRequestDTO;
import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller responsável pelos endpoints do cupom.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * Cria um novo cupom.
     */
    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@Valid @RequestBody CouponRequestDTO requestDTO) {
        CouponResponseDTO response = couponService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retorna todos os cupons ativos.
     */
    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> findAll() {
        List<CouponResponseDTO> response = couponService.findAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Busca um cupom ativo pelo id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> findById(@PathVariable UUID id) {
        CouponResponseDTO response = couponService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Faz o soft delete do cupom.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        couponService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}