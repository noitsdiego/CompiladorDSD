package co.edu.uniquindio.compiladores.sintactico

import javafx.scene.control.TreeItem

class CicloEliw (var expresionRelacional: ExpresionRelacional, var listaSentencia:ArrayList<Sentencia>, var incrementoDecremento:IncrementoDecremento?):Ciclo() {

    override fun toString(): String {
        return "CicloEliw(expresionRelacional=$expresionRelacional, listaSentencia=$listaSentencia, expresionIterador=$incrementoDecremento)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Ciclo Eliw")

        raiz.children.add(expresionRelacional.getArbolVisual())

        var raizSentencia= TreeItem<String>("Sentencias ")

        for (s in listaSentencia){
            raizSentencia.children.add(s.getArbolVisual())
        }
        raiz.children.addAll(raizSentencia)

        raiz.children.add(incrementoDecremento!!.getArbolVisual())

        return  raiz
    }
}