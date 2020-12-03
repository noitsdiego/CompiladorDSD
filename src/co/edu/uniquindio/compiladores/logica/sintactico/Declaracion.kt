package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

class Declaracion (var tipoDeclaracion: Token, var listaVariables:ArrayList<Variable>):Sentencia() {

    override fun toString(): String {
        return "Declaracion(tipoDeclaracion=$tipoDeclaracion, listaVariables=$listaVariables)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String> ("Declaracion")
        raiz.children.add(TreeItem("Tipo Declaracion ${tipoDeclaracion.lexema}"))
        var raizLista=TreeItem<String> ("Lista de Parametros")

        for(va in listaVariables){
            raizLista.children.add(va.getArbolVisual())
        }
        raiz.children.add(raizLista)

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito: String, acceso: String) {
        for (v in listaVariables){
            tablaSimbolos.guardarSimboloValor(v.nombreVariable.lexema,tipoDeclaracion.lexema,true,ambito,acceso,v.nombreVariable.fila,v.nombreVariable.columna)
        }
    }
}