package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional (var expresionAritmetica:ExpresionAritmetica?, var operadorRelacional:Token, var expAritmetica:ExpresionAritmetica?):Expresion() {

    override fun toString(): String {
        return "ExpresionRelacional(expresionAritmetica=$expresionAritmetica, operadorRelacional=$operadorRelacional, expAritmetica=$expAritmetica)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String> ("Expresion Relacional")

        raiz.children.add(expresionAritmetica!!.getArbolVisual())
        raiz.children.add(TreeItem("Operador Relacional= ${operadorRelacional.lexema}"))
        raiz.children.add(expAritmetica!!.getArbolVisual())

        return raiz
    }
}