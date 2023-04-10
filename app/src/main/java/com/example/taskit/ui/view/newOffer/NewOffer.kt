package com.example.taskit.ui.view.newOffer

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun NewOffer(navController: NavController) {
    val fireStore = Firebase.firestore

    var category by remember { mutableStateOf("Choose a Category") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    val currentuser = FirebaseAuth.getInstance().currentUser?.uid;

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFF0077be)),
            contentAlignment = Alignment.TopCenter


        ){
            Text(text = "New Offer", fontSize = 30.sp, color = Color.White, textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp))
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 90.dp)
                .height(720.dp)
                .width(350.dp)
                .background(Color.LightGray),


            ){
            Text(text = "Title :", fontSize = 20.sp, color = Color.Black, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))
            OutlinedTextField(value =title , onValueChange = {title = it},
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .height(40.dp)
                    .width(300.dp)
                    .background(Color.White),)
            CategoryList(onClick = {category = it})
            OutlinedTextField(value =category , onValueChange = {category=it},
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .width(300.dp)
                    .background(Color.White),
                label = {Text(text = "Other category")}
            )
            Text(text = "Location :", fontSize = 20.sp, color = Color.Black, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))
            OutlinedTextField(value =location , onValueChange = {location=it},
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .height(40.dp)
                    .width(300.dp)
                    .background(Color.White),)
            Text(text = "Description :", fontSize = 20.sp, color = Color.Black, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))
            OutlinedTextField(value =description , onValueChange = {description=it},
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .height(200.dp)
                    .width(300.dp)
                    .background(Color.White),)

            Text(text = "Price :", fontSize = 20.sp, color = Color.Black, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))
            OutlinedTextField(value =price.toString() , onValueChange = {price = it.toIntOrNull() ?: 0},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .height(40.dp)
                    .width(300.dp)
                    .background(Color.White),)
            Row(
                modifier = Modifier
                    .padding(horizontal = 80.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                /*Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                ) {
                    Text(text = "Cancel", color = Color(0xFF0077be))
                }
                 */
                Button(onClick = {
                    val job = hashMapOf<String, Any>(
                        "category" to category,
                        "description" to description,
                        "location" to location,
                        "Price" to price,
                        "title" to title,
                        "currentuser" to currentuser.toString()
                    )

                    fireStore.collection("offers")
                        .add(job)
                        .addOnSuccessListener { d -> Log.i("***", "job added") }
                        .addOnFailureListener { e -> Log.i("***", e.toString())}
                }) {
                    Text("Submit")
                }
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
                .width(300.dp)
                .background(Color(0xFF0077be))
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
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
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

