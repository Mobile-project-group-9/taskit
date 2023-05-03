package com.example.taskit.ui.view.chatBox

import android.graphics.Paint.Align
import android.os.Bundle
import com.example.taskit.ui.view.chatBox.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ChatScreen(userName: String, navController: NavHostController) {
    val messages = getMessagesForUser(userName)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
    ) {

        TopAppBar(
            title = { Text(
                text="ChatBox" ,
                fontSize = 25.sp ,
                fontWeight= FontWeight.Bold,
                color= Color.White)

            }
        )
            Spacer(modifier = Modifier.height(16.dp))
            ChatBox(navController)
            ProfileImage()
        }

    }


@Composable
fun ChatBox(navController: NavHostController) {
    Box(
        modifier = Modifier
            .padding(vertical = 120.dp)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color.White)

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .padding(top = 120.dp)
                .padding(horizontal = 16.dp)
        ) {
            ChatCard(Message("User123", "Hello there!", com.example.taskit.R.drawable.image), navController)
            ChatCard(Message("User456", "How are you?", com.example.taskit.R.drawable.image), navController)
            ChatCard(Message("User789", "I'm fine, thanks.", com.example.taskit.R.drawable.image), navController)
            ChatCard(Message("User321", "What about you?", com.example.taskit.R.drawable.image), navController)
        }
    }
}

@Composable
fun ProfileImage() {
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
fun ChatCard(message: Message, navController: NavHostController) {
    Card(
        backgroundColor= Color.LightGray,
        shape= RoundedCornerShape(20.dp),
        modifier= Modifier
            .height(100.dp)
            .fillMaxWidth(0.95f)
            .clickable {}
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp)
                .background(Color.LightGray)
                .clickable {
                    navController.navigate("message")
                }
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape),
                painter = painterResource(id = message.photoId),
                contentDescription = "Profile Image "
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = message.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(
                        text = message.text,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

data class Message(
    val name: String,
    val text: String,
    val photoId: Int
)

@Composable
fun ChatMessagesScreen(
    userName: String,
    navController: NavHostController
) {
    val (newMessage, setNewMessage) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Top app bar with back button
        TopAppBar(
            title = {
                Text(
                    text = userName,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp
        )

        // Chat messages list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            items(getMessagesForUser(userName)) { message ->
                ChatCard(message,navController)
            }
        }
        Box(
            modifier = Modifier
                .padding(50.dp)

        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = setNewMessage,
                label = { Text("Type a message...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(60.dp),
                maxLines = 2,
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape,
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = com.example.taskit.R.drawable.ic_send),
                            contentDescription = "Send",
                            tint = if (newMessage.isNotBlank()) MaterialTheme.colors.primary else Color.Gray
                        )
                    }
                }
            )
        }

    }
}

// Dummy function to get messages for a user
fun getMessagesForUser(userName: String): List<Message> {
    return listOf(
        Message(userName, "Hi", com.example.taskit.R.drawable.image),

        Message("Me", "Hey there!", com.example.taskit.R.drawable.image),
        Message(userName, "How are you?", com.example.taskit.R.drawable.image),
        Message("Me", "I'm good, thanks! How about you?", com.example.taskit.R.drawable.image),
        Message(userName, "I'm doing well too", com.example.taskit.R.drawable.image),
        Message("Me", "very good", com.example.taskit.R.drawable.image),
        Message(userName, "I'm doing well too", com.example.taskit.R.drawable.image)
        )
}
