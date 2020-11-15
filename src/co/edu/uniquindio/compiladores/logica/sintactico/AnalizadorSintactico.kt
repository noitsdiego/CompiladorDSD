package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Categoria
import co.edu.uniquindio.compiladores.logica.lexico.Token
import java.util.ArrayList

class AnalizadorSintactico (var listaTokens: ArrayList<Token>) {

    /**
     * Variables basicas para el funcionamiento del analizador Sintactico
     */
    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<ErrorSintactico>()

    /**
     * Funcion que nos permite ir cambiando de posicion en la lista de tokens
     */
    fun obtenerSiguienteToken() {
        posicionActual++
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        } else {
            tokenActual = Token("", Categoria.FINAL_CODIGO, tokenActual.fila, tokenActual.columna)
        }
    }

    /**
     * Funcion que nor permite listar nuestros errore sintacticos
     * @param mensaje; mensaje generado por el error
     */
    fun reportarErrores(mensaje: String) {
        listaErrores.add(ErrorSintactico(mensaje, tokenActual.fila, tokenActual.columna))
    }

    /**
     * Hacer backtracking, ya que no corresponde el término
     */
    fun hacerBTToken(posicionToken: Int) {
        posicionActual = posicionToken
        tokenActual = if (posicionToken < listaTokens.size) {
            listaTokens[posicionToken]
        } else {
            Token("", Categoria.ERROR, 0, 0)
        }
    }

    /**
     * <unidadDeCompilacion> :: = <listaDeFunciones>
     * <UnidadDeCompilacion> ::= [<VariablesGlobales>] <ListaFunciones> FinCodigo
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        var listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if (listaFunciones.size > 0) {
            UnidadDeCompilacion(listaFunciones)
        } else {
            null
        }
    }

    /**
     * <ListaFunciones> ::= <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()
        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }

        return listaFunciones
    }


    /*
    <DeclaracionMetodos> ::= fonc <NombreMetodo> <tipoRetorno> “(“ [<Parametros>] “)” “{” <Sentencia> “}”
    <Funcion> ::= “fun” <NombreMetodo> <TipoRetorno> “)“ [<ListaParametros>] “(” <BloqueSentencias>
     */
    fun esFuncion(): Funcion? {
        if(tokenActual.categoria == Categoria.FUNCION && tokenActual.lexema == "fun"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.IDENTIFICADOR_CLASE){
                var nombreFuncion = tokenActual
                obtenerSiguienteToken()
                var tipoRetorno = tipoRetorno()
                if(tipoRetorno != null) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.AGRUPADOR_DERECHO) {
                        obtenerSiguienteToken()
                        var listaParametros = esListaParametros()
                        if (tokenActual.categoria == Categoria.AGRUPADOR_IZQUIERDO) {
                            obtenerSiguienteToken()
                            var bloqueSentencias = esBloqueSentencias()
                            if (bloqueSentencias != null) {
                                return Funcion(nombreFuncion,tipoRetorno, listaParametros, bloqueSentencias)
                            } else {
                                reportarError("El bloque de sentencias está vacía")
                            }
                        } else {
                            reportarError("Hace falta el parentesis derecho")
                        }
                    } else {
                        reportarError("Hace falta el parentesis izquierdo")
                    }
                }else{
                    reportarError("Hace falta es especificar el retorno")
                }
            } else{
                reportarError("Hace falta el nombre de la función")
            }
        }
        return null
    }

    /*
        <TipoRetorno>::= etr| rlsl | bbn| crt| Pal|void
    */
    fun tipoRetorno():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "etr" || tokenActual.lexema == "rlsl" ||
                        tokenActual.lexema == "bbn" || tokenActual.lexema == "crt" || tokenActual.lexema == "Pal" || tokenActual.lexema == "void")){
            return tokenActual
        }
        return null
    }

    /*
    <ListaParametros> ::= <Parametro> [“|” <ListaParametros>]

     */
    fun esListaParametros():ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()
        while (parametro != null) {
            listaParametros.add(parametro)
            if(tokenActual.categoria == Categoria.SEPARADOR){
                obtenerSiguienteToken()
                parametro = esParametro()
            } else{
                if(tokenActual.categoria != Categoria.AGRUPADOR_DERECHO){
                    reportarError("Hace falta la coma en la lista de parametros")
                }
                break
            }
        }
        return listaParametros
    }

    /*
        <Parametro> ::= <TipoDato> = <NombreDato> [“,” <Parametro>]
        <Parametro> ::= <TipoDato> “I” <NombreVariable>[“|” <Parametro>]
         */
    fun esParametro():Parametro?{
        var tipoDato = esTipoDato()
        if(tipoDato != null){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                var caracter = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                    var nombreParametro = tokenActual
                    obtenerSiguienteToken()
                    return Parametro(tipoDato, caracter, nombreParametro)
                } else{
                    reportarError("Hace falta el nombre del dato")
                }
            } else{
                reportarError("Hace falta el caracter igual")
            }
        } else{
            reportarError("Espacio en blanco, por favor indique el parámetro")
        }
        return  null
    }

    /*
    <TipoDato> ::= etr | rls | bbn | crt | Pal
     */
    fun esTipoDato():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "etr"
                    || tokenActual.lexema == "rls" || tokenActual.lexema == "bbn" || tokenActual.lexema == "crt"|| tokenActual.lexema == "Pal"){
                return tokenActual
            }
        }
        return null
    }

    /*
        <BloqueSentencias> ::= "{" [<ListaSentencias>] "}"
        <BloqueSentencias> ::= “¡” <ListaSentencias> “!”

         */
    fun esBloqueSentencias():ArrayList<Sentencia>? {
        if(tokenActual.categoria == Categoria.AGRUPADOR_SEN_DERECHO){
            obtenerSiguienteToken()
            var listaSentencias = esListaSentencias()
            if(tokenActual.categoria == Categoria.AGRUPADOR_SEN_IZQUIERDA){
                obtenerSiguienteToken()
                return listaSentencias
            } else{
                reportarError("Falta el agrupador derecho del bloque de sentencias")
            }
        } else{
            reportarError("Falta el agrupador izquierdo del bloque de sentencias")
        }
        return null
    }

    /**
        <ListaSentencias> ::= <Sentencia> [<ListaSentencias>]

    fun esListaSentencias():ArrayList<Sentencia>{
        val lista = ArrayList<Sentencia>()
        var sentencia: Sentencia? = esSentencia()
        while (sentencia != null) {
            lista.add(sentencia)
            sentencia = esSentencia()
        }
        return lista
    }
    */

    fun reportarError(mensaje:String){
        listaErrores.add(ErrorSintactico(mensaje, tokenActual.fila, tokenActual.columna))
    }

}