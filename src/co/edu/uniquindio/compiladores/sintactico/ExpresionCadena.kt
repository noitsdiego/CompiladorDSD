package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena (var identificador:Token, var operadorCadena:Token, var expresion:Expresion):Sentencia() {
        override fun toString(): String {
            return "ExpresionCadena(identificador=$identificador, operadorCadena=$operadorCadena, expresion=$expresion)"
        }
        override fun getArbolVisual(): TreeItem<String> {
            var raiz = TreeItem<String>("Expresion de cadena")

            raiz.children.add(TreeItem("Identificador = ${identificador.lexema}"))
            raiz.children.add(TreeItem("Identificador = ${operadorCadena.lexema}"))
            raiz.children.add(expresion.getArbolVisual())

            return raiz
        }
    }
