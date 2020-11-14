package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem

//<Ciclos> ::= <CicloEliw> | <CicloFbw>
open class Ciclo ():Sentencia(){

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem<String>("Ciclo")
        return raiz
    }
}