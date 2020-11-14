package co.edu.uniquindio.compiladores.logica.sintactico

import javafx.scene.control.TreeItem

open class Sentencia {

    //<Sentencia> ::=  <Decision> | <DeclaracionVariable> | <Impresion> | <Ciclo> |<Asignacion> |
    // | <Lectura> | <InvocacionFuncion> | <Arreglo> | <Retorno> | <IncrementoDecremento>


    var decision: Decision? = null
    var asignacion: Asignacion? = null
    var incrementoDecremento: IncrementoDecremento?= null
    var declaracion: Declaracion? = null
    var impresion: Impresion? = null
    var ciclo: Ciclo? = null
    var lectura: Lectura? = null
    var invocacionFuncion: InvocacionFuncion? = null
    var arreglo: Arreglo? = null
    var retorno: Retorno? = null

    override fun toString(): String {

        if (decision != null) {
            return "Sentencia =(${decision})"
        }
        if (asignacion != null) {
            return "Sentencia =(${asignacion})"
        }
        if (arreglo != null) {
            return "Sentencia =(${arreglo})"
        }
        if (incrementoDecremento != null) {
            return "Sentencia =(${incrementoDecremento})"
        }
        if (declaracion != null) {
            return "Sentencia =(${declaracion})"
        }
        if (impresion != null) {
            return "Sentencia =(${impresion})"
        }
        if (ciclo != null) {
            return "Sentencia =(${ciclo})"
        }
        if (lectura != null) {
            return "Sentencia =(${lectura})"

        }
        if(invocacionFuncion != null) {
            return "Sentencia =(${invocacionFuncion})"
        }
        if (retorno != null) {
            return "Sentencia =(${retorno})"
        }
        return ""
    }

    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }

    constructor(decision: Decision) {
        this.decision = decision
    }
    constructor(asignacion: Asignacion) {
        this.asignacion = asignacion
    }
    constructor(incrementoDecremento: IncrementoDecremento) {
        this.incrementoDecremento = incrementoDecremento
    }
    constructor(declaracion: Declaracion) {
        this.declaracion = declaracion
    }
    constructor(impresion: Impresion) {
        this.impresion = impresion
    }
    constructor(ciclo: Ciclo) {
        this.ciclo = ciclo
    }
    constructor(lectura: Lectura) {
        this.lectura = lectura
    }
    constructor(invocacionFuncion: InvocacionFuncion) {
        this.invocacionFuncion = invocacionFuncion
    }
    constructor(arreglo: Arreglo) {
        this.arreglo = arreglo
    }
    constructor(retorno: Retorno) {
        this.retorno = retorno
    }
    constructor()
}