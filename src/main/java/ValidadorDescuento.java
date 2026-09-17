/**
 * Componente con estructura condicional compleja para análisis de Cobertura de Ramas.
 */
public class ValidadorDescuento {

    public double calcularDescuento(double monto, boolean esAfiliado, int antiguedadAnos, boolean cuponValido) {
        // Rama A: monto invalido
        if (monto <= 0) {
            return 0.0;
        }

        double porcentaje = 0.0;

        // Rama B: Afiliado vs No Afiliado
        if (esAfiliado) {
            // Rama B1: Antiguedad mayor a 5 años
            if (antiguedadAnos > 5) {
                porcentaje = 0.25;
            } else {
                // Rama B2: Antiguedad <= 5 años
                porcentaje = 0.15;
            }
        } else if (cuponValido) {
            // Rama C1: No afiliado pero con cupon valido
            porcentaje = 0.10;
        }
        // Rama C2: No afiliado y sin cupon -> porcentaje queda en 0.0

        // Rama D: Bonificacion monto mayor
        if (monto > 10000.0) {
            porcentaje += 0.05;
        }

        return porcentaje;
    }
}
