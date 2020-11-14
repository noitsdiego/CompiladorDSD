package co.edu.uniquindio.compiladores.sintactico

import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.logica.lexico.Token

    class Funcion (var nombre: Token,var tipoRetorno:Token ,var parametros:ArrayList<Parametro>, var bloqueSentencias:ArrayList<Sentencia>){

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

    }