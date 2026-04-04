package br.com.desafiotecnicooutforce.coupon_api.coupon;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void deveCriarESalvarCupomComSucesso() {
        CouponRequestDTO dtoRequisicao = new CouponRequestDTO(
                "ABC-123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                true
        );

        CouponEntity entidadeCupom = CouponEntity.create(
                "ABC-123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                true
        );

        CouponResponseDTO dtoResposta = new CouponResponseDTO();
        dtoResposta.setId(entidadeCupom.getId());
        dtoResposta.setCodigo(entidadeCupom.getCodigo());

        when(couponMapper.toEntity(dtoRequisicao)).thenReturn(entidadeCupom);
        when(couponRepository.save(entidadeCupom)).thenReturn(entidadeCupom);
        when(couponMapper.toResponseDTO(entidadeCupom)).thenReturn(dtoResposta);

        CouponResponseDTO resposta = couponService.create(dtoRequisicao);

        assertNotNull(resposta);
        assertEquals(entidadeCupom.getId(), resposta.getId());
        verify(couponMapper).toEntity(dtoRequisicao);
        verify(couponRepository).save(entidadeCupom);
        verify(couponMapper).toResponseDTO(entidadeCupom);
    }

    @Test
    @DisplayName("Deve buscar cupom ativo por id")
    void deveBuscarCupomPorId() {
        UUID idCupom = UUID.randomUUID();

        CouponEntity entidadeCupom = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                false
        );
        entidadeCupom.setId(idCupom);

        CouponResponseDTO dtoResposta = new CouponResponseDTO();
        dtoResposta.setId(idCupom);
        dtoResposta.setCodigo("ABC123");

        when(couponRepository.findById(idCupom)).thenReturn(Optional.of(entidadeCupom));
        when(couponMapper.toResponseDTO(entidadeCupom)).thenReturn(dtoResposta);

        CouponResponseDTO resposta = couponService.findById(idCupom);

        assertNotNull(resposta);
        assertEquals(idCupom, resposta.getId());
        verify(couponRepository).findById(idCupom);
        verify(couponMapper).toResponseDTO(entidadeCupom);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cupom inexistente")
    void deveLancarExcecaoAoBuscarCupomInexistente() {
        UUID idCupom = UUID.randomUUID();

        when(couponRepository.findById(idCupom)).thenReturn(Optional.empty());

        CouponNotFoundException excecao = assertThrows(
                CouponNotFoundException.class,
                () -> couponService.findById(idCupom)
        );

        assertEquals("Cupom não encontrado.", excecao.getMessage());
        verify(couponRepository).findById(idCupom);
        verify(couponMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("Deve realizar soft delete com sucesso")
    void deveRealizarSoftDeleteComSucesso() {
        UUID idCupom = UUID.randomUUID();

        CouponEntity entidadeCupom = CouponEntity.create(
                "ABC123",
                "Cupom de teste",
                new BigDecimal("10.00"),
                LocalDateTime.now().plusDays(1),
                false
        );
        entidadeCupom.setId(idCupom);

        when(couponRepository.findById(idCupom)).thenReturn(Optional.of(entidadeCupom));
        when(couponRepository.save(entidadeCupom)).thenReturn(entidadeCupom);

        couponService.deleteById(idCupom);

        assertTrue(entidadeCupom.isDeleted());
        verify(couponRepository).findById(idCupom);
        verify(couponRepository).save(entidadeCupom);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cupom inexistente")
    void deveLancarExcecaoAoDeletarCupomInexistente() {
        UUID idCupom = UUID.randomUUID();

        when(couponRepository.findById(idCupom)).thenReturn(Optional.empty());

        CouponNotFoundException excecao = assertThrows(
                CouponNotFoundException.class,
                () -> couponService.deleteById(idCupom)
        );

        assertEquals("Cupom não encontrado.", excecao.getMessage());
        verify(couponRepository).findById(idCupom);
        verify(couponRepository, never()).save(any());
    }
}