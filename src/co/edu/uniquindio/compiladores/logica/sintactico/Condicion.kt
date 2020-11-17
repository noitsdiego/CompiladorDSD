package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem

class Condicion (var nombreVariable:Variable, var expresionLogica:ExpresionLogica, var nombreVar:Variable, var incrementoDecremento:Iterador) {

    override fun toString(): String {
        return "Condicion(nombreVariable=$nombreVariable, expresionLogica=$expresionLogica, nombreVar=$nombreVar, expresionIterador=$incrementoDecremento)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Condicion")

        raiz.children.add(nombreVariable.getArbolVisual())
        raiz.children.add(expresionLogica.getArbolVisual())
        raiz.children.add(incrementoDecremento.getArbolVisual())

        return raiz
    }
}