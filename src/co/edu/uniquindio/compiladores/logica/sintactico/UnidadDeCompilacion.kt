package co.edu.uniquindio.compiladores.logica.sintactico
import co.edu.uniquindio.compiladores.logica.semantico.TablaSimbolos
import co.edu.uniquindio.compiladores.logica.semantico.Simbolo
import co.edu.uniquindio.compiladores.logica.semantico.ErrorSemantico
import javafx.scene.control.TreeItem

class UnidadDeCompilacion (var listaFunciones:ArrayList<Funcion>, var listaGlobales:ArrayList<VariableGlobal>) {

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones, listaVariablesGlobales= $listaGlobales)"
    }
    fun getArbolVisual():TreeItem<String>{

        var raiz= TreeItem<String>("Unidad de Compilación")

        for (f in listaGlobales){
            raiz.children.add(f.getArbolVisual())
        }

        for (f in listaFunciones){
            raiz.children.add(f.getArbolVisual())
        }

        return  raiz

    }
    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>) {
        for (f in listaFunciones) {
            var ambitoFuncion : Simbolo = Simbolo("UnidadCompilacion",null,false,null,null,0,0)

            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos,ambitoFuncion )
        }
    }
    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<ErrorSemantico>) {
        for (f in listaFunciones) {
            f.analizarSemantica(tablaSimbolos, erroresSemanticos)
        }
    }

    fun getJavaCode(): String {
        var codigo = "import javax.swing.*;\n public class Principal{\n"
        for (funcion in listaFunciones) {
            codigo += funcion.getJavaCode()
        }
        codigo += "\n}"
        return codigo
    }
}