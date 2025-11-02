package com.example.tercerparcial.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tercerparcial.DAO.daoConsultas
import com.example.tercerparcial.DAO.daoEspecialidad
import com.example.tercerparcial.DAO.daoMedicos
import com.example.tercerparcial.DAO.daoPacientes
import com.example.tercerparcial.Entidades.Consulta
import com.example.tercerparcial.Entidades.Especialidad
import com.example.tercerparcial.Entidades.Medico
import com.example.tercerparcial.Entidades.Paciente
import com.example.tercerparcial.components.Botones
import com.example.tercerparcial.components.DropPadre
import com.example.tercerparcial.components.InputField
import com.example.tercerparcial.components.MyDialog

@Composable
fun GestionConsultas(modifier: Modifier) {

    var motivoConsulta by rememberSaveable { mutableStateOf("") }
    var fechaConsulta by rememberSaveable { mutableStateOf("") }

    val contexto = LocalContext.current

    val daoMedicos = remember { daoMedicos(contexto) }
    val daoPaciente = remember { daoPacientes(contexto) }
    val daoEspecialidad = remember { daoEspecialidad(contexto) }
    val daoConsulta = remember { daoConsultas(contexto) }

    var especialidadSeleccionada by remember { mutableStateOf<Especialidad?>(null) }
    var pacienteSeleccionado by remember { mutableStateOf<Paciente?>(null) }
    var medicoSeleccionado by remember { mutableStateOf<Medico?>(null) }

    val listaEspecialidades = remember { daoEspecialidad.obtenerEspecialidades() }
    val listaPacientes = remember { daoPaciente.obtenerPacientes() }
    val listaMedicos = remember { daoMedicos.MostrarMedicos() }

    val medicosFiltrados by remember(especialidadSeleccionada, listaMedicos) {
        mutableStateOf(listaMedicos.filter { it.codigoEspecialidad == especialidadSeleccionada?.codigo })
    }

    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(modifier.verticalScroll(rememberScrollState())) {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = "Gestión Consultas",
                fontStyle = FontStyle.Italic,
                fontSize = 25.sp,
                modifier = Modifier.padding(25.dp)
            )
        }

        DropPadre(
            valorSeleccionado = pacienteSeleccionado ?: Paciente(0, "PACIENTES", 0, 0, 0, ""),
            onValorSeleccionado = { pacienteSeleccionado = it },
            opciones = listaPacientes,
            label = "Seleccione un paciente"
        )

        DropPadre(
            valorSeleccionado = especialidadSeleccionada ?: Especialidad(0, "ESPECIALIDADES", 0.0),
            onValorSeleccionado = { especialidadSeleccionada = it },
            opciones = listaEspecialidades,
            label = "Seleccione una especialidad"
        )


        Text(
            text = "Costo: Bs. ${especialidadSeleccionada?.costo ?: "0.00"}",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        MedicosPorEspecialidad(
            listaMedicos = medicosFiltrados,
            medicoSeleccionado = medicoSeleccionado,
            onSeleccionarMedico = { medicoSeleccionado = it }
        )

        medicoSeleccionado?.let {
            Text(
                text = "Médico seleccionado: ${it.nombre}",
                modifier = Modifier.padding(start = 16.dp),
                fontStyle = FontStyle.Italic
            )
        }

        InputField(
            motivoConsulta, "Motivo de la consulta",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onUserChange = { motivoConsulta = it }
        )

        InputField(
            fechaConsulta, "Fecha de la consulta",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onUserChange = { fechaConsulta = it }
        )

        Botones(modifier, "Registrar consulta") {
            if (
                pacienteSeleccionado != null &&
                especialidadSeleccionada != null &&
                medicoSeleccionado != null &&
                motivoConsulta.isNotBlank() &&
                fechaConsulta.isNotBlank()
            ) {
                val nuevaConsulta = Consulta(
                    codigo = 0,
                    codigoPaciente = pacienteSeleccionado!!.codigo,
                    codigoMedico = medicoSeleccionado!!.codigoMedico,
                    motivo = motivoConsulta,
                    fecha = fechaConsulta,
                    costo = especialidadSeleccionada!!.costo
                )

                daoConsulta.insertarConsulta(nuevaConsulta)
                mostrarDialogo = true
            } else {
                println("Faltan datos para registrar la consulta")
            }

        }

        if (mostrarDialogo) {
            MyDialog(onDismiss = { mostrarDialogo = false },"Consulta registrada", "La consulta fue ingresada correctamente")
        }
    }
}

@Composable
fun MedicosPorEspecialidad(
    listaMedicos: List<Medico>,
    medicoSeleccionado: Medico?,
    onSeleccionarMedico: (Medico) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(listaMedicos) { medico ->
            val isSelected = medico == medicoSeleccionado
            Text(
                text = "Código: ${medico.codigoMedico}; Nombre: ${medico.nombre}; Dirección: ${medico.direccion}; Celular: ${medico.celular}",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .then(if (isSelected) Modifier.padding(4.dp) else Modifier)
                    .clickable { onSeleccionarMedico(medico) },

                color = if(isSelected)Color.Blue else Color.Black,
                fontSize = if (isSelected) 18.sp else 16.sp
            )
        }
    }
}
