package co.edu.uniquindio.compiladores.logica.semantico

import java.util.ArrayList

class TablaSimbolos(var listaErrores: ArrayList<ErrorSemantico>) {
    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un símbolo de tipo valor en la tabla de símbolos
     */
    fun guardarSimboloValor(nombre: String, tipo: String?, ambito: Simbolo, fila: Int, columna: Int): Simbolo? {
        val s = buscarSimboloValor(nombre, ambito)
        if (s == null) {
            val nuevo = Simbolo(nombre, tipo, true, ambito, fila, columna)
            listaSimbolos.add(nuevo)
            return nuevo
        } else {
            listaErrores.add(
                    ErrorSemantico(
                            "La variable $nombre  ya existe en el ambito ${ambito.nombre!!} !!",
                            fila,
                            columna
                    )
            )
        }
        return null
    }

    /**
     * Permite guardar un símbolo de tipo función en la tabla de símbolos
     */
    fun guardarSimboloFuncion(
            nombre: String, tipo: String?, tipoParametros:
            ArrayList<String>, ambito: Simbolo, fila: Int, columna: Int
    ): Simbolo? {
        var s = buscarSimboloFuncion(nombre, tipoParametros)
        if (s == null) {
            var nuevo = Simbolo(nombre, tipo, tipoParametros, fila, columna)
            listaSimbolos.add(nuevo)
            return nuevo
        } else {
            listaErrores.add(
                    ErrorSemantico(
                            "La función $nombre de parametros ($tipoParametros) ya existe !!",
                            fila,
                            columna
                    )
            )
        }
        return null
    }

    /**
     *Funcion que nos permite buscar en la tabla de simbolos  si se encuentra
     * el valor que buscamos
     */
    fun buscarSimboloValor(nombre: String, ambito: Simbolo): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.ambito != null) {
                if (nombre == simbolo.nombre && ambito.nombre!! == simbolo.ambito!!.nombre) {
                    if (ambito.tipoParametros!!.size == simbolo.ambito!!.tipoParametros!!.size) {
                        var p = 0
                        while (p < ambito.tipoParametros!!.size && ambito.tipoParametros!![p] == simbolo.ambito!!.tipoParametros!![p]) {
                            p += 1
                        }
                        if (p == ambito.tipoParametros!!.size) {
                            return simbolo
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     *Funcion que nos permite buscar en la tabla de funciones  si se encuentra
     * la funcion  que buscamos
     */
    fun buscarSimboloFuncion(nombre: String, tiposParametros: ArrayList<String>):
            Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.tipoParametros != null) {
                if (nombre == simbolo.nombre && tiposParametros ==
                        simbolo.tipoParametros
                ) {
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