package com.example.taskit.ui.view.chatBox

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Blue)
    ){
        Text(
            modifier = Modifier.padding(top=10.dp, start = 20.dp),
            text="ChatBox" ,
            fontSize = 30.sp ,
            fontWeight= FontWeight.Bold,
            color= Color.White
        )
        ChatBox()
        ProfileImage()
    }

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
fun ProfileImage(){
    Box(
        modifier= Modifier.padding(vertical = 80.dp, horizontal = 150.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp),
            //.shadow(elevation=5.dp),

            painter = painterResource(id = com.example.taskit.R.drawable.image),
            contentDescription = "Profile Image "
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