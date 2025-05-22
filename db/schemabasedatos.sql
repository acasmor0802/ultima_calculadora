CREATE TABLE IF NOT EXISTS calculos (
                                        id IDENTITY PRIMARY KEY,
                                        numero1 DOUBLE,
                                        operador VARCHAR(10),
    numero2 DOUBLE,
    resultado DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS errores (
                                       id IDENTITY PRIMARY KEY,
                                       mensaje VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );