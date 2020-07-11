package com.example.myboaviagem_maul.model
import java.io.Serializable

data class Gasto(
    var idGasto: String, var valor: Double,
    var data: String, var descicao : String,
    var local : String, var tipoGasto : String
) : Serializable {

    constructor(): this("", 0.0, "", "", "", "")
}