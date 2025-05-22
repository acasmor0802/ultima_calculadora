package es.prog2425.calclog.data.dao

class ErrorDao(private val conn: java.sql.Connection) {

    fun insertarError(mensaje: String) {
        val sql = "INSERT INTO errores (mensaje) VALUES (?)"
        conn.prepareStatement(sql).use {
            it.setString(1, mensaje)
            it.executeUpdate()
        }
    }

    fun obtenerErrores(): List<String> {
        val sql = "SELECT mensaje, timestamp FROM errores ORDER BY timestamp DESC"
        conn.prepareStatement(sql).use {
            val rs = it.executeQuery()
            val errores = mutableListOf<String>()
            while (rs.next()) {
                errores.add("${rs.getTimestamp("timestamp")} - ${rs.getString("mensaje")}")
            }
            return errores
        }
    }
}
