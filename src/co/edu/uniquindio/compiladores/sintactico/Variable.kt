package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Variable (var nombreVariable:Token, var expresion:Expresion?) {

    override fun toString(): String {
        return "Variable(nombreVariable=$nombreVariable, expresion=$expresion)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Variable")

        if(nombreVariable!=null){
            raiz.children.add(TreeItem("${nombreVariable.lexema} ") )
        }
        if(expresion!=null){
            raiz.children.add(expresion!!.getArbolVisual())
        }


        return raiz
    }
}