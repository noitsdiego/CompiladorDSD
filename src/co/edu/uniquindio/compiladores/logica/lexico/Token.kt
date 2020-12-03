package co.edu.uniquindio.compiladores.logica.lexico

import co.edu.uniquindio.compiladores.logica.lexico.Categoria

/*
Clase para reconocer los tokens del analizador lexico
@autor Diego riveros, Stefanny Roman & Daniel Loaiza
 */
class Token(var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int){

    /*
    To String
     */
    override fun toString(): String {
        return "Token ( lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna )"
    }

    fun getJavaCode():String{
        if(categoria==Categoria.PALABRA_RESERVADA){
            when(lexema){
                "entero" -> {return "int"}
                "decimal" -> {return "double"}
                "log" -> {return "boolean"}
                "si" -> {return "if"}
                "imprimir" -> {return "JOptionPane.showMessageDialog(null,"}
                "retorno" -> {return "return"}
                "vacio" -> {return "void"}
                "verdadero" -> {return "true"}
                "falso" -> {return "false"}
                "mientras" -> {return "while"}
                "cadena" -> {return "String"}
            }
        }else if(categoria==Categoria.FINAL_SENTENCIA){
            return ";"
        }else if(categoria==Categoria.CADENA){
            lexema.replace("«", "\"")
            lexema.replace("»", "\"")
            return lexema;
        }else if(categoria==Categoria.CARACTER_SINGULAR){
            lexema.replace("_", "\"")
            return lexema
        }
        return "INDEFINIDO"
    }
}

