package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Parametro (var tipoDato:Token, var caracter:Token, var nombreParametro:Token) {

    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato, caracter='$caracter', nombreParametro=$nombreParametro)"
    }

    fun getArbolVisual(): TreeItem<String> {

        return TreeItem("${tipoDato.lexema}  ${caracter.lexema}  ${nombreParametro.lexema} ")

    }
}