package com.example.taskit.ui.view.chatBox

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.taskit.ui.theme.TaskitTheme
import com.example.taskit.ui.view.newOffer.NewOffer
import com.example.taskit.ui.viewmodel.navigation.TabItem
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun ChatScreen(profileViewModel: ProfileViewModel){

    TopAppBar(
        title = { Text(
            text="ChatBox" ,
            fontSize = 25.sp ,
            fontWeight= FontWeight.Bold,
            color= Color.White)

        }
    )
    ChatBox()
    FetchImage(profileViewModel.userId)

}

@Composable
fun ChatBox() {
    Box(
        modifier = Modifier
            .padding(vertical = 150.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color.White)

    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier= Modifier.padding(vertical = 90.dp, horizontal = 20.dp),
        ){
            ChatCard("User123",com.example.taskit.R.drawable.image)
            ChatCard("User456",com.example.taskit.R.drawable.image)
            ChatCard("User789",com.example.taskit.R.drawable.image)
            ChatCard("User321", com.example.taskit.R.drawable.image )
        }
    }
}

@Composable
fun FetchImage(user: String ){
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
    ProfileImage(urlImage)
}

@Composable
fun ProfileImage(urlImage :String ){
    Box(
        modifier= Modifier.padding(vertical = 80.dp, horizontal = 150.dp)
    ) {
        Image(
            painter= rememberImagePainter(data = urlImage),
            contentDescription = "Profile Image",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
        )
    }
}

@Composable
fun ChatCard(user:String,photoId:Int){
    Card(

        backgroundColor= Color(color=0xFFF6EFEF),
        shape= RoundedCornerShape(20.dp),
        modifier= Modifier
            .height(100.dp)
            .fillMaxWidth(0.95f)
            .clickable { }
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp)
        ){
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp),
                painter = painterResource(id = photoId),
                contentDescription = "Profile Image "
            )
            Text(
                text=user,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }

}

//@Preview(showSystemUi = true)
//@Composable
//fun chatScreen() {
//    TaskitTheme {
//        ChatScreen()
//    }
//}