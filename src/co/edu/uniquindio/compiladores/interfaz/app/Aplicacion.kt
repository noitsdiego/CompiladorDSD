package co.edu.uniquindio.compiladores.interfaz.app

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico

fun main(){

    val lexico = AnalizadorLexico("_ || @hola_ °hola° ¬ «Soy una cadena» <d>")
    lexico.analizar()

    val array = lexico.listaTokens

    for ( token in array)
    {
        println(token)
    }

}