package co.edu.uniquindio.compiladores.logica.semantico

class Simbolo (){
    var nombre: String = ""
    var tipo: String = ""
    var modificable: Boolean = false
    var acceso:String=""
    var fila = 0
    var columna = 0
    var ambito: String?= ""
    var tipoParametros: ArrayList<String>? = null

    /**
     * Constructor para crear un simbolo de tipo valor
     */
    constructor(nombre:String, tipoDato:String , modificable:Boolean, ambito:String,acceso:String, fila:Int, columna:Int):this(){
        this.nombre=nombre
        this.tipo=tipoDato
        this.modificable=modificable
        this.ambito=ambito
        this.acceso=acceso
        this.fila=fila
        this.columna=columna
    }
    /**
     * Constructor para crear un simbolo de tipo Metodo(funcion)
     */
    constructor(nombre:String, tipoRetorno:String,tipoParametros:ArrayList<String>,ambito:String,acceso: String):this(){
        this.nombre=nombre
        this.tipo=tipoRetorno
        this.tipoParametros=tipoParametros
        this.ambito=ambito
        this.acceso=acceso
    }

    override fun toString(): String {

        return if(tipoParametros==null){
             "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$modificable,acceso=$acceso, fila=$fila, columna=$columna, ambito=$ambito)"

        }else{
             "Simbolo(nombre='$nombre', tipo='$tipo', ambito=$ambito, tipoParametros=$tipoParametros,acceso=$acceso)"

        }
    }

}