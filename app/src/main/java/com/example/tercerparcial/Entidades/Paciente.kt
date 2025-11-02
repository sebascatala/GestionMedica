package com.example.tercerparcial.Entidades

data class Paciente (
    var codigo: Int = 0,
    var nombre: String,
    var edad: Int,
    var carnet: Int,
    var telefono: Int,
    var genero: String
){
    override fun toString(): String {
        return nombre
    }
}