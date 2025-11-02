package com.example.tercerparcial.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun InputField(variable: String, texto: String, keyboardOptions: KeyboardOptions, onUserChange: (String) -> Unit){
    // TextField( )
    OutlinedTextField(variable, onValueChange = {onUserChange(it)}, label ={ Text(texto) },keyboardOptions = keyboardOptions, modifier = Modifier.padding(10.dp))
}

@Composable
fun Botones(modifier: Modifier, texto: String, onClick: ()->Unit){
    Button(onClick = onClick, modifier.fillMaxWidth().padding(20.dp), content = { Text(texto) })
}

@Composable
fun <T> DropPadre(
    valorSeleccionado: T,
    onValorSeleccionado: (T) -> Unit,
    opciones: List<T> ,
    label: String = "SELECCIONAR"
) {
    var expandido by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(10.dp)) {
        Button(

            onClick = { expandido = true },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(

                text =  valorSeleccionado?.toString() ?: label,
                color = Color.Black
            )
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(

                   // leadingIcon = { Icon(Icons.Default., contentDescription = "") },
                    text = { Text(opcion.toString()) },
                    onClick = {
                        onValorSeleccionado(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}

@Composable
fun MyDialog(onDismiss: () -> Unit, titulo: String, texto: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Entendido")
            }
        },
        title = { Text(titulo) },
        text = { Text(texto) }
    )
}

