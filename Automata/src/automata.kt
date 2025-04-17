fun main() {
    print("Ingresa una cadena a validar: ")
    val cadena = readLine()?.trim() ?: ""

    val resultado = validarCadena(cadena)
    println("Cadena \"$cadena\" -> ${if (resultado) "VÁLIDA ✅" else "INVÁLIDA ❌"}")
}

fun validarCadena(cadena: String): Boolean {
    var estado = "q0"
    println("Estado inicial: $estado")

    for ((indice, caracter) in cadena.withIndex()) {
        val nuevoEstado = transicion(estado, caracter)

        if (nuevoEstado == null) {
            println("Entrada inválida: '$caracter' en posición ${indice + 1}, no hay transición desde $estado")
            return false
        }

        println("Caracter '$caracter' → transita de $estado a $nuevoEstado")
        estado = nuevoEstado
    }   

    println("Estado final: $estado")
    return estado == "q2"
}

fun transicion(estado: String, simbolo: Char): String? {
    return when (estado) {
        "q0" -> when (simbolo) {
            'a' -> "q1"
            'b' -> "q3"
            else -> null
        }
        "q1" -> when (simbolo) {
            'a' -> "q1"
            'b' -> "q2"
            else -> null
        }
        "q2" -> when (simbolo) {
            'a', 'b' -> "q3"
            else -> null
        }
        "q3" -> when (simbolo) {
            'a', 'b' -> "q3"
            else -> null
        }
        else -> null
    }
}
