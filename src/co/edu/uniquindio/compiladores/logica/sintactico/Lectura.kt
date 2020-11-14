package co.edu.uniquindio.compiladores.logica.sintactico
import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Lectura (var nombreVariable:Token):Sentencia() {

    override fun toString(): String {
        return "Lectura(nombreVariable=$nombreVariable)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Lectura")
        raiz.children.add(TreeItem(" ${nombreVariable.lexema}"))
        return  raiz
    }
}