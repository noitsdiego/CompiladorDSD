package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class VariableGlobal (var nombreVariableGlobal:Token, var expresion:Expresion?) {

    override fun toString(): String {
        return "VariableGlobal(nombreVariableGlobal=$nombreVariableGlobal, expresion=$expresion)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Variable")

        if (nombreVariableGlobal != null) {
            raiz.children.add(TreeItem("${nombreVariableGlobal.lexema} "))
        }
        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
        }


        return raiz
    }
}