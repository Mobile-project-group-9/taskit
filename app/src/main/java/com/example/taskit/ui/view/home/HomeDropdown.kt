package com.example.taskit.ui.view.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dropdownItems
import dropdownSubItems

@Composable
fun DropdownMenu(){
    val items = dropdownItems // use the imported list of items
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }
    val showDropdown2 = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select a job category:",
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
                        showDropdown2.value = true
                    }
                ) {
                    Text(text = text)
                }
            }
        }
    }
    if (showDropdown2.value){
        DropdownMenu2(selectedIndex.value)
        //showDropdown2.value = !showDropdown2.value
    }
}

@Composable
fun DropdownMenu2(categoryIndex: Int) {
    Log.d(TAG, categoryIndex.toString())

    val items = dropdownSubItems
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select a sub-category:",
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