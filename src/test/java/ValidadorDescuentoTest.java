import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas Unitarias para Cobertura de Ramas.
 */
public class ValidadorDescuentoTest {

    private final ValidadorDescuento validador = new ValidadorDescuento();

    // Caso 1: Rama A (True)
    @Test
    public void testMontoInvalido_RetornaCero() {
        double resultado = validador.calcularDescuento(0.0, true, 10, true);
        assertEquals(0.0, resultado, 0.0001);
    }

    // Caso 2: Ramas A(False), B(True), B1(True), D(True)
    @Test
    public void testAfiliadoSenior_MontoAlto_AplicaDescuentoMaximo() {
        double resultado = validador.calcularDescuento(15000.0, true, 7, false);
        assertEquals(0.30, resultado, 0.0001);
    }

    // Caso 3: Ramas A(False), B(True), B2(False), D(False)
    @Test
    public void testAfiliadoJunior_MontoNormal_AplicaDescuentoBase() {
        double resultado = validador.calcularDescuento(5000.0, true, 3, false);
        assertEquals(0.15, resultado, 0.0001);
    }

    // Caso 4: Ramas A(False), B(False), C1(True), D(False)
    @Test
    public void testNoAfiliado_ConCupon_MontoNormal() {
        double resultado = validador.calcularDescuento(8000.0, false, 0, true);
        assertEquals(0.10, resultado, 0.0001);
    }

    // Caso 5: Ramas A(False), B(False), C2(False), D(False)
    @Test
    public void testNoAfiliado_SinCupon_MontoNormal_SinDescuento() {
        double resultado = validador.calcularDescuento(2000.0, false, 0, false);
        assertEquals(0.0, resultado, 0.0001);
    }
}
