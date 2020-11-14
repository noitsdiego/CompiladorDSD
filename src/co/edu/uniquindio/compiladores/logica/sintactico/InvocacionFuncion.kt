package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class InvocacionFuncion (var nombreFuncion:Token, var listaArgumentos:ArrayList<Expresion>):Sentencia(){

    override fun toString(): String {
        return "InvocacionFuncion(nombreFuncion=$nombreFuncion, listaArgumentos=$listaArgumentos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Invocacion de Metodo")

        raiz.children.add(TreeItem("Nombre = ${nombreFuncion.lexema}"))

        var raizSentencia= TreeItem<String>("Sentencias ")

        for (s in listaArgumentos){
            raizSentencia.children.add(s.getArbolVisual())
        }
        raiz.children.addAll(raizSentencia)

        return raiz
    }

}