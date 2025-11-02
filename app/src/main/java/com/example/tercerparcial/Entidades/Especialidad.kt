package com.example.tercerparcial.Entidades

class Especialidad(
    val codigo: Int=0,
    val nombre: String,
    val costo: Double,
) {
    override fun toString(): String {
        return nombre
    }
}