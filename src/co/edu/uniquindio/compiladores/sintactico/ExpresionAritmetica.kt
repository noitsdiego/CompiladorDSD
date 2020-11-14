package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica (var valorN:ValorNumerico, var operador:Token?, var valorNU:ValorNumerico?):Expresion() {

    override fun toString(): String {
        return "ExpresionAritmetica(valorN=$valorN, operador=$operador, valorNU=$valorNU)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String> ("Expresion Aritmetica")

        if(valorNU==null){
            raiz.children.add(valorN!!.getArbolVisual())
        }else{
            raiz.children.add(valorN!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador aritmetico= ${operador!!.lexema}"))
            raiz.children.add(valorNU!!.getArbolVisual())
        }
        return raiz
    }
}