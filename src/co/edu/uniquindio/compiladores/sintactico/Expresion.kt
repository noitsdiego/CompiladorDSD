package co.edu.uniquindio.compiladores.sintactico

import javafx.scene.control.TreeItem

open class Expresion {
    //<ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionLogica> | |<ExpresionCadena>
    var expreRelacional:ExpresionRelacional? = null
    var expreAritmetica:ExpresionAritmetica? = null
    var expreCadena:ExpresionCadena? = null
    var expreLogica: ExpresionLogica? = null

    override fun toString(): String {
        if(expreRelacional!= null){
            return "Expresion Relacional :( ${expreRelacional} )"
        }else{if(expreAritmetica!= null){
                return "Expresion Arirmetica :( ${expreAritmetica} )"
            }else{if(expreLogica!= null){
                    return "Expresion Logica :( ${expreLogica} )"
                }else{if(expreCadena!= null){
                        return "Expresion Cadena :( ${expreCadena} )"
                        }else{
                            return "expresion no encontrada"
                            }
                       }
                }
        }
        return super.toString()
    }

    open fun getArbolVisual():TreeItem<String>{
        return  TreeItem("Expresion")
    }
    constructor(expreRelacional:ExpresionRelacional){
        this.expreRelacional= expreRelacional
    }

    constructor(expreAritmetica:ExpresionAritmetica){
        this.expreAritmetica= expreAritmetica
    }
    constructor(expreCadena:ExpresionCadena){
        this.expreCadena= expreCadena
    }
    constructor(expreLogica:ExpresionLogica){
        this.expreLogica= expreLogica
    }
    constructor(){

    }
}