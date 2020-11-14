package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion (var identificador:Token, var operadorAsignacion:Token, var expresion:Expresion):Sentencia() {

    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Asignación")

        raiz.children.add(TreeItem("Identificador = ${identificador.lexema}"))
        raiz.children.add(TreeItem("Identificador = ${operadorAsignacion.lexema}"))
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }
}