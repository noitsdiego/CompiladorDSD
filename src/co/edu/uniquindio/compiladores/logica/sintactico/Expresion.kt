package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

open abstract class Expresion() {

    open fun getArbolVisual(): TreeItem<String>? {
        return null
    }

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Simbolo): String {
        return ""
    }

    open fun analizarSemantica(
            tablaSimbolos: TablaSimbolos,
            erroresSemanticos: ArrayList<ErrorSemantico>,
            ambito: Simbolo
    ) {
    }


    open fun getJavaCode(): String {
        return ""
    }

}