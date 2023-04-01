package com.example.taskit.ui.view.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun DropDownMenu(){
    var firstDropdownSelection by remember { mutableStateOf(0) }
    var secondDropdownSelection by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(16.dp)) {
        // First Dropdown Menu
        DropdownMenu(
            expanded = false,
            onDismissRequest = {},
            modifier = Modifier.padding(16.dp)
        ) {
            repeat(3) {
                DropdownMenuItem(onClick = {
                    firstDropdownSelection = it
                    secondDropdownSelection = 0 // reset the selection in the second dropdown
                }) {
                    Text("Option $it")
                }
            }
        }

        // Second Dropdown Menu
        if (firstDropdownSelection > 0) {
            DropdownMenu(
                expanded = false,
                onDismissRequest = {},
                modifier = Modifier.padding(16.dp)
            ) {
                repeat(3) {
                    DropdownMenuItem(onClick = {
                        secondDropdownSelection = it
                    }) {
                        Text("Option ${firstDropdownSelection}.${it}")
                    }
                }
            }
        }
    }
}