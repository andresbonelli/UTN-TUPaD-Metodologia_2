/**
 * CODIGO REFACTORIZADO
 */
public class EvaluadorRiesgo {

    public enum ResultadoRiesgo {
        APROBADO_PREMIUM,
        APROBADO_ESTÁNDAR,
        APROBADO_CON_GARANTÍA,
        RECHAZADO_MONTO_INVÁLIDO,
        RECHAZADO_EDAD_OUT_OF_RANGE,
        RECHAZADO_SCORE_INSUFICIENTE,
        RECHAZADO_RIESGO_DEUDA
    }

    public ResultadoRiesgo evaluarSolicitudCredito(double monto, int edad, int scoreCrediticio, boolean tieneDeudas, boolean esPropietario) {
        if (monto <= 0) return ResultadoRiesgo.RECHAZADO_MONTO_INVÁLIDO;
        if (edad < 18 || edad > 65) return ResultadoRiesgo.RECHAZADO_EDAD_OUT_OF_RANGE;
        if (scoreCrediticio < 600) return ResultadoRiesgo.RECHAZADO_SCORE_INSUFICIENTE;

        if (scoreCrediticio > 700 && !tieneDeudas) {
            return esPropietario ? ResultadoRiesgo.APROBADO_PREMIUM : ResultadoRiesgo.APROBADO_ESTÁNDAR;
        }

        if (monto < 50000 || (esPropietario && !tieneDeudas)) {
            return ResultadoRiesgo.APROBADO_CON_GARANTÍA;
        }

        return ResultadoRiesgo.RECHAZADO_RIESGO_DEUDA;
    }
}
