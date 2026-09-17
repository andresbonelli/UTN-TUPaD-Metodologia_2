/**
 * CÓDIGO PROBLEMATICO (ANTES DE LA AUDITORÍA ESTÁTICA)
 * Problemas identificados por SonarQube:
 * 1. Complejidad Ciclomática Elevada (v=14, supera el máximo recomendado de 10).
 * 2. Bloques de código duplicados.
 * 3. Dependencia desactualizada con vulnerabilidad CVE conocida (CVE-2023-5072).
 */
public class EvaluadorRiesgo {

    public String evaluarSolicitudCredito(double monto, int edad, int scoreCrediticio, boolean tieneDeudas, boolean esPropietario) {
        if (monto > 0) {
            if (edad >= 18) {
                if (edad <= 65) {
                    if (scoreCrediticio > 700) {
                        if (!tieneDeudas) {
                            if (esPropietario) {
                                return "APROBADO_PREMIUM";
                            } else {
                                return "APROBADO_ESTÁNDAR";
                            }
                        } else {
                            if (monto < 50000) {
                                return "APROBADO_CON_GARANTÍA";
                            } else {
                                return "RECHAZADO_RIESGO_DEUDA";
                            }
                        }
                    } else if (scoreCrediticio >= 600) {
                        if (!tieneDeudas && esPropietario) {
                            return "APROBADO_CON_GARANTÍA";
                        } else {
                            return "RECHAZADO_SCORE_BAJO";
                        }
                    } else {
                        return "RECHAZADO_SCORE_INSUFICIENTE";
                    }
                } else {
                    return "RECHAZADO_EDAD_MÁXIMA";
                }
            } else {
                return "RECHAZADO_MENOR_EDAD";
            }
        } else {
            return "RECHAZADO_MONTO_INVÁLIDO";
        }
    }
}
