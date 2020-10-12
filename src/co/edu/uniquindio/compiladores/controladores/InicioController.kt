package co.edu.uniquindio.compiladores.controladores

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextField


class InicioController{

    @FXML lateinit var cajon1: TextField
    @FXML lateinit var cajon2: TextField
    @FXML
    fun cambiarValores ( e: ActionEvent){
        val aux= cajon1.text
        cajon1.text = cajon2.text
        cajon2.text = aux
    }
}