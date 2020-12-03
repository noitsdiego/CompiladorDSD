package co.edu.uniquindio.compiladores.interfaz.controlador

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.sintactico.AnalizadorSintactico
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantico.AnalizadorSemantico
import co.edu.uniquindio.compiladores.logica.sintactico.ErrorSintactico
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.TreeView
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

/*
    @Autor: Diego riveros, Stefanny roman y Daniel Loaiza
 */
class InicioController: Initializable {

    @FXML lateinit var codigoFuente: TextArea
    @FXML lateinit var tablaTokens: TableView<Token>
    @FXML lateinit var tablaErroresLexico: TableView<Error>
    @FXML lateinit var tablaErroresSintactico: TableView<ErrorSintactico>
    @FXML lateinit var colLexema: TableColumn<Token, String>
    @FXML lateinit var colCategoria: TableColumn<Token, String>
    @FXML lateinit var colFila: TableColumn<Token, String>
    @FXML lateinit var colColumna: TableColumn<Token, String>
    @FXML lateinit var colMensajeErrorLexico: TableColumn<Error, String>
    @FXML lateinit var colFilaErrorLexico: TableColumn<Error, String>
    @FXML lateinit var colColumnaErrorLexico: TableColumn<Error, String>
    @FXML lateinit var colMensajeErrorSintaxis: TableColumn<ErrorSintactico, String>
    @FXML lateinit var colFilaErrorSintaxis: TableColumn<ErrorSintactico, String>
    @FXML lateinit var colColumnaErrorSintaxis: TableColumn<ErrorSintactico, String>
    @FXML lateinit var arbolVisual:TreeView<String>


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        colLexema.cellValueFactory = PropertyValueFactory("lexema")
        colCategoria.cellValueFactory = PropertyValueFactory("categoria")
        colFila.cellValueFactory = PropertyValueFactory("fila")
        colColumna.cellValueFactory = PropertyValueFactory("columna")

        colMensajeErrorLexico.cellValueFactory = PropertyValueFactory("error")
        colFilaErrorLexico.cellValueFactory = PropertyValueFactory("fila")
        colColumnaErrorLexico.cellValueFactory = PropertyValueFactory("columna")

        colMensajeErrorSintaxis.cellValueFactory = PropertyValueFactory("mensaje")
        colFilaErrorSintaxis.cellValueFactory = PropertyValueFactory("fila")
        colColumnaErrorSintaxis.cellValueFactory = PropertyValueFactory("columna")

    }

    fun AnalizarCodigo ( e: ActionEvent) {

        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
            tablaErroresLexico.items = FXCollections.observableArrayList(lexico.listaErrores)

            if(lexico.listaErrores.isEmpty())
            {
                val sintaxis = AnalizadorSintactico(lexico.listaTokens)
                val uc = sintaxis.esUnidadDeCompilacion()
                tablaErroresSintactico.items = FXCollections.observableArrayList(sintaxis.listaErrores)

                if (uc != null) {
                    arbolVisual.root = uc.getArbolVisual()

                    val semantica=AnalizadorSemantico(uc!!)
                    semantica.llenarTablaSimbolos()
                    print(semantica.tablaSimbolos)

                }
            }
        }
    }
}