package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class IncrementoDecremento (var expresion:Token?){

    override fun toString(): String {
        return "ExpresionIterador(expresion=$expresion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Exprecion Iterador")
        raiz.children.add(TreeItem("${expresion!!.lexema}"))

        return raiz
    }
}