package co.edu.uniquindio.compiladores.logica.lexico

import co.edu.uniquindio.compiladores.logica.lexico.Categoria

/*
Clase para reconocer los tokens del analizador lexico
@autor Diego riveros, Stefanny Roman & Daniel Loaiza
 */

class Error (var error:String, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Error(error='$error', fila=$fila, columna=$columna)"
    }
}