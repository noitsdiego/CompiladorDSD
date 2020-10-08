package co.edu.uniquindio.compiladores.logica.lexico

class AnalizadorLexico(var codigoFuente: String)  {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

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

            if (esEntero()) continue
            if(esReal()) continue

            almacenarToken("" + caracterActual, Categoria.NO_RECONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }

    }

    fun esEntero(): Boolean {

        if(caracterActual == 'Z')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
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

        if(caracterActual=='R')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual =='R')
            {
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
                    }
                    else {

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
                }
                return false
        }
            return false
        }
        return false
        }
    }