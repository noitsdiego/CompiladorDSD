package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionLogica (var expresionRelacional:ExpresionRelacional?, var operador:Token, var expRelacional: ExpresionRelacional?):Expresion() {

    override fun toString(): String {
        return "ExpresionLogica(expresionRelacional=$expresionRelacional, operador=$operador, expRelacional=$expRelacional)"
    }

     override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String> ("Expresion Logica")

        if(expRelacional==null){
            raiz.children.add(TreeItem("Operador Unario = ${operador.lexema}"))
            raiz.children.add(expresionRelacional!!.getArbolVisual())
        }else{
            raiz.children.add(expresionRelacional!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Logico= ${operador.lexema}"))
            raiz.children.add(expRelacional!!.getArbolVisual())
        }

        return raiz
    }
}