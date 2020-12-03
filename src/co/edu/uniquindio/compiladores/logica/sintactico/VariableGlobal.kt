package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

class VariableGlobal (var nombreVariableGlobal:Token, var expresion:Expresion?) {

    override fun toString(): String {
        return "VariableGlobal(nombreVariableGlobal=$nombreVariableGlobal, expresion=$expresion)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Variable Global")

        if (nombreVariableGlobal != null) {
            raiz.children.add(TreeItem("${nombreVariableGlobal.lexema} "))
        }
        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
        }


        return raiz
    }
    fun llenarTablaSimbolos(
            tablaSimbolos: TablaSimbolos,
            erroresSemanticos: java.util.ArrayList<ErrorSemantico>,
            ambito: Simbolo

    ) {

        tablaSimbolos.guardarSimboloValor(nombreVariableGlobal.lexema,"",ambito,nombreVariableGlobal.fila,nombreVariableGlobal.columna)
    }
}