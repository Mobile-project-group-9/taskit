package com.example.taskit.ui.view.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


@Composable
fun ProfileScreen(navController: NavController,profileViewModel:ProfileViewModel,onSignOut: () -> Unit) {

    val scroll= rememberScrollState()


    Scaffold(
        topBar = { TopBar(profileViewModel,onSignOut) },
        content = { paddingValues ->
            Log.d("Padding values", "$paddingValues")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll)
            ){
                TopBox()
                MiddleBox()
                Row(
                    modifier= Modifier.padding(horizontal = 68.dp, vertical = 300.dp) ,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    EditButton(navController)
                    HistoryButton()
                }
                fetchImage(profileViewModel.userId,navController )
                InfoBox(profileViewModel.userId)
            }
        },
    )

}

@Composable
fun TopBox() {
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(Color.Blue)
    ){
        Text(
            modifier = Modifier.padding(top=10.dp, start = 20.dp),
            text="Profile" ,
            fontSize = 30.sp ,
            fontWeight= FontWeight.Bold,
            color= Color.White
        )
    }
}

@Composable
fun MiddleBox() {
    Box {
        Card(
            elevation = 15.dp,
            shape= RoundedCornerShape(20.dp),
            backgroundColor= Color(0xff7AD2EE),
            modifier = Modifier
                .padding(vertical = 200.dp, horizontal = 50.dp)
                .height(180.dp)
                .width(300.dp)


        ){

        }
    }

}

@Composable
fun InfoBox(user:String) {

    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("users")
    val documentRef = collectionRef.document(user)
    var userInfo by remember { mutableStateOf<MutableMap<String, Any>>(mutableMapOf()) }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val documentSnapshot = documentRef.get().await()
            if (documentSnapshot != null) {
                val data = documentSnapshot.data
                if (data != null) {
                    userInfo = data.toMutableMap()
                    lastName = userInfo.getValue("lastName").toString()
                    firstName = userInfo.getValue("firstName").toString()
                    email = userInfo.getValue("email").toString()
                    birthDate = userInfo.getValue("birthDate").toString()
                    phoneNumber = userInfo.getValue("phoneNumber").toString()
                }
            }
        } catch (e: Exception) {
            Log.e("******", e.message.toString())
        }
    }
        Info(firstName,lastName,birthDate,phoneNumber,email)
}

@Composable
fun Info(firstName:String , lastName:String , birthDate:String ,phoneNumber : String ,email:String){
            Box(
                modifier = Modifier
                    .padding(vertical = 450.dp)
                    .fillMaxSize()

            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)

                ) {
                    Card(
                        backgroundColor = Color(color = 0xFFF6EFEF),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "personIcon"
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "First Name"
                                )
                                Text(
                                    text = firstName
                                )
                            }
                        }
                    }
                    Card(
                        backgroundColor = Color(color = 0xFFF6EFEF),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "personIcon"
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "Last Name"
                                )
                                Text(
                                    text = lastName
                                )
                            }
                        }
                    }
                    Card(
                        backgroundColor = Color(color = 0xFFF6EFEF),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "emailIcon"
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "Email"
                                )
                                Text(
                                    text = email
                                )
                            }
                        }
                    }
                    Card(
                        backgroundColor = Color(color = 0xFFF6EFEF),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "dateIcon"
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "Birth date"
                                )
                                Text(
                                    text = birthDate
                                )
                            }
                        }
                    }
                    Card(
                        backgroundColor = Color(color = 0xFFF6EFEF),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "callIcon")
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "Phone number"
                                )
                                Text(
                                    text = phoneNumber
                                )
                            }
                        }
                    }
                }

            }
        }


@Composable
fun fetchImage(user: String , navController: NavController){
    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("users")
    val documentRef = collectionRef.document(user)
    var userInfo by remember { mutableStateOf<MutableMap<String, Any>>(mutableMapOf()) }
    var urlImage by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        try {
            val documentSnapshot = documentRef.get().await()
            if (documentSnapshot != null) {
                val data = documentSnapshot.data
                if (data != null) {
                    userInfo = data.toMutableMap()
                    urlImage = userInfo.getValue("photo").toString()
                }
            }
        } catch (e: Exception) {
            Log.e("******", e.message.toString())
        }
    }
    ProfileImage(navController,urlImage)
}



@Composable
fun ProfileImage(navController: NavController , urlImage : String){
    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier= Modifier.padding(vertical = 120.dp, horizontal = 150.dp)
    ) {

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Change Profile Photo")
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "emailIcon",
                            modifier=Modifier.clickable { showDialog.value = false }
                        )
                    } },
                text = { Text(text = "take or upload a photo ") },
                confirmButton = {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { navController.navigate("camera") }
                            ) {
                                Text(text = "Take A photo ")
                            }
                            Button(
                                onClick = { navController.navigate("device") }
                            ) {
                                Text(text = " Upload photo")
                            }
                        }
                }
            )
        }
        Image(
            painter= rememberImagePainter(data = urlImage),
            contentDescription = "Profile Image",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
                .clickable { showDialog.value = true }
        )
    }
}

@Composable
fun EditButton(navController: NavController){
    Button(
        onClick = { navController.navigate("Edit")},
        modifier = Modifier.width(width=120.dp)
    ) {
        Row(
        ){
            Text(text="Edit", fontSize = 15.sp)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(painter = painterResource(id = com.example.taskit.R.drawable.mode_edit_icon) , contentDescription = "Edit Icon" , modifier= Modifier.size(20.dp))
        }

    }
}

@Composable
fun HistoryButton(){
    Button(
        onClick = {  },
        modifier = Modifier.width(width=120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text="History", fontSize = 15.sp)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(painter = painterResource(id= com.example.taskit.R.drawable.work_history_icon) , contentDescription = "work history Icon", modifier = Modifier.size(20.dp))
        }
    }

}

@Composable
fun TopBar(profileViewModel:ProfileViewModel,onSignOut:() -> Unit) {
    val notification = rememberSaveable { mutableStateOf("") }
    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var expanded by remember { mutableStateOf( false) }
    TopAppBar(
        modifier = Modifier.background(Color.Blue),
        title = { Text(
            modifier = Modifier.padding(top=10.dp, start = 20.dp),
            text="Taskit" ,
            fontSize = 30.sp ,
            fontWeight= FontWeight.Bold,
            color= Color.White)

        },
        actions = {
            IconButton(onClick = {
                expanded = !expanded

            }
            ) {
                Icon(Icons.Filled.MoreVert,contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded ,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    profileViewModel.signOut()
                    onSignOut()
                }) {
                    Text("Log out")

                    Text(text = "Cancel",
                        modifier = Modifier.clickable { notification.value = "Cancelled" })
                    Text(text = "Save",
                        modifier = Modifier.clickable { notification.value = "Profile updated" })
                }

            }
        }

    )

    LaunchedEffect(key1 = profileViewModel?.hasUser){
        if (profileViewModel?.hasUser == false){
            onSignOut()
        }
    }
}