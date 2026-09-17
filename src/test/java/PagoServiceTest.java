import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Suite de Pruebas Unitarias para PagoService.
 * Diseñada para alcanzar el 100% de Cobertura de Ramas (Branch Coverage).
 */
public class PagoServiceTest {

    @Test
    @DisplayName("Rama 0a: Constructor con pasarela nula debe lanzar NullPointerException")
    public void testConstructor_PasarelaNula_LanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> new PagoService(null));
    }

    @Test
    @DisplayName("Rama 1a: Tarjeta nula debe retornar false sin llamar a la pasarela")
    public void testRealizarCobro_TarjetaNula_RetornaFalse() {
        PasarelaPagos pasarelaMock = mock(PasarelaPagos.class);
        PagoService service = new PagoService(pasarelaMock);

        boolean resultado = service.realizarCobro(null, 100.0);

        assertFalse(resultado);
        verifyNoInteractions(pasarelaMock);
    }

    @Test
    @DisplayName("Rama 1b: Tarjeta vacía debe retornar false sin llamar a la pasarela")
    public void testRealizarCobro_TarjetaVacia_RetornaFalse() {
        PasarelaPagos pasarelaMock = mock(PasarelaPagos.class);
        PagoService service = new PagoService(pasarelaMock);

        boolean resultado = service.realizarCobro("", 100.0);

        assertFalse(resultado);
        verifyNoInteractions(pasarelaMock);
    }

    @Test
    @DisplayName("Rama 1c: Monto menor o igual a cero (<= 0) debe retornar false sin llamar a la pasarela")
    public void testRealizarCobro_MontoInvalido_RetornaFalse() {
        PasarelaPagos pasarelaMock = mock(PasarelaPagos.class);
        PagoService service = new PagoService(pasarelaMock);

        boolean resultado = service.realizarCobro("4500-1234", 0.0);

        assertFalse(resultado);
        verifyNoInteractions(pasarelaMock);
    }

    @Test
    @DisplayName("Rama 2a: Transacción exitosa (APROBADO) debe retornar true y registrar auditoría de éxito")
    public void testRealizarCobro_TransaccionExitosa_RetornaTrueYRegistraAuditoriaExito() {
        // ARRANGE
        String tarjetaId = "4500-1234-5678-9999";
        double monto = 2500.0;
        TransaccionPago respuestaStub = new TransaccionPago("TX-8899", monto, "APROBADO");

        PasarelaPagos pasarelaMock = mock(PasarelaPagos.class);
        when(pasarelaMock.procesarPago(tarjetaId, monto)).thenReturn(respuestaStub);

        PagoService service = new PagoService(pasarelaMock);

        // ACT
        boolean resultado = service.realizarCobro(tarjetaId, monto);

        // ASSERT
        assertTrue(resultado);
        verify(pasarelaMock, times(1)).procesarPago(tarjetaId, monto);
        verify(pasarelaMock, times(1)).registrarAuditoria("TX-8899", "PAGO_EXITOSO");
    }

    @Test
    @DisplayName("Rama 2b + 3a: Transacción rechazada debe retornar false y registrar auditoría de fallo")
    public void testRealizarCobro_TransaccionRechazada_RetornaFalseYRegistraAuditoriaFallo() {
        // ARRANGE
        String tarjetaId = "4500-0000-0000-0000";
        double monto = 5000.0;
        TransaccionPago respuestaStub = new TransaccionPago("TX-9900", monto, "RECHAZADO");

        PasarelaPagos pasarelaMock = mock(PasarelaPagos.class);
        when(pasarelaMock.procesarPago(tarjetaId, monto)).thenReturn(respuestaStub);

        PagoService service = new PagoService(pasarelaMock);

        // ACT
        boolean resultado = service.realizarCobro(tarjetaId, monto);

        // ASSERT
        assertFalse(resultado);
        verify(pasarelaMock, times(1)).procesarPago(tarjetaId, monto);
        verify(pasarelaMock, times(1)).registrarAuditoria("TX-9900", "PAGO_RECHAZADO");
    }

    @Test
    @DisplayName("Rama 2b + 3b: Respuesta nula de la pasarela debe retornar false sin registrar auditoría")
    public void testRealizarCobro_TransaccionNula_RetornaFalseSinAuditoria() {
        // ARRANGE
        String tarjetaId = "4500-1234-5678-9999";
        double monto = 1000.0;

        PasarelaPagos pasarelaMock = mock(PasarelaPagos.class);
        when(pasarelaMock.procesarPago(tarjetaId, monto)).thenReturn(null);

        PagoService service = new PagoService(pasarelaMock);

        // ACT
        boolean resultado = service.realizarCobro(tarjetaId, monto);

        // ASSERT
        assertFalse(resultado);
        verify(pasarelaMock, times(1)).procesarPago(tarjetaId, monto);
        verify(pasarelaMock, never()).registrarAuditoria(anyString(), anyString());
    }
}
