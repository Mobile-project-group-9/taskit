package com.example.taskit.ui.view.profile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskit.R
import com.example.taskit.ui.theme.TaskitTheme
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun TaskScreen(profileViewModel: ProfileViewModel){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ){
        TopAppBar(
            modifier = Modifier.background(Color.Blue),
            title = { Text(
                text="Tasks" ,
                fontSize = 25.sp ,
                fontWeight= FontWeight.Bold,
                color= Color.White)

            },
            navigationIcon = {
                IconButton(onClick = {}
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24) , contentDescription = "Go back Icon")
                }
            }
        )
        TaskBox(profileViewModel.userId)
    }

}

@Composable
fun TaskBox(userId : String ) {
    val applications = remember { mutableStateListOf<Application>() }

    LaunchedEffect(userId){
        applications.clear()
        val retrievedApplication =  getApplicationsForUser(userId)
        applications.addAll(retrievedApplication)
    }
    SideEffect {
        applications.forEachIndexed { index, application ->
            Log.d("TaskBox", "Application $index: $application")
        }
    }
    Box(
        modifier = Modifier
            .padding(vertical = 150.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color.White)

    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier= Modifier.padding(vertical = 70.dp, horizontal = 20.dp),
        ){
            items(applications) { application ->
                ApplicationCard(application = application)
            }
        }

    }
}




@Composable
fun ApplicationCard(application: Application) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = application.title,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = application.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            Row {
                Text(
                    text = "Price: $${application.price}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Category: ${application.category}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Location: ${application.location}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .width(90.dp)
                    .height(50.dp)
            ){
                Text(
                    text=application.status,
                    color = getStatusColor(application.status)
                )
            }
        }
    }
}

@Composable
fun getStatusColor(status: String):Color {
    return when (status) {
        "Pending" -> Color.Gray
        "Approved" -> Color.Green
        "Rejected" -> Color.Red
        else -> Color.Black
    }
}


suspend fun getApplicationsForUser(userId: String): List<Application> {
    val db = FirebaseFirestore.getInstance()
    val applications = mutableListOf<Application>()

    try {
        val appSnapshot = db.collection("applications")
            .document(userId)
            .get()
            .await()

        val offerIds = appSnapshot.data?.keys?.toSet() ?: emptySet<String>()

        val offerSnapshots = offerIds.map { offerId ->
            db.collection("offers")
                .document(offerId)
                .get()
                .await()
        }

        for (offerSnapshot in offerSnapshots) {
            val offerTitle = offerSnapshot.getString("title")
            val offerCategory = offerSnapshot.getString("category")
            val offerDescription = offerSnapshot.getString("description")
            val offerPrice = offerSnapshot.getString("price")
            val offerLocation = offerSnapshot.getString("location")
            val userId = offerSnapshot.getString("userId")

            val userSnapshot = db.collection("users")
                .document(userId!!)
                .get()
                .await()

            val userFName = userSnapshot.getString("firstName")
            val userLName = userSnapshot.getString("lastName")
            val userPhoneNumber = userSnapshot.getString("phoneNumber")

            val status = appSnapshot.getString(offerSnapshot.id)

            val application = Application(
                offerTitle!!,
                offerDescription!!,
                offerPrice!!,
                offerCategory!!,
                offerLocation!!,
                userLName!!,
                userFName!!,
                userPhoneNumber!!,
                status!!
            )

            applications.add(application)
        }
    } catch (e: Exception) {
        // Handle any exceptions here
    }

    return applications
}





data class Application(
    val title: String,
    val description: String,
    val price: String,
    val category: String,
    val location: String,
    val userLName : String,
    val userFName : String,
    val userPhoneNumber: String,
    val status : String
)

