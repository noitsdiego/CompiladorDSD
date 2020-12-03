package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos

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
    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: Simbolo) {}

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito: Simbolo) {}

    open fun getJavaCode():String{
        return ""
    }
}