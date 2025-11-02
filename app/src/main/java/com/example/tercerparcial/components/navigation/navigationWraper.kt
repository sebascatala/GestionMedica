package com.example.tercerparcial.components.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tercerparcial.components.MyNavigationBar
import com.example.tercerparcial.components.MyTopAppBar
import com.example.tercerparcial.screens.GestionConsultas
import com.example.tercerparcial.screens.GestionReportes

import com.example.tercerparcial.screens.ParentPacientes
import com.example.tercerparcial.screens.inicioEspecialidad
import com.example.tercerparcial.screens.inicioMedicos

@Composable
fun NavigationWraper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MyNavigationBar(navController = navController) },
        topBar = { MyTopAppBar(modifier = Modifier.fillMaxSize()) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = pacienteScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<pacienteScreen> {
                ParentPacientes(modifier)
            }
            composable<especialidadScreen> {
                inicioEspecialidad(modifier)
            }
            composable<medicosScreen> {
                inicioMedicos(modifier)
            }
            composable<consultasScreen> {
                GestionConsultas(modifier)
            }
            composable<reportesScreen> {
                GestionReportes(modifier)
            }
        }
    }
}
