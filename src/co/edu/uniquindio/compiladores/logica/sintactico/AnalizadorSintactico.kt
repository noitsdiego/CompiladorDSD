package co.edu.uniquindio.compiladores.logica.sintactico

import co.edu.uniquindio.compiladores.logica.lexico.Categoria
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.lexico.Error



class AnalizadorSintactico (var listaTokens: ArrayList<Token>) {

    /**
     * Variables basicas para el funcionamiento del analizador Sintactico
     */
    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<ErrorSintactico>()
    var esMetodo = true
    var esPosible = false

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

    fun hacerBacktracking(posi:String){
        posicionActual=posi.toInt()
        tokenActual = listaTokens[posicionActual]
    }

    /**
     * <unidadDeCompilacion> :: = <listaDeFunciones>
     * <UnidadDeCompilacion> ::= [<VariablesGlobales>] <ListaFunciones> FinCodigo
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {


        var listaVariablesG: ArrayList<VariableGlobal> = esListaVariablesGlobales()
        var listaFunciones: ArrayList<Funcion> = esListaFunciones()


        if (listaVariablesG.size>0||listaFunciones.size > 0) {
            return UnidadDeCompilacion(listaVariablesG, listaFunciones)
        } else {
            return null
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
    <Funcion> ::= “fun” <NombreMetodo> <TipoRetorno> “)“ [<ListaParametros>] “(” <BloqueSentencias>
     */
    fun esFuncion(): Funcion? {
        if(tokenActual.categoria == Categoria.FUNCION && tokenActual.lexema == "fun"){
            var funIdenti = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.IDENTIFICADOR_METODO){
                var nombreFuncion = tokenActual
                obtenerSiguienteToken()
                var tipoRetorno = tipoRetorno()
                if(tipoRetorno != null) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.AGRUPADOR_METODO_ABRIR) {
                        var agrupador1 = tokenActual
                        obtenerSiguienteToken()
                        var listaParametros = esListaParametros()
                        if (tokenActual.categoria == Categoria.AGRUPADOR_METODO_CERRAR) {
                            var agrupador2 = tokenActual
                            obtenerSiguienteToken()
                            var bloqueSentencias = esBloqueSentencias()
                            if (bloqueSentencias != null) {
                                return Funcion(funIdenti, tipoRetorno , nombreFuncion,agrupador1,
                                        listaParametros, agrupador2, bloqueSentencias)
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
            obtenerSiguienteToken()
            if(tokenActual.categoria != Categoria.SEPARADOR){
                return listaParametros
            }else
            {
                obtenerSiguienteToken()
                parametro = esParametro()
            }
        }
        return listaParametros
    }

    /*
        <Parametro> ::= <TipoDato> <NombreDato> [“,” <Parametro>]
        <Parametro> ::= <TipoDato> <NombreVariable>[“|” <Parametro>]
         */
    fun esParametro():Parametro?{
        var tipoDato = esTipoDato()
        if(tipoDato != null){
            obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                    var nombreParametro = tokenActual
                    return Parametro(tipoDato, nombreParametro)
                } else{
                    reportarError("Hace falta el nombre del dato")
                }
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
        if(tokenActual.categoria == Categoria.AGRUPADOR_SEN_ABRIR){
            obtenerSiguienteToken()
            var listaSentencias = esListaSentencias()
            if(tokenActual.categoria == Categoria.AGRUPADOR_SEN_CERRAR){
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
     */
    fun esListaSentencias():ArrayList<Sentencia>{
        val lista = ArrayList<Sentencia>()
        var sentencia: Sentencia? = esSentencia()
        while (sentencia != null) {
            lista.add(sentencia)
            sentencia = esSentencia()
        }
        return lista
    }

    /**
    <Sentencia> ::=  <Decision> | <DeclaracionVariable> | <Impresion> | <Ciclo> | <Lectura> | <ExpresionAsignacion>
    | <InvocacionFuncion> | <Listas> | <Retorno>

    <Sentencia> ::=  <Asignacion> | <IncrementoDecremento> |<Arreglo>

     */
    fun esSentencia():Sentencia?{

        var sentencia:Sentencia? = esDecision()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esDeclaracion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esImpresion()
        if(sentencia != null){
            return sentencia
        }
        //FALTA POR TERMINAR EL CICLO DO
        sentencia = esCiclo()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esLectura()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esExpresionAsignacion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esInvocacionFuncion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esRetorno()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esArreglo()
        if(sentencia != null){
            return sentencia
        }
        return null
    }

    /*
        <Decision> ::= $si “)” <Expresion> “(” <BloqueSentencias> [$sino<BloqueSentencia>]
        | $si “)” <Expresion> “(” <BloqueSentencias> [$o<BloqueSentencia>]
         */
    fun esDecision():Decision?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "\$si"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.AGRUPADOR_METODO_ABRIR){
                obtenerSiguienteToken()
                esMetodo=false
                var expresion = esExpresionLogica()
                esMetodo=true
                if(expresion != null){
                    if(tokenActual.categoria == Categoria.AGRUPADOR_METODO_CERRAR){
                        obtenerSiguienteToken()
                        var bloqueSentenciasS = esBloqueSentencias()
                        if(bloqueSentenciasS != null){
                            if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "\$sino"){
                                obtenerSiguienteToken()
                                var bloqueSentenciasA = esBloqueSentencias()
                                if(bloqueSentenciasA != null){
                                    return Decision(expresion, bloqueSentenciasS, bloqueSentenciasA)
                                }
                            } else{
                                return Decision(expresion, bloqueSentenciasS, null)
                            }
                            if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "\$o"){
                                obtenerSiguienteToken()
                                var bloqueSentenciasO = esBloqueSentencias()
                                if(bloqueSentenciasO != null){
                                    return Decision(expresion, bloqueSentenciasS, bloqueSentenciasO)
                                }
                            } else{
                                return Decision(expresion, bloqueSentenciasS, null)
                            }

                        }
                    } else{
                        reportarError("Hace falta el parentesis derecho")
                    }
                } else{
                    reportarError("Error en la condición")
                }
            } else{
                reportarError("Hace falta el parentesis izquierdo")
            }
        }
        return null
    }

