package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloEliw (var expresionRelacional: ExpresionRelacional, var listaSentencia:ArrayList<Sentencia>):Ciclo() {

    override fun toString(): String {
        return "CicloEliw(expresionRelacional=$expresionRelacional, listaSentencia=$listaSentencia)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Ciclo Eliw")

        raiz.children.add(expresionRelacional.getArbolVisual())

        var raizSentencia= TreeItem<String>("Sentencias ")

        for (s in listaSentencia){
            raizSentencia.children.add(s.getArbolVisual())
        }
        raiz.children.addAll(raizSentencia)


        return  raiz
    }
    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito:Simbolo) {
        for (s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }

    }
}