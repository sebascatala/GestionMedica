package com.example.tercerparcial.Entidades

class Medico(
    var codigoMedico: Int=0,
    var nombre: String,
    var celular: Int,
    var direccion: String,
    var codigoEspecialidad:Int

) {
    override fun toString(): String {
        return nombre
    }
}