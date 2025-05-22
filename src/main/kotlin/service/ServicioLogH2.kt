package es.prog2425.calclog.service

import es.prog2425.calclog.data.dao.CalculoDao
import es.prog2425.calclog.data.dao.ErrorDao
import es.prog2425.calclog.model.Calculo

/**
 * Implementación de IServicioLog usando base de datos H2.
 */
class ServicioLogH2(
    private val calculoDao: CalculoDao,
    private val errorDao: ErrorDao
) : IServicioLog {

    /**
     * Registra un mensaje de error en la tabla de errores.
     */
    override fun registrarEntradaLog(mensaje: String) {
        errorDao.insertarError(mensaje)
    }

    /**
     * Registra un cálculo en la tabla de cálculos.
     */
    override fun registrarEntradaLog(calculo: Calculo) {
        calculoDao.insertarCalculo(
            calculo.numero1,
            calculo.operador.simboloUi.toString(),
            calculo.numero2,
            calculo.resultado
        )
    }

    /**
     * Devuelve todas las entradas de cálculos y errores.
     */
    override fun getInfoUltimoLog(): List<String> {
        val calcLogs = calculoDao.obtenerTodos()
        val errorLogs = errorDao.obtenerErrores()
        // Combina resultados.
        return calcLogs + errorLogs
    }
}