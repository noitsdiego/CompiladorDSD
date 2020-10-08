package co.edu.uniquindio.compiladores.interfaz.app

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico

fun main(){

    val lexico = AnalizadorLexico("RR R34.6 43.6RR3")
    lexico.analizar()
    print(lexico.listaTokens)

}