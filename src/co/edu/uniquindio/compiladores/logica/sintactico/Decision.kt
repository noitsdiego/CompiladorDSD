package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

class Decision (var expresion:ExpresionLogica, var listaSentencia: ArrayList<Sentencia>, var listaSentenciaElse: ArrayList<Sentencia>?):Sentencia(){

    override fun toString(): String {
        return "Decision(expresion=$expresion, listaSentencia=$listaSentencia, listaSentenciaElse=$listaSentenciaElse)"
    }


    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem<String>("Desicion")
        var condicion =TreeItem<String>("Condicion")

        condicion.children.add(expresion.getArbolVisual())
        raiz.children.add(condicion)
        var raizTrue=TreeItem<String>("Sentencias Verdaderas")

        for (sv in listaSentencia){
            raizTrue.children.add(sv.getArbolVisual() )
        }
        raiz.children.add((raizTrue))
        if(listaSentenciaElse!=null){
            var raizfalse =TreeItem("Sentencias Falsas")

            for (sf in listaSentenciaElse!!){
                raizfalse.children.add(sf.getArbolVisual())
            }
            raiz.children.add(raizfalse)
        }

        return raiz
    }
    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito: Simbolo) {
        for (s in listaSentencia) {
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
        if (listaSentenciaElse != null) {
            for (s in listaSentenciaElse!!) {
                s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
            }
        }
    }

}