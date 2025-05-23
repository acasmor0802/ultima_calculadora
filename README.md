Para este ejercicio de la calculadora, seleccione la calculadora solución que mandaste.
Le elimine todo lo relacionado con los logs ya existentes pues los iba a hacer en una bbdd.
Despues había creado dos carpetas en data:
- ## Una con db, que contiene AppDatabase, en la que se crean las conexiones en un objeto:
- ## Una carpeta dao en la que se ejecutaran las consultas en SQL sobre las bbdd. 
- ### La clase CalculoDao, recibe una conexion, y sus metodos son:
- #### ```fun insertarCalculo(numero1: Double, operador: String, numero2: Double, resultado: Double)``` que recibe el calculo y mediante un insert, lo almacena en la bbdd añadiendo tambien su fecha ya que al crear la tabla se escribe ```timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP``` que lo que hace es almacenar la fecha en la que se hace el insert. Retornaria una lista de strings llamada resultados.
- #### ```fun obtenerTodos(): List<String>``` que recoge todos los calculos almacenados en la tabla con el formato ```"%.2f %s %.2f = %.2f (%s)".format```, con una consulta que además, los ordena por fecha.
- ### La clase ErrorDao, recibe una conexion, y sus metodos son:
- #### ```fun insertarError(mensaje: String)``` que solo inserta el mensaje de error, ademas de la fecha en la que se ejecuta esta consulta que viene en la creacion de la tabla ```timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP```.
- #### ```fun obtenerErrores(): List<String>``` que recoge todos los errores guardados en la bbdd con el formato ```errores.add("${rs.getTimestamp("timestamp")} - ${rs.getString("mensaje")}")```.
Esta capa es controlada desde ServicioLogH2, en la que con los metodos ```override fun registrarEntradaLog(mensaje: String)``` y ```override fun registrarEntradaLog(calculo: Calculo)``` se pasan los parametros a las funciones anteriores para hacer un insert en la bbdd y con ```override fun getInfoUltimoLog(): List<String>``` se hacen en ambas tablas la consulta que devuelve una lista y las une en el return de la funcion.
