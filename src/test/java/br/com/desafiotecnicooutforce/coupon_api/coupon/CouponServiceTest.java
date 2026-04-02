package br.com.desafiotecnicooutforce.coupon_api.coupon;

import br.com.desafiotecnicooutforce.coupon_api.coupon.CouponEntity;
import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponMapper;
import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponRequestDTO;
import br.com.desafiotecnicooutforce.coupon_api.coupon.dto.CouponResponseDTO;

import br.com.desafiotecnicooutforce.coupon_api.exception.CouponNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponMapper couponMapper;

    @InjectMocks
    private CouponService couponService;

    @Test
    @DisplayName("Deve criar e salvar um cupom com sucesso")
    void shouldCreateCouponSuccessfully() {
        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "ABC-123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                true
        );

        CouponEntity entity = CouponEntity.create(
                "ABC-123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                true
        );

        CouponResponseDTO responseDTO = new CouponResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCode(entity.getCode());

        when(couponMapper.toEntity(requestDTO)).thenReturn(entity);
        when(couponRepository.save(entity)).thenReturn(entity);
        when(couponMapper.toResponseDTO(entity)).thenReturn(responseDTO);

        CouponResponseDTO response = couponService.create(requestDTO);

        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        verify(couponMapper).toEntity(requestDTO);
        verify(couponRepository).save(entity);
        verify(couponMapper).toResponseDTO(entity);
    }

    @Test
    @DisplayName("Deve buscar cupom ativo por id")
    void shouldFindCouponById() {
        UUID id = UUID.randomUUID();

        CouponEntity entity = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                false
        );
        entity.setId(id);

        CouponResponseDTO responseDTO = new CouponResponseDTO();
        responseDTO.setId(id);
        responseDTO.setCode("ABC123");

        when(couponRepository.findById(id)).thenReturn(Optional.of(entity));
        when(couponMapper.toResponseDTO(entity)).thenReturn(responseDTO);

        CouponResponseDTO response = couponService.findById(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(couponRepository).findById(id);
        verify(couponMapper).toResponseDTO(entity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cupom inexistente")
    void shouldThrowExceptionWhenCouponNotFound() {
        UUID id = UUID.randomUUID();

        when(couponRepository.findById(id)).thenReturn(Optional.empty());

        CouponNotFoundException exception = assertThrows(
                CouponNotFoundException.class,
                () -> couponService.findById(id)
        );

        assertEquals("Cupom não encontrado.", exception.getMessage());
        verify(couponRepository).findById(id);
        verify(couponMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("Deve realizar soft delete com sucesso")
    void shouldDeleteCouponSuccessfully() {
        UUID id = UUID.randomUUID();

        CouponEntity entity = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                false
        );
        entity.setId(id);

        when(couponRepository.findById(id)).thenReturn(Optional.of(entity));
        when(couponRepository.save(entity)).thenReturn(entity);

        couponService.deleteById(id);

        assertTrue(entity.isDeleted());
        verify(couponRepository).findById(id);
        verify(couponRepository).save(entity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cupom inexistente")
    void shouldThrowExceptionWhenDeletingNonExistingCoupon() {
        UUID id = UUID.randomUUID();

        when(couponRepository.findById(id)).thenReturn(Optional.empty());

        CouponNotFoundException exception = assertThrows(
                CouponNotFoundException.class,
                () -> couponService.deleteById(id)
        );

        assertEquals("Cupom não encontrado.", exception.getMessage());
        verify(couponRepository).findById(id);
        verify(couponRepository, never()).save(any());
    }
}