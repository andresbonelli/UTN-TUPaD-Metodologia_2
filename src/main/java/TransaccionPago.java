/**
 * Representa una transacción de pago.
 */
public class TransaccionPago {
    private final String idTransaccion;
    private final double monto;
    private final String estado;

    public TransaccionPago(String idTransaccion, double monto, String estado) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.estado = estado;
    }

    public String getIdTransaccion() { return idTransaccion; }
    public double getMonto() { return monto; }
    public String getEstado() { return estado; }

    public boolean esExitosa() {
        return "APROBADO".equalsIgnoreCase(estado);
    }
}
