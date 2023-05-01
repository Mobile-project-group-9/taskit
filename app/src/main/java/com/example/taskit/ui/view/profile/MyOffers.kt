package com.example.taskit.ui.view.profile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun OfferScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            modifier = Modifier.background(Color.Blue),
            title = {
                Text(
                    text = "Offers",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        UserOffersList()
    }
}

@Composable
fun UserOffersList() {
    val currentUserID = FirebaseAuth.getInstance().currentUser?.uid
    val firestoreDB = FirebaseFirestore.getInstance()

    val offers = remember { mutableStateListOf<Offer>() }

    LaunchedEffect(currentUserID) {
        firestoreDB.collection("offers")
            .whereEqualTo("userID", currentUserID)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    val title = document.getString("title") ?: ""
                    val description = document.getString("description") ?: ""
                    val price = document.getDouble("price") ?: 0.0
                    val category = document.getString("category") ?: ""
                    val location = document.getString("location") ?: ""
                    val userID = document.getString("userID") ?: ""
                    val offer = Offer(id, title, description, price, category, location, userID)
                    offers.add(offer)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("UserOffersList", "Error getting documents: ", exception)
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp) // Set a fixed height constraint here
    ) {
        itemsIndexed(offers) { index, offer ->
            OfferCard(
                offer = offer,
                onDeleteOffer = {
                    firestoreDB.collection("offers")
                        .document(offer.id)
                        .delete()
                        .addOnSuccessListener {
                            offers.removeAt(index)
                        }
                        .addOnFailureListener { exception ->
                            Log.w("UserOffersList", "Error deleting document: ", exception)
                        }
                }
            )
        }

    }
}


@Composable
fun OfferCard(offer: Offer, onDeleteOffer: () -> Unit) {
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
                text = offer.title,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = offer.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            Row {
                Text(
                    text = "Price: $${offer.price}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Category: ${offer.category}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Location: ${offer.location}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onDeleteOffer) {
                Text(text = "Delete")
            }
        }
    }
}

data class Offer(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val location: String,
    val userID: String
)


/*
@Composable
fun OfferBox() {


    /*
    Box(
        modifier = Modifier
            .padding(vertical = 150.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color.White)

    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 120.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Offers",
                style= TextStyle(
                    fontSize=20.sp,
                    shadow = Shadow(
                        offset = Offset(2.2f,2.2f),
                        blurRadius = 1.5f
                    )
                )

            )
            Text(
                text = "Tasks",
                fontSize = 20.sp,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier= Modifier.padding(vertical = 70.dp, horizontal = 20.dp),
        ){
            OfferCard("offer 1"," Assyriis victum distenta unius quaeritat idem placet pube sine sed.",com.example.taskit.R.drawable.image_offer)
            OfferCard("offer 2"," Assyriis victum distenta unius quaeritat idem placet pube sine sed.",com.example.taskit.R.drawable.image_offer)
            OfferCard("offer 3"," Assyriis victum distenta unius quaeritat idem placet pube sine sed.",com.example.taskit.R.drawable.image_offer)
        }

    }

     */
}

@Composable
fun OfferCard(OfferName:String,Description:String,photoId:Int){
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
                    .clip(RectangleShape)
                    .size(120.dp),
                painter = painterResource(id = photoId),
                contentDescription = "Offer Image "
            )
            Column() {
                Text(
                    text=OfferName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text=Description,
                    fontSize = 12.sp,
                )
            }
        }

    }

}

 */