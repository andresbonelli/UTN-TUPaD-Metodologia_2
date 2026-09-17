import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Suite de Pruebas Unitarias para EvaluadorRiesgo.
 * Diseñada para alcanzar el 100% de Cobertura de Ramas (Branch Coverage)
 * cubriendo exhaustivamente todas las combinaciones lógicas (short-circuit).
 */
public class EvaluadorRiesgoTest {

    private final EvaluadorRiesgo evaluador = new EvaluadorRiesgo();

    // -------------------------------------------------------------------------
    // 1. EVALUACIÓN DE GUARD CLAUSES (Filtros iniciales)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Rama 1: Monto inválido (<= 0) debe retornar RECHAZADO_MONTO_INVÁLIDO")
    public void testMontoInvalido_RetornaRechazadoMontoInvalido() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                0.0, 30, 750, false, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_MONTO_INVÁLIDO, resultado);
    }

    @Test
    @DisplayName("Rama 2a: Edad menor a 18 (< 18) debe retornar RECHAZADO_EDAD_OUT_OF_RANGE")
    public void testEdadMenorDe18_RetornaRechazadoEdadOutOfRange() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                10000.0, 17, 750, false, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_EDAD_OUT_OF_RANGE, resultado);
    }

    @Test
    @DisplayName("Rama 2b: Edad mayor a 65 (> 65) debe retornar RECHAZADO_EDAD_OUT_OF_RANGE")
    public void testEdadMayorDe65_RetornaRechazadoEdadOutOfRange() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                10000.0, 66, 750, false, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_EDAD_OUT_OF_RANGE, resultado);
    }

    @Test
    @DisplayName("Rama 3: Score crediticio menor a 600 (< 600) debe retornar RECHAZADO_SCORE_INSUFICIENTE")
    public void testScoreBajo_RetornaRechazadoScoreInsuficiente() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                10000.0, 30, 599, false, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_SCORE_INSUFICIENTE, resultado);
    }

    // -------------------------------------------------------------------------
    // 2. EVALUACIÓN DE RAMA DE SCORE ALTO (score > 700 && !tieneDeudas)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Rama 4a (TRUE & TRUE & TRUE): Score > 700, SIN deudas y Propietario -> APROBADO_PREMIUM")
    public void testScoreAltoSinDeudasPropietario_RetornaAprobadoPremium() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                10000.0, 30, 750, false, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.APROBADO_PREMIUM, resultado);
    }

    @Test
    @DisplayName("Rama 4b (TRUE & TRUE & FALSE): Score > 700, SIN deudas y NO Propietario -> APROBADO_ESTÁNDAR")
    public void testScoreAltoSinDeudasNoPropietario_RetornaAprobadoEstandar() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                10000.0, 30, 750, false, false
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.APROBADO_ESTÁNDAR, resultado);
    }

    @Test
    @DisplayName("Rama 4c (TRUE & FALSE): Score > 700 pero CON DEUDAS (!tieneDeudas = false), monto < 50k -> APROBADO_CON_GARANTÍA")
    public void testScoreAltoConDeudasMontoMenor50k_RetornaAprobadoConGarantia() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                20000.0, 30, 750, true, false
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.APROBADO_CON_GARANTÍA, resultado);
    }

    @Test
    @DisplayName("Rama 4d (TRUE & FALSE): Score > 700 pero CON DEUDAS (!tieneDeudas = false), monto >= 50k -> RECHAZADO_RIESGO_DEUDA")
    public void testScoreAltoConDeudasMontoMayor50k_RetornaRechazadoRiesgoDeuda() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                60000.0, 30, 750, true, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_RIESGO_DEUDA, resultado);
    }

    // -------------------------------------------------------------------------
    // 3. EVALUACIÓN DE RAMA DE GARANTÍA Y RIESGO DE DEUDA (score <= 700)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Rama 5a: Score regular (600-700), monto < 50.000 -> APROBADO_CON_GARANTÍA")
    public void testScoreMedioMontoMenor50k_RetornaAprobadoConGarantia() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                49999.0, 30, 650, true, false
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.APROBADO_CON_GARANTÍA, resultado);
    }

    @Test
    @DisplayName("Rama 5b: Score regular (600-700), monto >= 50.000, propietario y SIN deudas -> APROBADO_CON_GARANTÍA")
    public void testScoreMedioMontoMayor50kPropietarioSinDeudas_RetornaAprobadoConGarantia() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                60000.0, 30, 650, false, true
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.APROBADO_CON_GARANTÍA, resultado);
    }

    @Test
    @DisplayName("Rama 5c: Score regular (600-700), monto >= 50.000, CON deudas -> RECHAZADO_RIESGO_DEUDA")
    public void testScoreMedioMontoMayor50kConDeudas_RetornaRechazadoRiesgoDeuda() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                60000.0, 30, 650, true, false
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_RIESGO_DEUDA, resultado);
    }

    @Test
    @DisplayName("Rama 5d: Score regular (600-700), monto >= 50.000, SIN deudas pero NO propietario -> RECHAZADO_RIESGO_DEUDA")
    public void testScoreMedioMontoMayor50kSinDeudasNoPropietario_RetornaRechazadoRiesgoDeuda() {
        EvaluadorRiesgo.ResultadoRiesgo resultado = evaluador.evaluarSolicitudCredito(
                60000.0, 30, 650, false, false
        );
        assertEquals(EvaluadorRiesgo.ResultadoRiesgo.RECHAZADO_RIESGO_DEUDA, resultado);
    }
}
