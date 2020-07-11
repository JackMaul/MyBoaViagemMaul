package com.example.myboaviagem_maul.util

class Formatar {


    companion object {

        fun formataPorcetagem(valor: Double): String {
            return String.format("%.2f", valor)
        }
    }
}