package co.edu.uniquindio.compiladores.logica.lexico

/*
Clase enumeracion con las categorias del analizador lexico
@autor Diego riveros, Stefanny Roman & Daniel Loaiza
 */
enum class Categoria {

    IDENTIFICADOR_VARIABLE, IDENTIFICADOR_CLASE, IDENTIFICADOR_METODO, PALABRA_RESERVADA, ENTERO, REAL, OPERADOR_ARITMETICO,
    OPERADOR_LOGICO, OPERADOR_ASIGNACION, OPERADOR_INCREMENTO, OPERADOR_DECREMENTO, OPERADOR_RELACIONAL,
    AGRUPADOR_DERECHO, AGRUPADOR_IZQUIERDO, FINAL_SENTENCIA, SEPARADOR,COMENTARIO_LINEA, COMENTARIO_BLOQUE, FINAL_CODIGO, CADENA,
    CARACTER_SINGULAR, NO_IDENTIFICADO

}