    /*
    <Expresion> ::= <ExpresionLogica> | <ExpresionRelacional> | <ExpresionAritmetica>
     */
    fun esExpresion():Expresion?{

        var expresion:Expresion? = esExpresionLogica()
        if(expresion != null){
            return expresion
        }

        var pos=(posicionActual).toString()
        expresion = esExpresionRelacional()
        if(expresion != null){
            return expresion
        }

        if(esPosible){
            hacerBacktracking(pos)
            esPosible=false
        }

        expresion = esExpresionAritmetica()
        if(expresion != null){
            return expresion
        }
        return null
    }

    /*
       <ExpresionLogica> ::= OperadorLogico <ExpresionRelacional> | <ExpresionRelacional> OperadorLogico <ExpresionRelacional>
        */
    fun esExpresionLogica():ExpresionLogica?{
        if(tokenActual.categoria == Categoria.OPERADOR_LOGICO && tokenActual.lexema == "NOT"){
            var operador = tokenActual
            obtenerSiguienteToken()
            var expresionRelacional = esExpresionRelacional()
            if(expresionRelacional != null){
                return ExpresionLogica(null, operador, expresionRelacional)
            } else{
                reportarError("Hace falta la exoresión relacional")
            }
        } else{
            if(!esMetodo) {
                val expresionRelacional = esExpresionRelacional()
                if (expresionRelacional != null) {
                    if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                        if (tokenActual.lexema == "AND" || tokenActual.lexema == "OR") {
                            var operador = tokenActual
                            obtenerSiguienteToken()
                            var expRelacional = esExpresionRelacional()
                            if (expRelacional != null) {
                                return ExpresionLogica(expresionRelacional, operador, expRelacional)
                            } else {
                                reportarError("Hace falta la segunda expresión relacional")
                            }
                        }
                    } else {
                        reportarError("Hace falta el operador lógico")
                    }
                } else {
                    reportarError("Hace falta la expresión relacional")
                }
            }else{
                print("no lo esta tomando")
            }
        }
        return null
    }

    /*
    <ExpresionRelacional> ::= <ExpresionAritmetica> OperadorRelacional <ExpresionAritmetica> | ffn | tvs
    ¡pruebaM(4|M|3,2)..
     */
    fun esExpresionRelacional():ExpresionRelacional?{
        var expresionAritmetica = esExpresionAritmetica()
        if(expresionAritmetica != null){
            if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL){
                var operadorRelacional = tokenActual
                obtenerSiguienteToken()
                var expAritmetica = esExpresionAritmetica()
                if(expAritmetica != null){
                    return ExpresionRelacional(expresionAritmetica, operadorRelacional, expAritmetica)
                } else{
                    reportarError("Hace falta una segunda expresión aritmetica")
                }
            } else{
                if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                    esPosible =true
                    return null
                }else{
                    if(esMetodo){
                        esPosible =true
                        return null
                    }else{
                        reportarError("Hace falta un operador relacional")
                    }

                }

            }
        } else{
            if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
                if(tokenActual.lexema == "ffn" || tokenActual.lexema == "tvs"){
                    var operador = tokenActual
                    return ExpresionRelacional(null, operador, null)
                }
            } else{
                reportarError("Hace falta una expresión relacional")
            }
        }
        return null
    }

    /*
    <ExpresionAritmetica> ::= <ExpresionAritmetica>[ OperadorAritmetico <ExpresionAritmetica>]
     | <ValorNumerico>[OperadorAritmetico <ExpresionAritmetica>]
     */
    fun esExpresionAritmetica():ExpresionAritmetica?{
        var valorNumerico:ValorNumerico?
        if(!esMetodo && tokenActual.categoria == Categoria.SEPARADOR){
            return null
        }else{
            valorNumerico = esValorNumerico()
        }
        if(valorNumerico != null){
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                var operador = tokenActual
                obtenerSiguienteToken()
                var valorNum = esValorNumerico()
                if(valorNum != null){
                    return ExpresionAritmetica(valorNumerico, operador, valorNum)
                } else{
                    reportarError("Hace falta un valor numérico")
                }
            } else{
                return ExpresionAritmetica(valorNumerico, null, null)
            }
        } else{
            print("aqui es el error ${tokenActual.lexema}")
            reportarError("Hace falta un valor numérico")
        }
        return null
    }

    /*
        <ValorNumerico> ::= [<Signo>] decimal | [<Signo>] entero | [<Signo>] identificador
        <ValorNumerico> ::= [Signo] rls | [Signo] etr | [Signo] <NombreVariable>
         */
    fun esValorNumerico():ValorNumerico?{
        if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema == "*P*"  || tokenActual.lexema == "*M*")) {
            var signo = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.REAL || tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                var valor = tokenActual
                obtenerSiguienteToken()
                return ValorNumerico(valor,signo)
            }else{
                reportarError("No se a colocado un numero o identificador")
            }
        } else{
            if(tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.REAL || tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                var valor =tokenActual
                var signoVacio= Token("",Categoria.OPERADOR_ARITMETICO,-1,-1)
                obtenerSiguienteToken()
                return ValorNumerico(valor,signoVacio)
            }
        }
        return null
    }

    /*
        <Invocacion> ::= identificador ")" [<ListaArgumentos>] "(" "_”
         */
    fun esInvocacionFuncion():InvocacionFuncion?{
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_METODO) {
            val nombreFuncion = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.AGRUPADOR_METODO_ABRIR) {
                obtenerSiguienteToken()
                val argumentos: ArrayList<Expresion> = esListaArgumentos()
                if (tokenActual.categoria == Categoria.AGRUPADOR_METODO_CERRAR) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FINAL_SENTENCIA) {
                        obtenerSiguienteToken()
                        esMetodo=false
                        return InvocacionFuncion(nombreFuncion, argumentos)
                    } else {
                        reportarError("Falta fin de sentencia")
                    }
                } else {
                    reportarError("Falta agrupador derecho")
                }
            } else {
                reportarError("Falta agrupador izquierdo")
            }
        }
        return null
    }

    /*
    <ListaArgumentos> ::= <Expresion> ["," <ListaArgumentos>]
     */
    fun esListaArgumentos():ArrayList<Expresion>{
        var lista = ArrayList<Expresion>()
        var expresion = esExpresion()
        while(expresion != null){
            lista.add(expresion)
            if(tokenActual.categoria == Categoria.SEPARADOR){
                obtenerSiguienteToken()
                expresion = esExpresion()
            } else{
                expresion = null
            }
        }
        return lista
    }

    /*
    <Retorno> ::= ret <Expresion> “_”
     */
    fun esRetorno():Retorno?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "ret"){
            var retorno = tokenActual
            obtenerSiguienteToken()
            var expresion = esExpresion()
            if(expresion != null){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.FINAL_SENTENCIA){
                    var fin = tokenActual
                    obtenerSiguienteToken()
                    return Retorno(retorno, expresion, fin)
                } else{
                    reportarError("Hace falta el fin de sentencia al final del retorno")
                }
            } else{
                reportarError("Error en la expresion del retorno")
            }
        }
        return null
    }

    /*
    <Lista> ::= yarra <identificador> _<Tipo Dato>_ “=” "(" [Entero] ")"

     */
    fun esArreglo():Arreglo?{
       if(tokenActual.lexema =="yarra"){
           obtenerSiguienteToken()
           if (tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
               var nombre=tokenActual
               obtenerSiguienteToken()
               if (tokenActual.lexema == "etr"
                       || tokenActual.lexema == "rls" || tokenActual.lexema == "bbn" || tokenActual.lexema == "crt"|| tokenActual.lexema == "Pal"){
                   var tipo=tokenActual
                   obtenerSiguienteToken()
                   if(tokenActual.categoria==Categoria.OPERADOR_ASIGNACION){
                       obtenerSiguienteToken()
                       if(tokenActual.categoria==Categoria.AGRUPADOR_SEN_ABRIR){
                           obtenerSiguienteToken()
                           if(tokenActual.categoria==Categoria.ENTERO){
                               obtenerSiguienteToken()
                           }
                           if(tokenActual.categoria==Categoria.AGRUPADOR_SEN_CERRAR){
                               obtenerSiguienteToken()

                               return Arreglo(nombre,tipo)
                           }else{
                               reportarError("Hacen falta el agrupador cerrar sentencia")
                           }
                       }
                       else{
                           reportarError("Hacen falta el agrupador de abrir sentencia")
                       }
                   }
                   else{
                       reportarError("Hacen falta el operador de asignacion")
                   }

               }
               else{
                   reportarError("Hacen falta el tipo de dato")
               }
           }
           else{
               reportarError("Hacen falta el identificador de variable")
           }

       }else {
           reportarError("Hacen falta la palabra reservada para el arreglo")
       }
        return null
    }

    /*
    <DeclaracionVariables> ::= <TipoDeclaracion> <TipoDato> <ListaVariables> “..”
     */
    fun esDeclaracion():Declaracion?{
        var tipoDeclaracion = esTipoDeclaracion()
        if(tipoDeclaracion != null){
            obtenerSiguienteToken()
            var tipoDato = esTipoDato()
            if(tipoDato != null){
                obtenerSiguienteToken()
                var listaVariables = esListaVariables()
                if(listaVariables != null){
                    if(tokenActual.categoria == Categoria.FINAL_SENTENCIA && tokenActual.lexema == "_" ){
                        obtenerSiguienteToken()
                        return Declaracion(tipoDato, listaVariables)
                    } else{
                        reportarError("Hace falta el fin de sentencia declaracion")
                    }
                } else{
                    reportarError("Hacen falta las variables")
                }
            } else{
                reportarError("Hace falta el tipo de dato")
            }
        }
        return null
    }

    /*
    <TipoDeclaracion> ::= mut | const
     */
    fun esTipoDeclaracion():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "mut" || tokenActual.lexema == "const"){
                return  tokenActual
            }
        }
        return null
    }

    /*
    <ListaVariables> ::= <Variable> [<ListaVariables>]
     */
    fun esListaVariables():ArrayList<Variable>{
        var lista = ArrayList<Variable>()
        var variable = esNombreVariable()
        while(variable != null){
            lista.add(variable)
            variable = esNombreVariable()
        }
        return lista
    }

    /*
<ListaVariables> ::= <Variable> [<ListaVariables>]
 */
    fun esListaVariablesGlobales():ArrayList<VariableGlobal>{
        var lista = ArrayList<VariableGlobal>()
        var variable = esNombreVariableGlobal()
        while(variable != null){
            lista.add(variable)
            variable = esNombreVariableGlobal()
        }
        return lista
    }

    /*
    <Variable> ::= identificador ["I" <Expresion>]
     */
    fun esNombreVariable():Variable?{
        if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
            var nombreVariable = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                obtenerSiguienteToken()
                var expresion = esExpresion()
                if(expresion != null){
                    return Variable(nombreVariable, expresion)
                } else{
                    reportarError("Hace falta la expresión")
                }
            } else{
                return Variable(nombreVariable, null)
            }
        }
        return null
    }

    /*
<Variable> ::= identificador ["I" <Expresion>]
 */
    fun esNombreVariableGlobal():VariableGlobal?{
        if(tokenActual.categoria == Categoria.VARIABLE_GLOBAL){
            obtenerSiguienteToken()
            if(tokenActual.lexema == "etr"
                    || tokenActual.lexema == "rls" || tokenActual.lexema == "bbn"
                    || tokenActual.lexema == "crt"|| tokenActual.lexema == "Pal"){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                    var nombreVariable = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                        obtenerSiguienteToken()
                        var expresion = esExpresion()
                        if(expresion != null){
                            return VariableGlobal(nombreVariable, expresion)
                        } else{
                            reportarError("Hace falta la expresión")
                        }
                    } else{
                        return VariableGlobal(nombreVariable, null)
                    }
                }
            }

        }
        return null
    }

    /*
    <Impresion> ::= impp“<” <Expresion> “>” “_”
     */
    fun esImpresion():Impresion?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "impp"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.AGRUPADOR_IMPRIMIR_ABRIR){
                obtenerSiguienteToken()
                var expresion = esExpresion()
                if(expresion != null){
                    if(tokenActual.categoria == Categoria.AGRUPADOR_IMPRIMIR_CERRAR){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.FINAL_SENTENCIA){
                            obtenerSiguienteToken()
                            return Impresion(expresion)
                        } else{
                            reportarError("Hace falta el fin de sentencia")
                        }
                    } else{
                        reportarError("Hace falta el parentesis derecho")
                    }
                }
            } else{
                reportarError("Hace falta el parentesis izquierdo")
            }
        }
        return null
    }

    /*
    <Ciclos> ::= <CicloPour> | <CicloAlors> | <CicloFaire>
     */
    fun esCiclo():Ciclo?{

        var ciclo:Ciclo? = esCicloPour()
        if(ciclo != null){
            return ciclo
        }
        ciclo = esCicloAlors()
        if(ciclo != null){
            return ciclo
        }

        return null
    }

    /*
    <CicloPour> ::= fbw ”)“ <Condición> “(”  <BloqueSentencias>
     */
    fun esCicloPour():CicloFbw?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "fbw"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.AGRUPADOR_METODO_ABRIR){
                obtenerSiguienteToken()
                var cond = esCondicion()
                print("Condicion " + cond)
                if(cond != null){
                    if(tokenActual.categoria == Categoria.AGRUPADOR_METODO_CERRAR){
                        print("parentesis dere " + tokenActual)
                        obtenerSiguienteToken()
                        var bloqueSentencias = esBloqueSentencias()
                        if(bloqueSentencias != null){
                            obtenerSiguienteToken()
                            return CicloFbw(cond, bloqueSentencias)
                        }
                    } else{
                        reportarError("Hace falta el parentesis derecho")
                    }
                } else{
                    reportarError("Hace falta la condición")
                }
            }
        }
        return null
    }


    /*
    <CicloAlors> ::= eliw ”)“ <NombreVariable> <ExpresionRelacional> ”)” “{“ <Sentencia> <ExpresionIterador> “}”
     */
    fun esCicloAlors():CicloEliw?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "eliw"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.AGRUPADOR_METODO_ABRIR){
                obtenerSiguienteToken()
                var variable = esNombreVariable()
                if(variable != null){
                    var expresionRelacional = esExpresionRelacional()
                    if(expresionRelacional != null){
                        if(tokenActual.categoria == Categoria.AGRUPADOR_METODO_CERRAR){
                            obtenerSiguienteToken()
                            var bloqueSentencias = esBloqueSentenciasCiclo()
                            if(bloqueSentencias != null){
                                obtenerSiguienteToken()
                                return CicloEliw(expresionRelacional, bloqueSentencias)
                            }
                        } else{
                            reportarError("Hace falta el parentesis derecho")
                        }
                    } else{
                        reportarError("Hace falta la expresión relacional ciclo alors")
                    }
                } else{
                    reportarError("Hace falta la variable")
                }
            } else{
                reportarError("Hace falta el parentesis izquierdo")
            }
        }
        return null
    }

    /*
    <BloqueSentenciasCiclo> ::= "{" [<ListaSentencias>] <ExpresionIterador>"}"
     */
    fun esBloqueSentenciasCiclo():ArrayList<Sentencia>? {
        if(tokenActual.categoria == Categoria.AGRUPADOR_SEN_ABRIR){
            obtenerSiguienteToken()
            var listaSentencias = esListaSentencias()
            print("listasentencias " + listaSentencias)
            var expresionIterador = esExpresionIterador()
            if(expresionIterador != null){
                print("Iterador ciclo " + tokenActual)
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.AGRUPADOR_SEN_CERRAR){
                    obtenerSiguienteToken()
                    return listaSentencias
                } else{
                    reportarError("Falta la llave derecha del bloque de sentencias")
                }
            } else{
                reportarError("Hace falta el iterador al final del bloque de sentencias del ciclo")
            }
        } else{
            reportarError("Falta la llave izquierda del bloque de sentencias")
        }
        return null
    }

    /*
    <Condicion> ::= <NombreVariable> “,” <ExpresionLogica> “,” <NombreVariable> <ExpresionIterador>
     */
    fun esCondicion():Condicion?{
        var variable = esNombreVariable()
        if(variable != null){
            if(tokenActual.categoria == Categoria.SEPARADOR) {
                esMetodo=false
                obtenerSiguienteToken()
                var expresionLogica = esExpresionLogica()
                esMetodo=true
                if (expresionLogica != null) {
                    if (tokenActual.categoria == Categoria.SEPARADOR) {
                        obtenerSiguienteToken()
                        var variabl = esNombreVariable()
                        if (variabl != null) {
                            print("\n \n  este llega en el error antes ${tokenActual.lexema} \n \n ")
                            var expresionIterador = esExpresionIterador()
                            if (expresionIterador != null) {
                                obtenerSiguienteToken()
                                return Condicion(variable, expresionLogica, variabl, expresionIterador)
                            } else {
                                reportarError("Hace falta el iterador")
                            }
                        } else {
                            reportarError("Hace falta indicar el nombre de la variable")
                        }
                    } else {
                        reportarError("Hace falta la coma")
                    }
                } else {
                    reportarError("Hace falta una expresión lógica")
                }
            }
        } else{
            reportarError("No se encuentran variables")
        }
        return null
    }

    /*
    <Lectura> ::= lee <NombreIdentificador> “_”
     */
    fun esLectura():Lectura?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "lee"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                var nombreVariable = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.FINAL_SENTENCIA){
                    obtenerSiguienteToken()
                    return Lectura(nombreVariable)
                } else{
                    reportarError("Hace falta el fin de sentencia")
                }
            } else{
                reportarError("Hace falta el nombre dle identificador de la variable")
            }
        }
        return null
    }

    /*
    <ExpresionAsignacion> ::= Identificador OperadorAsignacion <Expresion> ".." | Identificador OperadorAsignacion <InvocacionFuncion> ".."
     */
    fun esExpresionAsignacion():Asignacion?{
        var posicion = posicionActual
        if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
            var identificador = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                if(tokenActual.lexema == "I" || tokenActual.lexema == "+I" || tokenActual.lexema == "-I" ||
                        tokenActual.lexema == "*I" || tokenActual.lexema == "/I"){
                    var operadorAsignacion = tokenActual
                    obtenerSiguienteToken()
                    var expresion = esExpresion()
                    if(expresion != null){
                        if(tokenActual.categoria == Categoria.FINAL_SENTENCIA){
                            obtenerSiguienteToken()
                            return Asignacion(identificador, operadorAsignacion, expresion)
                        } else{
                            reportarError("Hace falta el fin de sentencia en la expresión de asignación")
                        }
                    } else{
                        reportarError("Hace falta la expresión de asignación")
                    }
                }
            } else{
                reportarError("Hace falta el operador de asignación")
            }
        }
        return null
    }

    /*
    <ExpresionIterador> ::= <ExpresionIncremento> | <ExpresionDecremento>
    */
    fun esExpresionIterador():Iterador?{
        if(tokenActual.categoria == Categoria.OPERADOR_INCREMENTO || tokenActual.categoria == Categoria.OPERADOR_DECREMENTO){
            return Iterador(tokenActual)
        }
        return null
    }

    fun reportarError(mensaje:String){
        listaErrores.add(ErrorSintactico(mensaje, tokenActual.fila, tokenActual.columna))
    }

}