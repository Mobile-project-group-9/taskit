package com.example.taskit.ui.view.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

import com.example.taskit.ui.viewmodel.home.Offer
import com.example.taskit.ui.viewmodel.home.OfferViewModel
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun FavouriteScreen(profileViewModel: ProfileViewModel , offerViewModel: OfferViewModel) {


    Box(
        modifier = Modifier
            .fillMaxSize()
        ) {
        Column() {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    //.height(25.dp)
//                    .background(Color(0xFF0077be))
//                    .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
//            ) {
//                Text(
//                    text = "Favorites", fontSize = 30.sp, color = Color.White,
//                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
//                )
//            }
            TopAppBar(
                title = { Text(
                    text="Favorites" ,
                    fontSize = 25.sp ,
                    fontWeight= FontWeight.Bold,
                    color= Color.White)

                }
            )
            OfferListScreen(profileViewModel.userId, offerViewModel = offerViewModel)
        }


    }
}

@Composable
fun OfferListScreen(userId:String , offerViewModel: OfferViewModel) {
    val offers by offerViewModel.favouriteOffers(userId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var selectedOffer by remember {
            mutableStateOf<Offer?>(null)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(offers) { offer ->
                OfferCard(offer = offer,isFavorite = offer.isFavorite, onFavoriteClicked = {offerViewModel.onFavoriteClicked(offer)
                }, onClick = { selectedOffer = it })
            }
        }
        if (selectedOffer != null) {
            OfferDetailsDialog(
                offer = selectedOffer,
                onDismiss = { selectedOffer = null }
            )
        }

    }
}

@Composable
fun OfferDetailsDialog(offer: Offer?, onDismiss: () -> Unit) {
    if (offer != null) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Title: ${offer.title}")
                    Text(text = "Description: ${offer.description}")
                    Text(text = "Price: ${offer.price}€")
                    Text(text = "Category: ${offer.category}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
fun OfferCard(offer: Offer, isFavorite : Boolean , onFavoriteClicked:() -> Unit,onClick: (Offer) -> Unit){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(offer) },
            elevation = 4.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = offer.title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(text = offer.description)
                    Text(
                        text = "Price: ${offer.price}€",
                        style = MaterialTheme.typography.caption
                    )
                }
                IconButton(
                    onClick = {onFavoriteClicked()}
                ){
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites " else " Add to favorites"
                    )
                }
            }
        }
    }







