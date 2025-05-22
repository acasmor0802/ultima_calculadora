package es.prog2425.calclog.app

import es.prog2425.calclog.model.Operador
import es.prog2425.calclog.service.ServicioCalc
import es.prog2425.calclog.service.IServicioLog
import es.prog2425.calclog.ui.IEntradaSalida

class Controlador(
    private val ui: IEntradaSalida,
    private val calculadora: ServicioCalc,
    private val gestorLog: IServicioLog
) {
    fun iniciar(args: Array<String>) {
        mostrarInfoLog()

        if (args.size == 4) ejecutarCalculoConArgumentos(args)

        ui.pausar("Pulsa ENTER para iniciar la calculadora...")
        ui.limpiarPantalla()

        bucleCalculosUsuario()
    }

    private fun mostrarInfoLog() {
        val lineas = gestorLog.getInfoUltimoLog()
        if (lineas.isEmpty()) {
            ui.mostrar("No existe información de un log previo!")
        } else {
            ui.mostrar("Contenido de los últimos cálculos:")
            lineas.forEach { ui.mostrar(it) }
        }
    }

    private fun ejecutarCalculoConArgumentos(args: Array<String>) {
        val numero1 = args[1].replace(',', '.').toDoubleOrNull()
        val operador = Operador.getOperador(args[2].firstOrNull())
        val numero2 = args[3].replace(',', '.').toDoubleOrNull()

        if (numero1 == null || operador == null || numero2 == null) {
            val msg = "Error en los argumentos: operación no válida."
            ui.mostrarError(msg)
            gestorLog.registrarEntradaLog(msg)
        } else {
            realizarCalculo(numero1, operador, numero2)
        }
    }

    private fun bucleCalculosUsuario() {
        do {
            try {
                val numero1 = ui.pedirDouble("Introduce el primer número: ") ?: throw InfoCalcException("El primer número no es válido!")
                val simbolo = ui.pedirInfo("Introduce el operador (+, -, x, /): ").firstOrNull()
                val operador = Operador.getOperador(simbolo) ?: throw InfoCalcException("El operador no es válido!")
                val numero2 = ui.pedirDouble("Introduce el segundo número: ") ?: throw InfoCalcException("El segundo número no es válido!")

                realizarCalculo(numero1, operador, numero2)
            } catch (e: InfoCalcException) {
                val mensaje = e.message ?: "Se ha producido un error!"
                ui.mostrarError(mensaje)
                gestorLog.registrarEntradaLog(mensaje)
            }
        } while (ui.preguntar())

        ui.limpiarPantalla()
    }

    private fun realizarCalculo(num1: Double, operador: Operador, num2: Double) {
        val calculo = calculadora.realizarCalculo(num1, operador, num2)
        ui.mostrar(calculo.toString())
        gestorLog.registrarEntradaLog(calculo)
    }
}