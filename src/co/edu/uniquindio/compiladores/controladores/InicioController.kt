package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextArea



class InicioController{

    @FXML lateinit var codigoFuente: TextArea

    fun AnalizarCodigo ( e: ActionEvent){

        if(codigoFuente.text.length>0){

            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()

            print(lexico.listaTokens)
        }

    }
}