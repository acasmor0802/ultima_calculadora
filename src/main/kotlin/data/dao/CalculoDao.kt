package es.prog2425.calclog.data.dao

class CalculoDao(private val conn: java.sql.Connection) {

    fun insertarCalculo(numero1: Double, operador: String, numero2: Double, resultado: Double) {
        val sql = "INSERT INTO calculos (numero1, operador, numero2, resultado) VALUES (?, ?, ?, ?)"
        conn.prepareStatement(sql).use {
            it.setDouble(1, numero1)
            it.setString(2, operador)
            it.setDouble(3, numero2)
            it.setDouble(4, resultado)
            it.executeUpdate()
        }
    }

    fun obtenerTodos(): List<String> {
        val sql = "SELECT numero1, operador, numero2, resultado, timestamp FROM calculos ORDER BY timestamp DESC"
        conn.prepareStatement(sql).use { stmt ->
            val rs = stmt.executeQuery()
            val resultados = mutableListOf<String>()
            while (rs.next()) {
                resultados.add(
                    "%.2f %s %.2f = %.2f (%s)".format(
                        rs.getDouble("numero1"),
                        rs.getString("operador"),
                        rs.getDouble("numero2"),
                        rs.getDouble("resultado"),
                        rs.getTimestamp("timestamp").toString()
                    )
                )
            }
            return resultados
        }
    }
}
