package com.example.tercerparcial.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tercerparcial.DAO.daoEspecialidad
import com.example.tercerparcial.Entidades.Especialidad
import com.example.tercerparcial.components.Botones
import com.example.tercerparcial.components.InputField
import com.example.tercerparcial.components.MyDialog

@Composable
fun inicioEspecialidad(modifier: Modifier){
    var codigoEspecialidad: String by rememberSaveable { mutableStateOf("") }
    var nombreEspecialidad: String by rememberSaveable { mutableStateOf("") }
    var costoEspecialidad: String by rememberSaveable { mutableStateOf("") }

    val contexto = LocalContext.current
    val dao = remember { daoEspecialidad(contexto) }

    var mostrarDialogo by remember { mutableStateOf(false) }

    Column (modifier.verticalScroll(rememberScrollState())){
        //titulo
        Box (Modifier.fillMaxWidth(),contentAlignment = androidx.compose.ui.Alignment.Center){
            Text(text = "Gestion Especialidades",fontStyle = FontStyle.Italic, fontSize = 25.sp, modifier = Modifier.padding(25.dp))
        }
        //et
        InputField(codigoEspecialidad,"codigo de la Especialidad", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),onUserChange = {codigoEspecialidad = it})
        InputField(nombreEspecialidad,"Ingrese el nombre", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), onUserChange = {nombreEspecialidad = it})
        InputField(costoEspecialidad,"Ingrese el costo", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onUserChange = {costoEspecialidad  = it})

        //botones
        Botones(modifier, "INSERTE LA ESPECIALIDAD") {
            insertarEspecialidad(dao,nombreEspecialidad, costoEspecialidad)
            mostrarDialogo = true
        }


        if (mostrarDialogo) {
            MyDialog(onDismiss = { mostrarDialogo = false },"Especialidad ingresada", "La especialidad fue ingresada correctamente")
        }

    }
}




fun insertarEspecialidad(dao: daoEspecialidad, nombreEspecialidad: String, costoEspecialidad: String){

    val especialidad = Especialidad(
        nombre = nombreEspecialidad,
        costo = costoEspecialidad.toDouble()
    )
    dao.insertarEspecialidad(especialidad)
}
