package co.edu.uniquindio.compiladores.interfaz.app

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.logica.lexico.Token

fun main(){

    val lexico = AnalizadorLexico("etr  \$si\$ fbw rlsd -II +II")
    lexico.analizar()

    var array = lexico.listaTokens

    for ( token in array)
    {
        println(token)
    }

}