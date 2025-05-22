package org.example

import data.dao.CalculoDao
import data.dao.ErrorDao
import es.prog2425.calclog.app.Controlador
import data.db.AppDatabase
import es.prog2425.calclog.service.ServicioCalc
import es.prog2425.calclog.ui.Consola
import org.example.service.ServicioLogH2

fun main(args: Array<String>) {

    // Instancia UI y lógica de cálculo
    val consola = Consola()
    val servicioCalc = ServicioCalc()

    val dataSource = try {
        AppDatabase.getDs()
    } catch (e: IllegalStateException) {
        consola.mostrarError("Problemas al crear el DataSource: ${e.message}")
        return // Se acaba el programa porque no puedo interactuar con la base de datos
    }

    // Inicializa el DataSource de H2
    val calculoDao = CalculoDao()
    val errorDao = ErrorDao()

    // Inyecta los DAOs en servicio de log sobre H2
    val servicioLog = ServicioLogH2(calculoDao, errorDao)


    // Monta el controlador con tus dependencias
    val controlador = Controlador(consola, servicioCalc, servicioLog)

    // Arranca la aplicación
    controlador.iniciar(args)
}