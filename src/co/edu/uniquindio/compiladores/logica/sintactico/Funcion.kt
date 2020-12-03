package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos

class Funcion (var nombre: Token
                   ,var tipoRetorno:Token
                   ,var parametros:ArrayList<Parametro>,
                   var bloqueSentencias:ArrayList<Sentencia>){

        override fun toString(): String {
            return "Funcion(nombre=$nombre, parametros=$parametros, tipoRetorno=${tipoRetorno.lexema},bloqueSentencias=$bloqueSentencias)"
        }
        fun getArbolVisual(): TreeItem<String> {
            var raiz= TreeItem("Función")
            raiz.children.add(TreeItem("Nombre: ${nombre.lexema}"))
            raiz.children.add(TreeItem("Tipo Retorno:${tipoRetorno.lexema}"))
            var raizParametros= TreeItem("Lista de Parametros")
            for (p in parametros){
                raizParametros.children.add(p.getArbolVisual())
            }
            raiz.children.add(raizParametros)
            var raizSentencia= TreeItem("Lista de Sentencias")
            for (s in bloqueSentencias){
                raizSentencia.children.add(s.getArbolVisual())
            }
            raiz.children.add(raizSentencia)
            return raiz
        }

        fun obtenerTipoDeParametros(): ArrayList<String> {
            var lista = ArrayList<String>()
            for (p in parametros) {
                lista.add(p.tipoDato.lexema)
            }
            return lista
        }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>, ambito: String,acceso:String){

        tablaSimbolos.guardarSimboloFuncion(nombre.lexema,tipoRetorno.lexema,obtenerTipoDeParametros(),ambito,acceso,nombre.fila,nombre.columna)

    }
    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>) {

        /**
         * aqui seguimos luego
         */
    }

    }