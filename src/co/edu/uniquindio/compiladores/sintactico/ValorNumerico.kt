package co.edu.uniquindio.compiladores.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico (var valor:Token, var signo:Token) {

    override fun toString(): String {
        return "ValorNumerico( Signo= ${signo} valor='$valor')"
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String> ("Valor numerico")

        if(signo.lexema==""){
            raiz.children.add(TreeItem(" ${valor.lexema}"))
        }else{
            raiz.children.add(TreeItem("${signo.lexema}${valor.lexema}"))
        }
        return raiz
    }
}