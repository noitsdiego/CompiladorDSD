package co.edu.uniquindio.compiladores.logica.sintactico
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
}