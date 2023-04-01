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
    val items = listOf("Option 1", "Option 2", "Option 3")
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select an option:",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colors.onSurface, shape = RoundedCornerShape(4.dp))
                .clickable(onClick = { setExpanded(!expanded) })
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = items[selectedIndex.value],
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex.value = index
                        setExpanded(false)
                    }
                ) {
                    Text(text = text)
                }
            }
        }
    }
}