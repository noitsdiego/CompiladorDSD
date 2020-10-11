package co.edu.uniquindio.compiladores.interfaz.app

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico

fun main(){

    val lexico = AnalizadorLexico("\$o Pal +I + AND NOT OR I **I +I //I *I* */I +II")
    lexico.analizar()

    val array = lexico.listaTokens

    for ( token in array)
    {
        println(token)
    }

}