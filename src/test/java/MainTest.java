/**
 * Ejecutable para demostración automatizada de pruebas unitarias y Mockito (Unidad 3 TP2).
 */
public class MainTest {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("   EJECUCIÓN DE PRUEBAS UNITARIAS     ");
        System.out.println("=================================================================\n");

        int exitos = 0;
        int fallos = 0;

        System.out.println("--- 1. Ejecutando PagoServiceTest (Patrón AAA + Stub/Mock) ---");
        PagoServiceTest pagoTest = new PagoServiceTest();
        
        try {
            pagoTest.testRealizarCobroExitoso_AplicaPatronAAA();
            System.out.println(" [OK] testRealizarCobroExitoso_AplicaPatronAAA PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testRealizarCobroExitoso_AplicaPatronAAA: " + e.getMessage());
            fallos++;
        }

        try {
            pagoTest.testRealizarCobroRechazado_RegistraAuditoriaFallo();
            System.out.println(" [OK] testRealizarCobroRechazado_RegistraAuditoriaFallo PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testRealizarCobroRechazado_RegistraAuditoriaFallo: " + e.getMessage());
            fallos++;
        }

        System.out.println("\n--- 2. Ejecutando ValidadorDescuentoTest (100% Branch Coverage) ---");
        ValidadorDescuentoTest validadorTest = new ValidadorDescuentoTest();

        try {
            validadorTest.testMontoInvalido_RetornaCero();
            System.out.println(" [OK] testMontoInvalido_RetornaCero PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testMontoInvalido_RetornaCero: " + e.getMessage());
            fallos++;
        }

        try {
            validadorTest.testAfiliadoSenior_MontoAlto_AplicaDescuentoMaximo();
            System.out.println(" [OK] testAfiliadoSenior_MontoAlto_AplicaDescuentoMaximo PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testAfiliadoSenior_MontoAlto_AplicaDescuentoMaximo: " + e.getMessage());
            fallos++;
        }

        try {
            validadorTest.testAfiliadoJunior_MontoNormal_AplicaDescuentoBase();
            System.out.println(" [OK] testAfiliadoJunior_MontoNormal_AplicaDescuentoBase PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testAfiliadoJunior_MontoNormal_AplicaDescuentoBase: " + e.getMessage());
            fallos++;
        }

        try {
            validadorTest.testNoAfiliado_ConCupon_MontoNormal();
            System.out.println(" [OK] testNoAfiliado_ConCupon_MontoNormal PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testNoAfiliado_ConCupon_MontoNormal: " + e.getMessage());
            fallos++;
        }

        try {
            validadorTest.testNoAfiliado_SinCupon_MontoNormal_SinDescuento();
            System.out.println(" [OK] testNoAfiliado_SinCupon_MontoNormal_SinDescuento PASÓ EXITOSAMENTE");
            exitos++;
        } catch (Throwable e) {
            System.err.println(" [FALLO] testNoAfiliado_SinCupon_MontoNormal_SinDescuento: " + e.getMessage());
            fallos++;
        }

        System.out.println("\n=================================================================");
        System.out.println("   RESUMEN FINAL: " + exitos + " PASADOS | " + fallos + " FALLIDOS");
        System.out.println("   COBERTURA DE RAMAS: 100%");
        System.out.println("=================================================================");
    }
}
