package co.edu.uniquindio.compiladores.logica.lexico

import co.edu.uniquindio.compiladores.logica.lexico.Categoria

/*
Clase para reconocer los tokens del analizador lexico
@autor Diego riveros, Stefanny Roman & Daniel Loaiza
 */
class Token(var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int){

    /*
    To String
     */
    override fun toString(): String {
        return "Token ( lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna )"
    }

}