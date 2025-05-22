package data.dao

import data.db.AppDatabase
import java.sql.SQLException

class ErrorDao() {

    fun insertarError(mensaje: String) {
        try {
            AppDatabase.getConnection().use { conn ->
                val sql = "INSERT INTO errores (mensaje) VALUES (?)"
                conn.prepareStatement(sql).use {
                    it.setString(1, mensaje)
                    it.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("")
        }
    }

    fun obtenerErrores(): List<String> {

        try {
            AppDatabase.getConnection().use { conn ->
                val sql = "SELECT mensaje, timestamp FROM errores ORDER BY timestamp DESC"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.executeQuery().use { rs ->
                        val errores = mutableListOf<String>()
                        while (rs.next()) {
                            errores.add("${rs.getTimestamp("timestamp")} - ${rs.getString("mensaje")}")
                        }
                        return errores
                    }
                }
            }
        } catch (e: SQLException) {
            throw SQLException("")
        }

    }
}
