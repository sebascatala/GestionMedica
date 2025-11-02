package com.example.tercerparcial.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tercerparcial.DAO.daoEspecialidad
import com.example.tercerparcial.DAO.daoMedicos
import com.example.tercerparcial.DAO.daoPacientes
import com.example.tercerparcial.DAO.daoReportes
import com.example.tercerparcial.Entidades.Especialidad
import com.example.tercerparcial.Entidades.Medico
import com.example.tercerparcial.Entidades.Paciente
import com.example.tercerparcial.components.DropPadre

@Composable
fun GestionReportes(modifier: Modifier){

    val contexto = LocalContext.current

    val daoMedicos = remember { daoMedicos(contexto) }
    val daoPaciente = remember { daoPacientes(contexto) }
    val daoEspecialidad = remember { daoEspecialidad(contexto) }


    var especialidadSeleccionada by remember { mutableStateOf<Especialidad?>(null) }
    var medicoSeleccionado by remember { mutableStateOf<Medico?>(null) }
    var pacienteSeleccionado by remember { mutableStateOf<Paciente?>(null) }


    val listaEspecialidades = remember { daoEspecialidad.obtenerEspecialidades() }
    val listaPaciente = remember { daoPaciente.obtenerPacientes()}
    val listaMedico = remember { daoMedicos.MostrarMedicos() }


    var tipoReporte by rememberSaveable { mutableStateOf("SELECCIONAR") }

    val daoReporte = remember { daoReportes(contexto) }





    Column (modifier.verticalScroll(rememberScrollState())){
        Box (Modifier.fillMaxWidth(),contentAlignment = androidx.compose.ui.Alignment.Center){
            Text(text = "Gestion Reportes",fontStyle = FontStyle.Italic, fontSize = 25.sp, modifier = Modifier.padding(25.dp))
        }

        DropPadre( valorSeleccionado = tipoReporte, onValorSeleccionado = { tipoReporte = it },listOf("Medico", "Especialidad", "Paciente"))
        MostrarDropCorrecto(
            tipoReporte = tipoReporte,
            especialidadSeleccionada = especialidadSeleccionada,
            onEspecialidadSeleccionada = { especialidadSeleccionada = it },
            medicoSeleccionado = medicoSeleccionado,
            onMedicoSeleccionado = { medicoSeleccionado = it },
            pacienteSeleccionado = pacienteSeleccionado,
            onPacienteSeleccionado = { pacienteSeleccionado = it },
            listaEspecialidades = listaEspecialidades,
            listaMedicos = listaMedico,
            listaPacientes = listaPaciente,
            daoReporte = daoReporte
        )



    }


}

@Composable
fun MostrarDropCorrecto(
    tipoReporte: String,
    especialidadSeleccionada: Especialidad?,
    onEspecialidadSeleccionada: (Especialidad) -> Unit,
    medicoSeleccionado: Medico?,
    onMedicoSeleccionado: (Medico) -> Unit,
    pacienteSeleccionado: Paciente?,
    onPacienteSeleccionado: (Paciente) -> Unit,
    listaEspecialidades: List<Especialidad>,
    listaMedicos: List<Medico>,
    listaPacientes: List<Paciente>,
    daoReporte: daoReportes
) {
        when(tipoReporte){
            "Medico"-> {
                DropPadre(
                    valorSeleccionado = medicoSeleccionado ?: Medico(0, "SELECCIONAR", 0, "", 1),
                    onValorSeleccionado = onMedicoSeleccionado,
                    opciones = listaMedicos,
                    label = "Seleccione un médico"
                )
                medicoSeleccionado?.let {
                    reportMedico(daoReporte, it.codigoMedico)
                }
            }
            "Paciente"-> {
                DropPadre(
                    valorSeleccionado = pacienteSeleccionado ?: Paciente(0, "SELECCIONAR", 0, 0, 0, ""),
                    onValorSeleccionado = onPacienteSeleccionado,
                    opciones = listaPacientes,
                    label = "Seleccione un paciente"

                )

                pacienteSeleccionado?.let {
                    reportePaciente(daoReporte, it.codigo)
                }
            }
            "Especialidad"-> {
                DropPadre(
                    valorSeleccionado = especialidadSeleccionada ?: Especialidad(0, "SELECCIONAR", 0.0),
                    onValorSeleccionado = onEspecialidadSeleccionada,
                    opciones = listaEspecialidades,
                    label = "Seleccione una especialidad"
                )
                especialidadSeleccionada?.let {
                    reporteEspecialidad(daoReporte, it.codigo)
                }
            }

        }

}

@Composable
fun reportePaciente(daoReportes: daoReportes, codigoPacienteSeleccionado: Int) {
    val (cantidadConsultas, totalPago) = daoReportes.obtenerDatosPorPaciente(codigoPacienteSeleccionado)
    val historial = daoReportes.obtenerMedicosYFechasPorPaciente(codigoPacienteSeleccionado)

    Text("La cantidad de consultas del paciente son: $cantidadConsultas")
    Text("En total pagó: $totalPago Bs")

    if (historial.isNotEmpty()) {
        Text("Historial de atención:")
        historial.forEach { (medico, fecha) ->
            Text("- $medico en fecha $fecha")
        }
    } else {
        Text("No se encontraron consultas registradas.")
    }
}


@Composable
fun reportMedico(daoReportes: daoReportes, codigoMedicoSeleccionado: Int){
    var res = daoReportes.obtenerDatosPorMedico(idMedico = codigoMedicoSeleccionado)
    Text("La especialidad del medico: ${res.first}")
    Text("En total tuvo: ${res.second} , consultas")
    Text("En total cobro: ${res.third},Bs")
}

@Composable
fun reporteEspecialidad(daoReportes: daoReportes, codigoEspecialidadSeleccionado: Int){
    val res = daoReportes.obtenerDatosPorEspecialidad(codigoEspecialidadSeleccionado)
    Text("Número de médicos con esta especialidad: ${res.first}")
    Text("Cantidad total de consultas: ${res.second}")
    Text("Total recaudado: ${res.third} Bs")
}
