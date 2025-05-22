package data.db

import org.h2.jdbcx.JdbcDataSource
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.sql.DataSource

object AppDatabase {
    private const val JDBC_URL = "jdbc:h2:./db/calculadoradb"
    private const val USER     = "sa"
    private const val PASS     = ""

    init {
        try {
            //Class.forName("org.h2.Driver")
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("No se pudo cargar el driver de H2", e)
        }
    }

    //val connection = DriverManager.getConnection(JDBC_URL, USER, PASS)

    fun getDs(): DataSource {
        val ds = JdbcDataSource()
        ds.setURL(JDBC_URL)
        ds.user = USER
        ds.password = PASS
        return ds
    }

    /**
     * Establece la conexión a la base de datos H2.
     * Lanza una excepción si no se puede conectar.
     */
    fun getConnection(): Connection {
        return try {
            DriverManager.getConnection(JDBC_URL, USER, PASS)
        } catch (e: SQLException) {
            throw IllegalStateException("Error al conectar con la base de datos H2", e)
        }
    }

    /**
    /**
     * Cierra la conexión de forma segura.
     * Si hay error al cerrar, lanza una excepción controlada.
     */
    fun closeConnection(conn: Connection?) {
        try {
            conn?.close()
        } catch (e: SQLException) {
            throw IllegalStateException("Error al cerrar la conexión con la base de datos", e)
        }
    }
    */
}
