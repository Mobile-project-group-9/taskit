package com.example.taskit.ui.view.profile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskit.HomeRoutes
import com.example.taskit.ui.theme.TaskitTheme
import com.example.taskit.ui.view.login.LoginScreen
import com.example.taskit.ui.view.navigation.MyBottomNavigationBar
import com.example.taskit.ui.viewmodel.home.HomeViewModel
import com.example.taskit.ui.viewmodel.navigation.TabItem
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// ------ ADD THE SIGN OUT FUNCTION HERE -------
/*

@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    navToLoginPage: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        homeViewModel?.signOut()
                        navToLoginPage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = "Home")
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
        }
    }
}
*/


@Composable
fun ProfileScreen(navController: NavController, items: List<TabItem>) {

    val profileViewmodel : ProfileViewModel = viewModel()
    val scroll= rememberScrollState()

    Scaffold(
        topBar = { TopBar( ) },
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
                    EditButton()
                    HistoryButton()
                }
                ProfileImage()
                InfoBox()
            }
        },
        bottomBar= { MyBottomNavigationBar(items,navController ) }
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
fun InfoBox(){
    Box(
        modifier = Modifier
            .padding(vertical = 450.dp)
            .fillMaxSize()

    ){
        Column(
            modifier= Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            TextField(
                modifier= Modifier.fillMaxWidth(0.9f), shape= RoundedCornerShape(10.dp),
                label= { Text(text = "First Name") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person , contentDescription = "personIcon") },
                value = "", onValueChange = {} )
            TextField(
                modifier= Modifier.fillMaxWidth(0.9f), shape= RoundedCornerShape(10.dp),
                label= { Text(text = "Last Name") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person , contentDescription = "personIcon") },
                value = " ", onValueChange = {} )
            TextField(modifier= Modifier.fillMaxWidth(0.9f), shape= RoundedCornerShape(10.dp),
                label= { Text(text = "Email") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email , contentDescription = "emailIcon") },
                value = "", onValueChange = {} )
            TextField(
                modifier= Modifier.fillMaxWidth(0.9f), shape= RoundedCornerShape(10.dp),
                label= { Text(text = "date of birth ") },
                leadingIcon = { Icon(imageVector = Icons.Default.DateRange , contentDescription = "dateIcon") },
                value = "", onValueChange = {} )
            TextField(
                modifier= Modifier.fillMaxWidth(0.9f), shape= RoundedCornerShape(10.dp),
                label= { Text(text = " phone number ") },
                leadingIcon = { Icon(imageVector = Icons.Default.Call , contentDescription = "callIcon") },
                value = "" , onValueChange = {})

        }

    }
}

@Composable
fun ProfileImage(){
    Box(
        modifier= Modifier.padding(vertical = 120.dp, horizontal = 150.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp),
            painter = painterResource(id = com.example.taskit.R.drawable.image),
            contentDescription = "Profile Image "
        )
    }
}

@Composable
fun EditButton(){
    Button(
        onClick = {  },
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
fun TopBar( ) {
    val vm : HomeViewModel = viewModel()
    val vl: ProfileViewModel = viewModel()
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
                DropdownMenuItem(onClick = { vm.signOut()}) {
                    Text("Log out")
                }
            }
        }
    )
}