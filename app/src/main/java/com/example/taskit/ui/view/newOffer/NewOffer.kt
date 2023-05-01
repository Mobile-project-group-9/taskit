package com.example.taskit.ui.view.newOffer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.taskit.ui.theme.TaskitTheme
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewOffer(navController: NavController) {
    val fireStore = Firebase.firestore
    var category by remember { mutableStateOf("Choose a Category") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    val userID = FirebaseAuth.getInstance().currentUser?.uid

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Blue,
                title = {
                    Text(
                        "New Offer",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val job = hashMapOf(
                                "category" to category,
                                "description" to description,
                                "location" to location,
                                "price" to price,
                                "title" to title,
                                "userID" to userID.toString()
                            )
                            fireStore.collection("offers")
                                .add(job)
                                .addOnSuccessListener { d -> Log.i("***", "job added") }
                                .addOnFailureListener { e -> Log.i("***", e.toString()) }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
                    ) {
                        Text(
                            text = "Submit",
                            color = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Card(
            modifier = Modifier
                //.padding(horizontal = 30.dp, vertical = 90.dp)
                .height(720.dp)
                .width(350.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Title :",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                CategoryList(onClick = { category = it })
                Text(
                    text = "Location :",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    singleLine = true
                )
                Text(
                    text = "Description :",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 5
                )
                Text(
                    text = "Price :",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                OutlinedTextField(
                    value = price.toString(),
                    onValueChange = { price = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    singleLine = true
                )
            }
        }
    }
}

@Composable
fun CategoryList(onClick:(String) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Choose a Category") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val items = listOf("Household","Babysitting","Gardening","Other category")

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .fillMaxWidth()
                .background(Color(0xFFFFA500))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label

                    val category: String = when (label) {
                        "Household" -> "Household"
                        "Babysitting" -> "Babysitting"
                        "Gardening" -> "Gardening"
                        "Other category" -> "Other category"
                        else -> "Other category"
                    }
                    onClick(category)
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}