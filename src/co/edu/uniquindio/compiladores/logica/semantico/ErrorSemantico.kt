package co.edu.uniquindio.compiladores.logica.semantico



/**
 * Clase que captura el error y genera cada mensaje
 */
class ErrorSemantico(var mensaje:String, var fila:Int, var columna:Int) {
    /**
     * Funcion que obtiene cada mensaje
     */
    fun obtenerMensaje(): String {
        return mensaje
    }

    /**
     * Nos permite cambiar el mensaje del error
     */
    fun cambiarMensaje(mensaje: String) {
        this.mensaje = mensaje
    }

    fun obtenerFila(): Int {
        return fila
    }

    fun cambiarFila(fila: Int) {
        this.fila = fila
    }

    fun obtenerColumna(): Int {
        return columna
    }

    fun cambiarColumna(columna: Int) {
        this.columna = columna
    }

    /**
     * Sobrescripcion del metodo ToString
     */
    override fun toString(): String{
        return "Error Semantico [mensaje=$mensaje, fila=$fila, columna=$columna]"
    }

}