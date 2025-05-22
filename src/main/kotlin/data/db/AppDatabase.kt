package es.prog2425.calclog.data.db

import java.sql.Connection
import java.sql.DriverManager

object AppDatabase {
    private const val JDBC_URL = "jdbc:h2:~/db"
    private const val USER     = "sa"
    private const val PASS     = ""

    val connection: Connection by lazy {
        Class.forName("org.h2.Driver")

        // Abre la conexión y crea las tablas si no existen
        DriverManager.getConnection(JDBC_URL, USER, PASS).apply {
            createStatement().use { stmt ->
                stmt.executeUpdate(
                    """
                    CREATE TABLE IF NOT EXISTS calculos (
                        id IDENTITY PRIMARY KEY,
                        numero1 DOUBLE,
                        operador VARCHAR(10),
                        numero2 DOUBLE,
                        resultado DOUBLE,
                        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                    """
                )
                stmt.executeUpdate(
                    """
                    CREATE TABLE IF NOT EXISTS errores (
                        id IDENTITY PRIMARY KEY,
                        mensaje VARCHAR(255),
                        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                    """
                )
            }
        }
    }
}
