/**
 * Interfaz API Externa (Componente que se mockea o simula en pruebas).
 */
public interface PasarelaPagos {
    TransaccionPago procesarPago(String tarjetaId, double monto);
    String obtenerEstadoTransaccion(String idTransaccion);
    void registrarAuditoria(String idTransaccion, String evento);
}
