package co.edu.uniquindio.compiladores.logica.semantico


import co.edu.uniquindio.compiladores.logica.sintactico.UnidadDeCompilacion


/**
 * fun entero metodo ( decimal numero, entero numero) {}
 *
 */
class AnalizadorSemantico(var uc: UnidadDeCompilacion) {

    var erroresSemanticos: ArrayList<ErrorSemantico> = ArrayList()
    var tablaSimbolos: TablaSimbolos = TablaSimbolos(erroresSemanticos)

    fun llenarTablaSimbolos() {
        uc.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos)
    }

    fun analizarSemantica() {
        uc.analizarSemantica(tablaSimbolos, erroresSemanticos)
    }



}