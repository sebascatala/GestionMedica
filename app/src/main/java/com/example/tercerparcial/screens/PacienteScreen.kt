package com.example.tercerparcial.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tercerparcial.DAO.daoPacientes
import com.example.tercerparcial.Entidades.Paciente
import com.example.tercerparcial.components.Botones
import com.example.tercerparcial.components.DropPadre
import com.example.tercerparcial.components.InputField
import com.example.tercerparcial.components.MyDialog


@Composable
fun ParentPacientes(modifier: Modifier){
    //variables de los et
    var codigoPaciente: String by rememberSaveable { mutableStateOf("") }
    var nombre: String by rememberSaveable { mutableStateOf("") }
    var edad: String by rememberSaveable { mutableStateOf("") }
    var carnet: String by rememberSaveable { mutableStateOf("") }
    var telefono: String by rememberSaveable { mutableStateOf("") }
    //variable dropdown
    var genero by rememberSaveable { mutableStateOf("GENERO") }


    // lógica de pacientes
    val contexto = LocalContext.current
    val dao = remember { daoPacientes(contexto) }
    val listaPacientes = remember { mutableStateListOf<Paciente>() }
    var mostrarLista by remember { mutableStateOf(false) }


    //variable para el alert
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        listaPacientes.clear()
        listaPacientes.addAll(dao.obtenerPacientes())
    }

    Column (modifier.verticalScroll(rememberScrollState())){
        //titulo
        Box (Modifier.fillMaxWidth(),contentAlignment = androidx.compose.ui.Alignment.Center){
            Text(text = "Gestion Pacientes",fontStyle = FontStyle.Italic, fontSize = 25.sp, modifier = Modifier.padding(25.dp))
        }
        //et
        InputField(codigoPaciente,"codigo de Paciente", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),onUserChange = {codigoPaciente = it})
        InputField(nombre,"Ingrese el nombre", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), onUserChange = {nombre = it})
        InputField(edad,"Ingrese la edad", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onUserChange = {edad  = it})
        InputField(carnet,"Ingrese el carnet", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onUserChange = {carnet  = it})
        InputField(telefono,"Ingrese el telefono", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onUserChange = {telefono  = it})

        //drop
        DropPadre( valorSeleccionado = genero, onValorSeleccionado = { genero = it },listOf("Masculino", "Femenino", "Otro"))

        //botones
        Botones(Modifier,"INSERTAR PACIENTE"){
            insertarPaciente(dao, codigoPaciente, nombre, edad, carnet, telefono, genero)
            mostrarDialogo = true
        }
        Botones(Modifier, "ACTUALIZAR PACIENTE"){
            actualizarPacientes(dao, codigoPaciente, nombre, edad, carnet, telefono, genero)
        }
        Botones(Modifier,"MOSTRAR TODOS LOS PACIENTES"){
            mostrarLista = !mostrarLista // alterna visibilidad
            if (mostrarLista) {
                cargarPacientes(dao, listaPacientes) // actualiza solo si se va a mostrar
            }
        }
        //mostrar la lista de los pacientes
        AnimatedVisibility (mostrarLista, exit = scaleOut()) { // cambie un if por animatd visibility para que cuando se cierre el lazy column se vea bonito
            MostrarPacientes(listaPacientes)
        }

        if (mostrarDialogo) {
            MyDialog(onDismiss = { mostrarDialogo = false },"Paciente ingresado", "El paciente fue ingresado correctamente")
        }

    }
}


@Composable
fun MostrarPacientes(listaPacientes: List<Paciente>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(listaPacientes) { paciente ->
            Text(
                text = "Código: ${paciente.codigo}; Nombre: ${paciente.nombre}; Edad: ${paciente.edad}; Género: ${paciente.genero}, Telefono: ${paciente.telefono}",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



fun insertarPaciente(dao: daoPacientes, codigo: String, nombre: String, edad: String, carnet: String, telefono: String, genero: String) {
    val paciente = Paciente(
        codigo = codigo.toIntOrNull() ?: 0,
        nombre = nombre,
        edad = edad.toIntOrNull() ?: 0,
        carnet = carnet.toIntOrNull() ?: 0,
        telefono = telefono.toIntOrNull() ?: 0,
        genero = genero
    )
    dao.insertarPaciente(paciente)

}

fun cargarPacientes(dao: daoPacientes, listaPacientes: SnapshotStateList<Paciente>) {
    listaPacientes.clear()
    listaPacientes.addAll(dao.obtenerPacientes())
}

fun actualizarPacientes(dao: daoPacientes, codigo: String, nombre: String, edad: String,carnet: String,telefono: String,genero: String) {
    val paciente = Paciente(
        codigo = codigo.toIntOrNull() ?: 0,
        nombre = nombre,
        edad = edad.toIntOrNull() ?: 0,
        carnet = carnet.toIntOrNull() ?: 0,
        telefono = telefono.toIntOrNull() ?: 0,
        genero = genero
    )
    dao.actualizarPaciente(paciente)
}