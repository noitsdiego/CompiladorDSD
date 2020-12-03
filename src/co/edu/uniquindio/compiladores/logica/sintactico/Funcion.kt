package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos

class Funcion (
        var palabraFun: Token?,
        var tipoRetorno: Token,
        var nombre: Token,
        var parIzq: Token?,
        var lstParametros: ArrayList<Parametro>,
        var parDer: Token?,
        var lstSentencias: ArrayList<Sentencia>
        )
{

    override fun toString(): String {
        return "Funcion(palabraFun=$palabraFun, tipoRetrono=$tipoRetorno, nombre=$nombre, parIzq=$parIzq, " +
                "parametros=$lstParametros, parDer=$parDer, llaveIzq=!, bloqueSentencias=$lstSentencias, laveDer=¡)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Funcion")
        raiz.children.add(TreeItem("Identificador:  ${nombre?.lexema}"))
        raiz.children.add(TreeItem("Retorno:  ${tipoRetorno?.lexema}"))

        var raizParametro = TreeItem("Parametro")
        for (p in lstParametros) {
            raizParametro.children.add(p.getArbolVisual())
        }

        var raizSentencia = TreeItem("Sentencias")
        for (s in lstSentencias) {
            raizSentencia.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizParametro)
        raiz.children.add(raizSentencia)
        return raiz
    }

    fun obtenerTipoDeParametros(): java.util.ArrayList<String> {
        var lista = java.util.ArrayList<String>()
        for (p in lstParametros) {
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(
            tablaSimbolos: TablaSimbolos,
            erroresSemanticos: java.util.ArrayList<ErrorSemantico>,
            ambito: Simbolo
    ) {
        if (tipoRetorno != null) {
            tablaSimbolos.guardarSimboloFuncion(
                    nombre.lexema,
                    tipoRetorno.lexema,
                    obtenerTipoDeParametros(),
                    ambito,
                    nombre.fila,
                    nombre.columna
            )
        } else {
            tablaSimbolos.guardarSimboloFuncion(
                    nombre.lexema,
                    null,
                    obtenerTipoDeParametros(),
                    ambito,
                    nombre.fila,
                    nombre.columna
            )
        }

        for (parametro in lstParametros) {

            var ambitoFuncion: Simbolo = Simbolo(
                    nombre.lexema,
                    tipoRetorno.lexema,
                    obtenerTipoDeParametros(),
                    nombre.fila,
                    nombre.columna
            )
            tablaSimbolos.guardarSimboloValor(
                    parametro.nombre.lexema,
                    parametro.tipoDato.lexema,
                    ambitoFuncion,
                    parametro.nombre.fila,
                    parametro.nombre.columna
            )
        }

        for (s in lstSentencias) {
            var ambitoFuncion: Simbolo = Simbolo(
                    nombre.lexema,
                    tipoRetorno.lexema,
                    obtenerTipoDeParametros(),
                    nombre.fila,
                    nombre.columna
            )
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambitoFuncion)
        }
    }


    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: java.util.ArrayList<ErrorSemantico>) {
        for (s in lstSentencias) {
            var ambitoFuncion: Simbolo = Simbolo(
                    nombre.lexema,
                    tipoRetorno.lexema,
                    obtenerTipoDeParametros(),
                    nombre.fila,
                    nombre.columna
            )
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambitoFuncion)
        }

    }
    fun getJavaCode(): String {
        var codigo: String = ""
        if (nombre.lexema == "main") {
            codigo = "public static void main(String[] args){"
        } else {
            codigo = "static " + tipoRetorno.getJavaCode() + " " + nombre.lexema
            if(lstParametros.size == 0) {
                codigo+="( ){"
            }else {
                codigo += "("
                for (p in lstParametros) {
                    codigo += p.getJavaCode() + ","
                }
                codigo = codigo.subSequence(0, codigo.length - 1) as String
                codigo += "){"
            }
        }
        for (s in lstSentencias) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }

    }