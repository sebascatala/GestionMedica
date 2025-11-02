package com.example.tercerparcial.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tercerparcial.R
import com.example.tercerparcial.model.NavItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.tercerparcial.components.navigation.consultasScreen
import com.example.tercerparcial.components.navigation.especialidadScreen
import com.example.tercerparcial.components.navigation.medicosScreen
import com.example.tercerparcial.components.navigation.pacienteScreen
import com.example.tercerparcial.components.navigation.reportesScreen

// MyNavigationBar.kt
@Composable
fun MyNavigationBar(modifier: Modifier = Modifier,navController: NavHostController) {
    val itemList = listOf(
        NavItem(R.drawable.pacientepng, pacienteScreen),
        NavItem(R.drawable.especialidadpng, especialidadScreen),
        NavItem(R.drawable.medicopng, medicosScreen),
        NavItem(R.drawable.consultapng, consultasScreen),
        NavItem(R.drawable.reportespng, reportesScreen)
    )
    var selectedindex by remember { mutableIntStateOf(0) }
    NavigationBar(modifier = modifier, containerColor = Color.LightGray) {
        itemList.forEachIndexed { index, item ->
            Item(item, isSelected = index == selectedindex) {
                selectedindex = index
                navController.navigate(item.screen) {
                    popUpTo(navController.graph.id) { inclusive = false }
                    launchSingleTop = true
                }
            }
        }



    }
}
@Composable
fun RowScope.Item(navItem: NavItem, isSelected: Boolean, onItemClick: ()-> Unit){

    NavigationBarItem(
        selected = isSelected,
        onClick = {onItemClick()},
        icon = {
            Icon(
                painter = painterResource(navItem.image),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        },
        colors =  NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            indicatorColor = Color.Gray
        )
    )

}
