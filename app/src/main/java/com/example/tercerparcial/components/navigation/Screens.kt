package com.example.tercerparcial.components.navigation

import kotlinx.serialization.Serializable

interface ScreenDestination

@Serializable
object pacienteScreen : ScreenDestination

@Serializable
object especialidadScreen : ScreenDestination

@Serializable
object medicosScreen: ScreenDestination

@Serializable
object consultasScreen: ScreenDestination
@Serializable
object reportesScreen: ScreenDestination