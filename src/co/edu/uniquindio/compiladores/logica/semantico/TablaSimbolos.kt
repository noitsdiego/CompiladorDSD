package co.edu.uniquindio.compiladores.logica.semantico

class TablaSimbolos(var listaErrores: ArrayList<ErrorSemantico>) {
    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un símbolo que represente una variable, un parametro, una constante,un arreglo
     */
    fun guardarSimboloValor(nombre:String, tipoDato:String , modificable:Boolean, ambito:String,acceso:String, fila:Int, columna:Int): Simbolo? {
        val s= buscarSimboloValor(nombre,ambito)
        if(s==null){
            listaSimbolos.add(Simbolo(nombre, tipoDato, modificable, ambito, acceso, fila, columna))
        }else{
            listaErrores.add(ErrorSemantico("El campo $nombre existe dentro del ambito $ambito",fila,columna))
        }
        return null
    }

    /**
     * Permite guardar un símbolo que representa una funcion
     */
    fun guardarSimboloFuncion(nombre:String, tipoRetorno:String,tipoParametros:ArrayList<String>,ambito:String,acceso: String, fila:Int, columna:Int): Simbolo? {
        var s = buscarSimboloFuncion(nombre, tipoParametros)
        if(s==null){
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tipoParametros, ambito, acceso))
        }else{
            listaErrores.add(ErrorSemantico("La función $nombre existe dentro del ambito $ambito",fila,columna))
        }
        return null
    }

    /**
     *Funcion que nos permite buscar en la tabla de simbolos  si se encuentra
     * el valor que buscamos
     */
    fun buscarSimboloValor(nombre: String, ambito: String): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.tipoParametros == null) {
                if (simbolo.nombre == nombre && simbolo.ambito == ambito) {
                    return simbolo
                }
            }
        }
        return null
    }

    /**
     *Funcion que nos permite buscar en la tabla de funciones  si se encuentra
     * la funcion  que buscamos
     */
    fun buscarSimboloFuncion(nombre: String, tiposParametros: ArrayList<String>): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.tipoParametros != null) {
                if (nombre == simbolo.nombre && tiposParametros == simbolo.tipoParametros) {
                    return simbolo
                }

            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}