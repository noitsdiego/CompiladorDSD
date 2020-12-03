package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

open abstract class Sentencia() {

    open fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia")
        return raiz
    }

    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: Simbolo) {}

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito: Simbolo) {}

    open fun getJavaCode():String{
        return ""
    }
}