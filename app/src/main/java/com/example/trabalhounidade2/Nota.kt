package com.example.trabalhounidade2

data class Nota (var id: Long) {
    var titulo: String = ""
    var texto: String = ""

    override fun toString(): String {
        return "Nota(titulo='$titulo')"
    }

}