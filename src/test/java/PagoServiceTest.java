import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas Unitarias aplicando Patrón AAA y  Dobles de Prueba Stub y Mock.
 */
public class PagoServiceTest {

    @Test
    public void testRealizarCobroExitoso_AplicaPatronAAA() {
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
        assertTrue(resultado, "El cobro debería retornar true cuando la transacción es APROBADA");
        verify(pasarelaMock, times(1)).procesarPago(tarjetaId, monto);
        verify(pasarelaMock, times(1)).registrarAuditoria("TX-8899", "PAGO_EXITOSO");
    }

    @Test
    public void testRealizarCobroRechazado_RegistraAuditoriaFallo() {
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
        assertFalse(resultado, "El cobro debería retornar false cuando la transacción es RECHAZADA");
        verify(pasarelaMock).procesarPago(tarjetaId, monto);
        verify(pasarelaMock).registrarAuditoria("TX-9900", "PAGO_RECHAZADO");
    }
}
