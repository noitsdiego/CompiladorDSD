package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo() :Sentencia(){


    var nombreArreglo:Token? = null
    var tipoDato:Token? = null
    var cantidad:Token? = null

    override fun toString(): String {
        if (cantidad==null){
            return ("Es Arreglo ( nombre= ${nombreArreglo.toString()} \n tipoDato = ${tipoDato})")
        }else{
            return ("Es Arreglo ( nombre= ${nombreArreglo.toString()} \n tipoDato = ${tipoDato} \n Cantidad= ${cantidad})")
        }

    }
    constructor(nombreArreglo:Token?, tipoDato:Token?, cantidad:Token?):this(){
        this.nombreArreglo=nombreArreglo
        this.tipoDato=tipoDato
        this.cantidad=cantidad
    }

    constructor(nombreArreglo:Token?,tipoDato:Token?):this(){
        this.nombreArreglo=nombreArreglo
        this.tipoDato=tipoDato
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Lista")
        if(nombreArreglo!=null){
            raiz.children.add(TreeItem("Nombre Arreglo ${nombreArreglo!!.lexema}"))
        }
        if(tipoDato!=null){
            raiz.children.add(TreeItem("Tipo Dato ${tipoDato!!.lexema}"))
        }
        if(cantidad!=null){
            raiz.children.add(TreeItem("Cuenta ${cantidad!!.lexema}"))
        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito: String, acceso: String) {
        tablaSimbolos.guardarSimboloValor(nombreArreglo!!.lexema,tipoDato!!.lexema,true,ambito,acceso,nombreArreglo!!.fila,nombreArreglo!!.columna)
    }
}