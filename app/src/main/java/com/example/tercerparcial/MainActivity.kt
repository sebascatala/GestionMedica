package com.example.tercerparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
/*import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.tercerparcial.components.MyNavigationBar
import com.example.tercerparcial.components.MyTopAppBar*/
import com.example.tercerparcial.components.navigation.NavigationWraper
import com.example.tercerparcial.ui.theme.TercerParcialTheme

class MainActivity : ComponentActivity() {
   // private var gestion: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       setContent {
           TercerParcialTheme {
               NavigationWraper()
           }
       }
    }
/*    @Composable
    fun DeterminarScreen(gestion: Int, modifier: PaddingValues){
        if (gestion == 1){
            ParentPacientes(modifier = Modifier.padding(modifier))
        }
        if (gestion == 2){
            inicioEspecialidad(modifier = Modifier.padding(modifier))
        }

    }*/

}





