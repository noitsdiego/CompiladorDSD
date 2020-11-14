package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem

class Retorno (var expresion:Expresion):Sentencia(){

    override fun toString(): String {
        return "Retorno(expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Lista")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }
}