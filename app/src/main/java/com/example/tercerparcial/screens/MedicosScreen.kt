
package com.example.tercerparcial.screens


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
import com.example.tercerparcial.DAO.daoEspecialidad
import com.example.tercerparcial.DAO.daoMedicos

import com.example.tercerparcial.Entidades.Especialidad
import com.example.tercerparcial.Entidades.Medico

import com.example.tercerparcial.components.Botones
import com.example.tercerparcial.components.DropPadre
import com.example.tercerparcial.components.InputField
import com.example.tercerparcial.components.MyDialog


@Composable
fun inicioMedicos(modifier: Modifier){

        val contexto = LocalContext.current
        val daoMedicos = remember { daoMedicos(contexto) }
        val daoEspecialidad = remember { daoEspecialidad(contexto) }


        var codigoMedico: String by rememberSaveable { mutableStateOf("") }
        var nombre: String by rememberSaveable { mutableStateOf("") }
        var direccion: String by rememberSaveable { mutableStateOf("") }
        var celular: String by rememberSaveable { mutableStateOf("") }

        val listaEspecialidades = remember { daoEspecialidad.obtenerEspecialidades() }
        var especialidadSeleccionada by remember{ mutableStateOf<Especialidad?>(null) }
        val listaMedico = remember { mutableStateListOf<Medico>() }
        var mostrarLista by remember { mutableStateOf(false) }


        var mostrarDialogo by remember { mutableStateOf(false) }

    Column (modifier.verticalScroll(rememberScrollState())){
        //titulo
        Box (Modifier.fillMaxWidth(),contentAlignment = androidx.compose.ui.Alignment.Center){
            Text(text = "Gestion Medicos",fontStyle = FontStyle.Italic, fontSize = 25.sp, modifier = Modifier.padding(25.dp))
        }
        //et
        InputField(codigoMedico,"codigo de Medico", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),onUserChange = {codigoMedico = it})
        InputField(nombre,"Ingrese el nombre", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), onUserChange = {nombre = it})
        InputField(direccion,"Ingrese la direccion", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onUserChange = {direccion  = it})
        InputField(celular,"Ingrese su numero celular", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), onUserChange = {celular = it})




        DropPadre(
            valorSeleccionado = especialidadSeleccionada ?: Especialidad(0, "ESPECIALIDAD", costo = 0.0),
            onValorSeleccionado = { especialidadSeleccionada = it },
            opciones = listaEspecialidades,
            label = "Seleccione una especialidad"
        )

        Botones(Modifier,"INSERTAR MEDICO"){
            insertarMedicos(daoMedicos, codigoMedico, nombre, direccion, celular, especialidadSeleccionada!!.codigo.toString())
            mostrarDialogo = true

        }
        Botones(Modifier, "ACTUALIZAR MEDICO"){
            actualizarMedicos(daoMedicos, codigoMedico, nombre, direccion, celular, especialidadSeleccionada!!.codigo.toString() )
        }
        Botones(Modifier,"MOSTRAR TODOS LOS MEDICOS"){
            mostrarLista = !mostrarLista // alterna visibilidad
            if (mostrarLista) {
                cargarMedicos(daoMedicos, listaMedico) // actualiza solo si se va a mostrar
            }
        }
        //mostrar la lista de los pacientes
        if (mostrarLista) {
            MostrarMedicos(listaMedico)
        }

        if (mostrarDialogo) {
            MyDialog(onDismiss = { mostrarDialogo = false },"Medico ingresado", "El medico fue ingresado correctamente")
        }




    }

}
@Composable
fun MostrarMedicos(listaMedicos: List<Medico>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(listaMedicos) { medico ->
            Text(
                text = "Código: ${medico.codigoMedico}; Nombre: ${medico.nombre}; Direccion: ${medico.direccion}; Especialidad: ${medico.codigoEspecialidad}, Celular: ${medico.celular}",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun insertarMedicos(dao: daoMedicos, codigo: String, nombre: String, direccion: String, celular: String, codigoEspecialidad: String) {
    val medicos = Medico(
        codigoMedico = codigo.toIntOrNull() ?: 0,
        nombre = nombre,
        direccion = direccion,
        celular = celular.toIntOrNull() ?: 0,
        codigoEspecialidad = codigoEspecialidad.toIntOrNull() ?: 0
    )
    dao.insertarMedicos(medicos)

}

fun cargarMedicos(dao: daoMedicos, listaMedico: SnapshotStateList<Medico>) {
    listaMedico.clear()
    listaMedico.addAll(dao.MostrarMedicos())
}

fun actualizarMedicos(dao: daoMedicos,  codigo: String, nombre: String, direccion: String, celular: String, codigoEspecialidad: String) {
    val medicos = Medico(
        codigoMedico = codigo.toIntOrNull() ?: 0,
        nombre = nombre,
        direccion = direccion,
        celular = celular.toIntOrNull() ?: 0,
        codigoEspecialidad = codigoEspecialidad.toIntOrNull() ?: 0
    )
    dao.updateMedicos(medicos)
}

