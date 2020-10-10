package co.edu.uniquindio.compiladores.interfaz.app

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico

fun main(){

    val lexico = AnalizadorLexico("\$\$hola \$JOLA1 RR RR43.. ")
    lexico.analizar()
    println(lexico.listaTokens)

}