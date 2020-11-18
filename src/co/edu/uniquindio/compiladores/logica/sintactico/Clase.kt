package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Clase (var nombreClase:Token, var expresion:Expresion?) {

    override fun toString(): String {
        return "Clase(nombreClase=$nombreClase, expresion=$expresion)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Clase")

        if(nombreClase!=null){
            raiz.children.add(TreeItem("${nombreClase.lexema} ") )
        }
        if(expresion!=null){
            raiz.children.add(expresion!!.getArbolVisual())
        }


        return raiz
    }
}