// src/main/kotlin/es/prog2425/calclog/Main.kt
package es.prog2425.calclog

import es.prog2425.calclog.app.Controlador
import es.prog2425.calclog.data.dao.CalculoDao
import es.prog2425.calclog.data.dao.ErrorDao
import es.prog2425.calclog.data.db.AppDatabase
import es.prog2425.calclog.service.ServicioCalc
import es.prog2425.calclog.service.ServicioLogH2
import es.prog2425.calclog.ui.Consola

fun main(args: Array<String>) {
    // Inicializa el DataSource de H2
    val conn = AppDatabase.connection
    val calculoDao = CalculoDao(conn)
    val errorDao = ErrorDao(conn)

    // Inyecta los DAOs en servicio de log sobre H2
    val servicioLog = ServicioLogH2(calculoDao, errorDao)

    // Instancia UI y lógica de cálculo
    val consola = Consola()
    val servicioCalc = ServicioCalc()

    // Monta el controlador con tus dependencias
    val controlador = Controlador(consola, servicioCalc, servicioLog)

    // Arranca la aplicación
    controlador.iniciar(args)
}
