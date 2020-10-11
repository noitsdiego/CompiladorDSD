package co.edu.uniquindio.compiladores.logica.lexico

class AnalizadorLexico(var codigoFuente: String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    var posicionInicialLexema = 0
    var filaInicialLexema = 0
    var columnaInicialLexema = 0

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

    fun obtenerCaracterInicial() {

        posicionActual = posicionInicialLexema
        columnaActual = columnaInicialLexema
        filaActual = filaInicialLexema
        caracterActual = codigoFuente[posicionActual]
    }

    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) {
        listaTokens.add(Token(lexema, categoria, fila, columna))
    }

    fun hacerBT(filaInicial: Int, columnaInicial: Int, posicionInicial: Int) {
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    fun analizar() {

        while (caracterActual != finCodigo) {

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }

            if(esEntero()) continue
            if(esReal()) continue
            if(esIdentificadorVariable()) continue
            if(esIndentificadorClase()) continue
            if(esIdentificadorMetodo()) continue
            if(esPalabraReservada())continue

            almacenarToken("" + caracterActual, Categoria.NO_RECONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }

    }

    fun esEntero(): Boolean {

        if (caracterActual == 'Z') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
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

    fun esReal(): Boolean {

        if (caracterActual == 'R') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
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

                if(caracterActual.isDigit())
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.IDENTIFICADOR_VARIABLE, filaInicial, columnaInicial)
                    return true
                }else
                {
                    obtenerCaracterInicial()
                    return false
                }


            }else {
                obtenerCaracterInicial()
                return false
            }
        }
        else{
            return false
        }
    }

    fun esIndentificadorClase():Boolean{

        if (caracterActual == 'C') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

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
            }else {
                obtenerCaracterInicial()
                return false
            }
        }
        else{
            return false
        }

    }

    fun esIdentificadorMetodo(): Boolean{

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
            }else {
                obtenerCaracterInicial()
                return false
            }
        }
        else{
            return false
        }
    }

    fun esPalabraReservada(): Boolean{

        if (caracterActual.isLetter()) {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            posicionInicialLexema = posicionActual
            columnaInicialLexema = columnaActual
            filaInicialLexema = filaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (!caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }


            if (lexema == "etr" || lexema == "rls"|| lexema == "Pal" || lexema == "crt"
                    || lexema == "fbw" || lexema == "\$si\$" || lexema == "prin"|| lexema=="\$sino\$"
                    || lexema=="\$o\$"|| lexema=="+II" || lexema=="-II" || lexema=="bbn"
                    || lexema == "tvs" || lexema == "ffn" || lexema == "impp" ) {
                obtenerSiguienteCaracter()

                if (!caracterActual.isDigit() || !caracterActual.isLetter()) {

                    filaInicial = filaActual
                    columnaInicial = columnaActual
                    almacenarToken(lexema, Categoria.PAL_RESERVADA, filaInicial, columnaInicial)
                    return true
                }

            } else {
                obtenerCaracterInicial()
                return false

            }

        }
        return false


    }

}
