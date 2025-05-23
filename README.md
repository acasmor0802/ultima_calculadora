Para este ejercicio de la calculadora, seleccione la calculadora solución que mandaste.
Le elimine todo lo relacionado con los logs ya existentes pues los iba a hacer en una bbdd.
Despues había creado dos carpetas en data:
- ## Una con db, que contiene AppDatabase, en la que se crean las conexiones en un objeto:
- ## Una carpeta dao en la que se ejecutaran las consultas en SQL sobre las bbdd. 
- ### La clase CalculoDao, recibe una conexion, y sus metodos son:
- #### ```fun insertarCalculo(numero1: Double, operador: String, numero2: Double, resultado: Double)``` que recibe el calculo y mediante un insert, lo almacena en la bbdd añadiendo tambien su fecha ya que al crear la tabla se escribe ```timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP``` que lo que hace es almacenar la fecha en la que se hace el insert. Lanzaría un ```SQLException``` en el caso en el que diera un error.
- #### ```fun obtenerTodos(): List<String>``` que recoge todos los calculos almacenados en la tabla con el formato ```"%.2f %s %.2f = %.2f (%s)".format```, con una consulta que además, los ordena por fecha. El desarrollo de esta función sería el siguiente:
1. Se inicia un try catch para atrapar cualquier error y lanzarlo con un throw. <br>
2. Se crea una conexion con Appdatabase para crear una conexion que se cierra automaticamente con el .use. <br>
3. Se crea un stmt con otro .use para que se cierre automaticamente.
4. Se ejecuta la consulta y obtienen resultados con un formato especifico que se almacenan en una variable mutablelist para ser posteriormente mostrada (tambien se cierra automaticamente con un .use).
- ### La clase ErrorDao, recibe una conexion, y sus metodos son:
- #### ```fun insertarError(mensaje: String)``` que solo inserta el mensaje de error, ademas de la fecha en la que se ejecuta esta consulta que viene en la creacion de la tabla ```timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP```.
- #### ```fun obtenerErrores(): List<String>``` que recoge todos los errores guardados en la bbdd con el formato ```errores.add("${rs.getTimestamp("timestamp")} - ${rs.getString("mensaje")}")```.
Esta capa es controlada desde ServicioLogH2, en la que con los metodos ```override fun registrarEntradaLog(mensaje: String)``` y ```override fun registrarEntradaLog(calculo: Calculo)``` se pasan los parametros a las funciones anteriores para hacer un insert en la bbdd y con ```override fun getInfoUltimoLog(): List<String>``` se hacen en ambas tablas la consulta que devuelve una lista y las une en el return de la funcion.
- En app/Controlador se puede ver el control de todo el programa, asi que voy a contar el rumbo que tendria el programa despues de contar las funciones de esta clase:
- La funcion ```fun iniciar(args: Array<String>)``` llama a la funcion mostrar logs, ya que al iniciarse el programa lo primero que hace es mostrar los logs de las cuentas y errores guardados en la base de datos. Despues hay un if que lo que hace es realizar una cuenta instantaneamente si se inicia el programa con los datos ya ingresados. Despues llama a la funcion pausar para que el usuario pulse enter para iniciar la calculadora, y limpiarPantalla. A continuación entraría en el bucle en el que se manejaria la mayor parte del tiempo el programa.
- ```private fun mostrarInfoLog()``` Lo que hace es llamar al service, que lo que haría sería: realizar las 2 consultas de obtener anteriormente explicadas, y unirlas en un return. Despues se comprueba si estan vacias, si lo estan muestra un mensaje de que no hay logs. Pero si existen logs los muestra con un forEach mostrandolo llamando al ui.
- ```private fun ejecutarCalculoConArgumentos(args: Array<String>)``` Lo que hace es que le pasan 4 valores, pero solo utilizan los 3 ultimos, ya que son los que contienen la cuenta, comprueba que los 3 elementos sean validos y realizaria la cuenta, sino le pasaria a la ui un msj que mostraria un error.
- ``` private fun bucleCalculosUsuario()``` Aquí se realiza toda la lógica una vez se empieza el programa y se muestran los logs. primero se hace un try catch para atrapar cualquier error del programa, acto seguido, se van pidiendo al usuario que ingrese los valores o se lanzaran errores. Después se realizara el cálculo y se guardara en la bbdd además de mostrarse por pantalla. por último se preguntará si se quiere acabar el programa o continuar.


## Ciclo del programa
- En el main se realizan las inyecciones y se inicia controlador iniciar
- En iniciar se muestran los logs si hay llamando a ServicioLog que a su vez llamaria a los dao para realizar una consulta y devolver una lista. Luego se empieza el bucle del programa.
- En el bucle se piden valores como numeros o simbolos, que se comprueban y si son errores, se lanza un error y se guardaría en el Dao de errores, si por otro lado no hay errores, se realiza el calculo llamando al ServicioCalc para que realice la cuenta en el model la Clase Calculo, Se mostraría desde la ui y se guardaria en la bbdd de cuentas.
- Se preguntaría si se quiere continuar con los calculos, si se dice s o si se continuaria en el bucle, sino se acabaría el programa.
