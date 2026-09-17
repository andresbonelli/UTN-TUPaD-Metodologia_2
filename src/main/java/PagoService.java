import java.util.Objects;

/**
 * Servicio que gestiona cobros interactuando con API externa.
 */
public class PagoService {

    private final PasarelaPagos pasarelaPagos;

    public PagoService(PasarelaPagos pasarelaPagos) {
        this.pasarelaPagos = Objects.requireNonNull(pasarelaPagos, "La pasarela de pagos no puede ser nula");
    }

    public boolean realizarCobro(String tarjetaId, double monto) {
        if (tarjetaId == null || tarjetaId.isEmpty() || monto <= 0) {
            return false;
        }

        TransaccionPago transaccion = pasarelaPagos.procesarPago(tarjetaId, monto);

        if (transaccion != null && transaccion.esExitosa()) {
            pasarelaPagos.registrarAuditoria(transaccion.getIdTransaccion(), "PAGO_EXITOSO");
            return true;
        } else {
            if (transaccion != null) {
                pasarelaPagos.registrarAuditoria(transaccion.getIdTransaccion(), "PAGO_RECHAZADO");
            }
            return false;
        }
    }
}
