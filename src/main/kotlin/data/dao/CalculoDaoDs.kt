package org.example.data.dao

import data.db.AppDatabase
import java.sql.SQLException
import javax.sql.DataSource

class CalculoDaoDs(private val ds: DataSource) {

    fun insertarCalculo(numero1: Double, operador: String, numero2: Double, resultado: Double) {
        try {
            ds.connection.use { conn ->
                val sql = "INSERT INTO calculos (numero1, operador, numero2, resultado) VALUES (?, ?, ?, ?)"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, numero1)
                    stmt.setString(2, operador)
                    stmt.setDouble(3, numero2)
                    stmt.setDouble(4, resultado)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException){
            throw SQLException("Error al insertar calculos en la base de datos: $e")
        }
    }

    fun obtenerTodos(): List<String> {
        try {
            ds.connection.use { conn ->
                val sql = "SELECT numero1, operador, numero2, resultado, timestamp FROM calculos ORDER BY timestamp DESC"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.executeQuery().use { rs ->
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
        } catch (e: SQLException) {
            throw SQLException("Error al obtener calculos en la base de datos: $e")
        }
    }
}