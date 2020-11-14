package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem

class Impresion (var expresion:Expresion):Sentencia(){

    override fun toString(): String {
        return "Impresion(expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem<String>("Impresion")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }
}