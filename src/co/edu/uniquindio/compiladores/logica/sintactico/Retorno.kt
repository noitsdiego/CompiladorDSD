package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

class Retorno (var palabraReservada: Token, var expresion: Expresion?, var finSentencia: Token) : Sentencia() {

    override fun toString(): String {
        return "Retorno(palabraReservada=$palabraReservada,expresion=$expresion,finSentencia=$finSentencia)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Retorno")
        raiz.children.add(expresion?.getArbolVisual())
        return raiz
    }


    override fun llenarTablaSimbolos(
            tablaSimbolos: TablaSimbolos,
            listaErrores: ArrayList<ErrorSemantico>,
            ambito: Simbolo
    ) {
    }


    override fun analizarSemantica(
            tablaSimbolos: TablaSimbolos,
            erroresSemanticos: ArrayList<ErrorSemantico>,
            ambito: Simbolo
    ) {
        var tipoExpresion:String? =null
        if(expresion != null) {
            expresion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            tipoExpresion= expresion!!.obtenerTipo(tablaSimbolos, ambito)
        }else{
            erroresSemanticos.add(ErrorSemantico("La expresion del retorno de ambito ${ambito.nombre} tiene un error",palabraReservada.fila,palabraReservada.columna))

        }

        if (tipoExpresion != ambito.tipo) {
            erroresSemanticos.add(
                    ErrorSemantico(
                            "El tipo de dato de la expresion $tipoExpresion no coincide con el metodo ${ambito.nombre} de tipo ${ambito.tipo}",
                            palabraReservada!!.fila,
                            palabraReservada!!.columna
                    )
            )
        }
    }


    override fun getJavaCode(): String {
        return palabraReservada?.getJavaCode()+" "+ expresion?.getJavaCode()+";"
    }

}