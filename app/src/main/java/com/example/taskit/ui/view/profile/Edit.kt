package com.example.taskit.ui.view.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskit.R

@Composable

fun EditScreen(navController: NavController){
    val scroll= rememberScrollState()

    Scaffold(
        topBar = {TopAppBar(
            modifier = Modifier.background(Color.Blue),
            title = { Text(
                modifier = Modifier.padding(top=10.dp, start = 20.dp),
                text="Taskit" ,
                fontSize = 30.sp ,
                fontWeight= FontWeight.Bold,
                color= Color.White)

            })},
        content = { paddingValues ->
            Log.d("Padding values", "$paddingValues")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll)
            ){
                TopEdit()
                EditBox()

            }
        },
    )
}

@Composable
fun TopEdit() {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(Color.Blue)
    ){
        Text(
            modifier = Modifier.padding(top=10.dp, start = 20.dp),
            text="Edit Page" ,
            fontSize = 30.sp ,
            fontWeight= FontWeight.Bold,
            color= Color.White
        )
    }
}

@Composable
fun EditBox() {
    Box(
        modifier = Modifier
            .padding(vertical = 200.dp)
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "First Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "personIcon"
                    )
                },
                value = "", onValueChange = {}, readOnly = true)
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "Last Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "personIcon"
                    )
                },
                value = " ", onValueChange = {}, readOnly = true)
            TextField(modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "emailIcon"
                    )
                },
                value = "", onValueChange = {})
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "date of birth ") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "dateIcon"
                    )
                },
                value = "", onValueChange = {})
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = " phone number ") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "callIcon"
                    )
                },
                value = "", onValueChange = {})

            Button(
                onClick = {  },
                modifier = Modifier.width(width=120.dp)
            ) {
                Row(
                ){
                    Text(text="Edit", fontSize = 15.sp)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(painter = painterResource(id = R.drawable.mode_edit_icon) , contentDescription = "Edit Icon" , modifier= Modifier.size(20.dp))
                }

            }

        }

    }
}