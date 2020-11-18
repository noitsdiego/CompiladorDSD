package co.edu.uniquindio.compiladores.logica.lexico

/*
Clase que realiza el analisis lexico de un codigo
@autor Diego riveros, Stefanny Roman & Daniel Loaiza
 */
class AnalizadorLexico(var codigoFuente: String) {

    //atributos
    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    var posicionInicialLexema = 0
    var filaInicialLexema = 0
    var columnaInicialLexema = 0

    /*
    Obtiene el siguiente caracter
     */
    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }

    fun verificarFinalCodigo(): Boolean
    {
        if (posicionActual == codigoFuente.length - 1) {
            return true
        } else {
           return false
        }
    }


    /*
    Obtiene el caracter inicial de un lexema que estaba siendo analizado
     */
    fun obtenerCaracterInicial() {

        posicionActual = posicionInicialLexema
        columnaActual = columnaInicialLexema
        filaActual = filaInicialLexema
        caracterActual = codigoFuente[posicionActual]
    }

    /*
    Almacena los tokens
     */
    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) {
        listaTokens.add(Token(lexema, categoria, fila, columna))
    }

    /*
    Verifica el BT
     */
    fun hacerBT(filaInicial: Int, columnaInicial: Int, posicionInicial: Int) {
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    /*
    Analiza el codigo ingresado
     */
    fun analizar() {

        while (caracterActual != finCodigo) {

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }

            if (esEntero()) continue
            if (esReal()) continue
            if (esCadena()) continue
            if (esCaracter()) continue
            if (esPalabraReservada()) continue
            if (esIdentificadorVariable()) continue
            if (esIndentificadorClase()) continue
            if (esIdentificadorMetodo()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorLogico()) continue
            if (esOperadorAsignacion()) continue
            if (esOperadorRelacional()) continue
            if (esAgrupadorAbrirMetodo()) continue
            if (esAgrupadorCerrarMetodo()) continue
            if (esAgrupadorAbrirSentencia()) continue
            if (esAgrupadorCerrarSentencia()) continue
            if (esAgrupadorAbrirImprimir()) continue
            if (esAgrupadorCerrarImprimir()) continue
            if (esTerminal()) continue
            if (esSeparador()) continue
            if (esComentarioLinea()) continue
            if (esComentarioBloque()) continue
            if (esFinDeCodigo()) continue


            almacenarToken("" + caracterActual, Categoria.NO_IDENTIFICADO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }

    }

    /*
    Verifica si el token es un entero
     */
    fun esEntero(): Boolean {

        if (caracterActual == 'Z') {
            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            val posicionInicial = posicionActual
            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isDigit() || caracterActual == '.') {
                    lexema += caracterActual
                    if (caracterActual == '.') {
                        hacerBT(filaInicial, columnaInicial, posicionInicial)
                        return false
                    }
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                return true
            }
        }

        return false
    }

    /*
    Verifica si el token es un real
    */
    fun esReal(): Boolean {

        if (caracterActual == 'R') {
            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            val posicionInicial = posicionActual
            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'R') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '.' || caracterActual.isDigit()) {
                    if (caracterActual == '.') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if (caracterActual.isDigit()) {

                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                        } else {
                            hacerBT(filaInicial, columnaInicial, posicionInicial)
                            return false
                        }
                    } else {

                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        while (caracterActual.isDigit()) {

                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }

                        if (caracterActual == '.') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                    }

                    while (caracterActual.isDigit()) {

                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if (caracterActual == '.') {
                        hacerBT(filaInicial, columnaInicial, posicionInicial)
                        return false

                    }
                    almacenarToken(
                            lexema,
                            Categoria.REAL, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    obtenerCaracterInicial()
                    return false
                }

            } else {
                obtenerCaracterInicial()
                return false
            }

        }
        return false
    }

    /*
     Verifica si el token es una variable
    */
    fun esIdentificadorVariable(): Boolean {

        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isLetter()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isLetter()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

                if (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.IDENTIFICADOR_VARIABLE, filaInicial, columnaInicial)
                    return true
                } else {
                    obtenerCaracterInicial()
                    return false
                }


            } else {
                obtenerCaracterInicial()
                return false
            }
        } else {
            return false
        }
    }

    /*
 Verifica si el token es un identificador de clase
    */
    fun esIndentificadorClase(): Boolean {

        if (caracterActual == 'C') {
            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if ((caracterActual.isLetter() && !caracterActual.isLowerCase()) || caracterActual.isDigit()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isLetter() || caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.IDENTIFICADOR_CLASE, filaInicial, columnaInicial)
                return true
            } else {
                obtenerCaracterInicial()
                return false
            }
        } else {
            return false
        }

    }

    /*
Verifica si el token es un identificador de metodo
*/
    fun esIdentificadorMetodo(): Boolean {

        if (caracterActual == 'M') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if ((caracterActual.isLetter() && caracterActual.isLowerCase()) || caracterActual.isDigit()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isLetter() || caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.IDENTIFICADOR_METODO, filaInicial, columnaInicial)
                return true
            } else {
                obtenerCaracterInicial()
                return false
            }
        } else {
            return false
        }
    }

    /*
Verifica si el token es una palabra reservada
*/
    fun esPalabraReservada(): Boolean {

        if (caracterActual.isLetter() || caracterActual == '\$' || caracterActual == '+' || caracterActual == '-') {

            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetter()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()

            }

            if (lexema == "+II") {
                almacenarToken(lexema, Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial)
                return true
            } else if (lexema == "-II") {
                almacenarToken(lexema, Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial)
                return true
            } else if (lexema == "etr" || lexema == "rls" || lexema == "Pal" || lexema == "crt"
                    || lexema == "fbw" || lexema == "\$si" || lexema == "prin" || lexema == "\$sino"
                    || lexema == "\$o" || lexema == "bbn" || lexema == "tvs" || lexema == "ffn"
                    || lexema == "impp" || lexema == "eliw" || lexema == "void") {

                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true

            } else if (lexema == "fun") {
                almacenarToken(lexema, Categoria.FUNCION, filaInicial, columnaInicial)
                return true
            }else if (lexema == "mut") {
                almacenarToken(lexema, Categoria.VARIABLE_MUTABLE, filaInicial, columnaInicial)
                return true
            }else if (lexema == "const") {
                almacenarToken(lexema, Categoria.VARIABLE_CONSTANTE, filaInicial, columnaInicial)
                return true
            }else if (lexema == "ret") {
                almacenarToken(lexema, Categoria.RETORNO, filaInicial, columnaInicial)
                return true
            }else if (lexema == "yarra") {
                almacenarToken(lexema, Categoria.ARREGLO, filaInicial, columnaInicial)
                return true
            }else if (lexema == "carac") {
                almacenarToken(lexema, Categoria.PAL_CONCATENACION, filaInicial, columnaInicial)
                return true
            }else if (lexema == "slac") {
                almacenarToken(lexema, Categoria.PAL_RESERVADA_CLASE, filaInicial, columnaInicial)
                return true
            }else if (lexema == "priv") {
                almacenarToken(lexema, Categoria.PAL_VISIBILIDAD_PRIVADA, filaInicial, columnaInicial)
                return true
            }else if (lexema == "pub") {
                almacenarToken(lexema, Categoria.PAL_VISIBILIDAD_PUBLICA, filaInicial, columnaInicial)
                return true
            }else if (lexema == "global") {
                almacenarToken(lexema, Categoria.VARIABLE_GLOBAL, filaInicial, columnaInicial)
                return true
            } else {
                obtenerCaracterInicial()
                return false

            }

        }
        return false


    }

    /*
Verifica si el token es un operador aritmetico
*/
    fun esOperadorAritmetico(): Boolean {

        if (caracterActual == '+') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'I') {
                hacerBT(filaInicial, columnaInicial, posicionInicial)
                return false

            } else {
                almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                )
                return true
            }

        } else if (caracterActual == '-') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'I') {
                hacerBT(filaInicial, columnaInicial, posicionInicial)
                return false

            } else {
                almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                )
                return true
            }
        } else if (caracterActual == '/') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'I') {
                hacerBT(filaInicial, columnaInicial, posicionInicial)
                return false

            } else {
                almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                )
                return true
            }
        } else if (caracterActual == '*' || caracterActual == '%') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                hacerBT(filaInicial, columnaInicial, posicionInicial)
                return false

            } else {
                almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                )
                return true
            }
        }
        return false
    }

    /*
Verifica si el token es un operador logico
*/
    fun esOperadorLogico(): Boolean {

        if (caracterActual.isLetter() && caracterActual.isUpperCase()) {

            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetter() && caracterActual.isUpperCase()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }


            if (lexema == "AND" || lexema == "OR" || lexema == "NOT") {

                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                return true


            } else {
                obtenerCaracterInicial()
                return false

            }

        }
        return false

    }

    /*
Verifica si el token es un operador de asignacion
*/
    fun esOperadorAsignacion(): Boolean {

        if (caracterActual == 'I' || caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual

            if (caracterActual == 'I') {
                obtenerSiguienteCaracter()
                if (caracterActual == 'I') {
                    hacerBT(filaInicial, columnaInicial, posicionInicial)
                    return false
                } else {

                    filaInicial = filaActual
                    columnaInicial = columnaActual
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
                    return true
                }
            }
            obtenerSiguienteCaracter()
            if (caracterActual == 'I') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                filaInicial = filaActual
                columnaInicial = columnaActual

                almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
                return true
            }
            hacerBT(filaInicial, columnaInicial, posicionInicial)
            return false
        }
        return false
    }

    /*
Verifica si el token es un operador relacional
*/
    fun esOperadorRelacional(): Boolean {

        if (caracterActual.isLetter() && caracterActual.isUpperCase()) {

            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetter() && caracterActual.isUpperCase()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }


            if (lexema == "MEN" || lexema == "MAY" || lexema == "DIF"
                    || lexema == "MENI" || lexema == "MAYI" || lexema == "DIFI"
                    || lexema == "II") {

                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true


            } else {
                obtenerCaracterInicial()
                return false

            }

        }
        return false


    }

    /*
Verifica si el token es un agrupador metodo de apertura
*/
    fun esAgrupadorAbrirMetodo(): Boolean {
        if (caracterActual == ')') {
            val filaInicio = filaActual
            val columnaInicio = columnaActual
            var lexema = ""

            lexema += caracterActual
            almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR_METODO_ABRIR, filaInicio, columnaInicio
            )
            obtenerSiguienteCaracter()
            return true
        }
        return false

    }

    /*
Verifica si el token es un agrupador metodo de cierre
*/
    fun esAgrupadorCerrarMetodo(): Boolean {
        if (caracterActual == '(') {
            val filaInicio = filaActual
            val columnaInicio = columnaActual
            var lexema = ""

            lexema += caracterActual
            almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR_METODO_CERRAR, filaInicio, columnaInicio
            )
            obtenerSiguienteCaracter()
            return true
        }
        return false

    }
    /*
    Verifica si el token es un agrupador sentencia de apertura
*/
    fun esAgrupadorAbrirSentencia(): Boolean {
        if (caracterActual == '!') {
            val filaInicio = filaActual
            val columnaInicio = columnaActual
            var lexema = ""

            lexema += caracterActual
            almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR_SEN_ABRIR, filaInicio, columnaInicio
            )
            obtenerSiguienteCaracter()
            return true
        }
        return false

    }

    /*
Verifica si el token es un agrupador sentencia de cierre
*/
    fun esAgrupadorCerrarSentencia(): Boolean {
        if (caracterActual == '¡') {
            val filaInicio = filaActual
            val columnaInicio = columnaActual
            var lexema = ""

            lexema += caracterActual
            almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR_SEN_CERRAR, filaInicio, columnaInicio
            )
            obtenerSiguienteCaracter()
            return true
        }
        return false

    }

   /*
    Verifica si el token es un agrupador imprimir de apertura
*/
    fun esAgrupadorAbrirImprimir(): Boolean {
        if (caracterActual == '>') {
            val filaInicio = filaActual
            val columnaInicio = columnaActual
            var lexema = ""

            lexema += caracterActual
            almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR_IMPRIMIR_ABRIR, filaInicio, columnaInicio
            )
            obtenerSiguienteCaracter()
            return true
        }
        return false

    }

    /*
Verifica si el token es un agrupador imprimir de cierre
*/
    fun esAgrupadorCerrarImprimir(): Boolean {
        if (caracterActual == '<') {
            val filaInicio = filaActual
            val columnaInicio = columnaActual
            var lexema = ""

            lexema += caracterActual
            almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR_IMPRIMIR_CERRAR, filaInicio, columnaInicio
            )
            obtenerSiguienteCaracter()
            return true
        }
        return false

    }

    /*
Verifica si el token es terminal
*/
    fun esTerminal(): Boolean {
        if (caracterActual == '_') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                    lexema,
                    Categoria.FINAL_SENTENCIA, filaInicial, columnaInicial
            )
            return true
        }

        return false

    }

    /*
Verifica si el token es un separador
*/
    fun esSeparador(): Boolean {
        if (caracterActual != '|') {
            return false
        }
        val posInicio = posicionActual
        val filaInicio = filaActual
        val columnaInicio = columnaActual
        var lexema = ""
        lexema += caracterActual
        listaTokens.add(
                Token(
                        lexema,
                        Categoria.SEPARADOR,
                        filaInicio,
                        columnaInicio
                )
        )
        obtenerSiguienteCaracter()
        return true
    }

    /*
Verifica si el token es un comentario de linea
*/
    fun esComentarioLinea(): Boolean {

        if (caracterActual == '@') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '_' && !verificarFinalCodigo()) {


                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if(verificarFinalCodigo())
            {
                if(caracterActual == '_')
                {
                    lexema += caracterActual
                    listaTokens.add(
                            Token(
                                    lexema,
                                    Categoria.COMENTARIO_LINEA,
                                    filaInicial,
                                    columnaInicial
                            )
                    )
                    obtenerSiguienteCaracter()
                    return true
                }
                obtenerCaracterInicial()
                return false
            }else
            {
                lexema += caracterActual
                listaTokens.add(
                        Token(
                                lexema,
                                Categoria.COMENTARIO_LINEA,
                                filaInicial,
                                columnaInicial
                        )
                )
                obtenerSiguienteCaracter()
                return true
            }



        }
        return false
    }

    /*
Verifica si el token es un comentario de bloque
*/
    fun esComentarioBloque(): Boolean {

        if (caracterActual == '°') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '°' && !verificarFinalCodigo()) {


                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if(verificarFinalCodigo())
            {
                if(caracterActual == '°')
                {
                    lexema += caracterActual
                    listaTokens.add(
                            Token(
                                    lexema,
                                    Categoria.COMENTARIO_BLOQUE,
                                    filaInicial,
                                    columnaInicial
                            )
                    )
                    obtenerSiguienteCaracter()
                    return true
                }
                obtenerCaracterInicial()
                return false
            }else
            {
                lexema += caracterActual
                listaTokens.add(
                        Token(
                                lexema,
                                Categoria.COMENTARIO_BLOQUE,
                                filaInicial,
                                columnaInicial
                        )
                )
                obtenerSiguienteCaracter()
                return true
            }

        }
        return false

    }

    /*
Verifica si el token es el caracter de fin de codigo
*/
    fun esFinDeCodigo(): Boolean {

        if (caracterActual == '¬') {

            var lexema = ""
            val posInicio = posicionActual
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            lexema += caracterActual



            almacenarToken(
                    lexema,
                    Categoria.FINAL_CODIGO, filaInicial, columnaInicial
            )
            obtenerSiguienteCaracter()
            return true
        }

        return false


    }

    /*
Verifica si el token es una cadena
    */
    fun esCadena(): Boolean {

        if (caracterActual == '«') {
            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '»' && !verificarFinalCodigo()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if(verificarFinalCodigo())
            {
                if(caracterActual == '»')
                {
                    lexema += caracterActual
                    listaTokens.add(
                            Token(
                                    lexema,
                                    Categoria.CADENA,
                                    filaInicial,
                                    columnaInicial
                            )
                    )
                    obtenerSiguienteCaracter()
                    return true
                }
                obtenerCaracterInicial()
                return false
            }else
            {
                lexema += caracterActual
                listaTokens.add(
                        Token(
                                lexema,
                                Categoria.CADENA,
                                filaInicial,
                                columnaInicial
                        )
                )
                obtenerSiguienteCaracter()
                return true
            }


        }

        return false
    }

    /*
Verifica si el token es un caracter
*/
    fun esCaracter(): Boolean {

        if (caracterActual == '<') {
            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '>') {
                lexema += caracterActual
                listaTokens.add(
                        Token(
                                lexema,
                                Categoria.CARACTER_SINGULAR,
                                filaInicial,
                                columnaInicial
                        )
                )
                obtenerSiguienteCaracter()
                return true
            } else {
                obtenerCaracterInicial()
                return false
            }
        }

        return false
    }

}