package co.edu.uniquindio.compiladores.interfaz.app

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.logica.sintactico.AnalizadorSintactico
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/*
    @Autor: Diego riveros, Stefanny roman y Daniel Loaiza
 */
class Aplicacion : Application() {

    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(Aplicacion::class.java.getResource("/Inicio.fxml"))
        val parent: Parent = loader.load()

        val scene = Scene(parent)

        primaryStage?.scene = scene
        primaryStage?.title = "Compilador DSD"
        primaryStage?.show()
    }

    /*
    ejemplo 1      fun Mhola etr )( !¡
    ejemplo 2      $variable1|hola_
    ejemplo 3 + rls $variable1
     */

    companion object{
        @JvmStatic
        fun main( args: Array<String>){
            launch(Aplicacion::class.java)
            val lexico=AnalizadorLexico("fun Mhola etr )( !¡")
            lexico.analizar()
            val sintactico=AnalizadorSintactico( lexico.listaTokens)
            print(sintactico.esUnidadDeCompilacion())
        }

    }

}