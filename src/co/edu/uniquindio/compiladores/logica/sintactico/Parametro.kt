package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Parametro(var tipoDato:Token, var nombre:Token) {

    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato,nombre=$nombre)"
    }

    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Tipo: ${tipoDato?.lexema} : Identificador:  ${nombre?.lexema}")
    }
    fun getJavaCode():String{
        return tipoDato.getJavaCode() +" "+nombre.lexema
    }

}