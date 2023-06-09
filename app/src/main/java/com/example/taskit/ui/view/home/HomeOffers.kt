import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskit.ui.viewmodel.home.OfferViewModel
import androidx.compose.ui.window.Dialog
import com.example.taskit.ui.viewmodel.home.Offer
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun OfferListScreen(category: String? = null , offerViewModel: OfferViewModel) {
    val offers by fetchOffers(category)
    var selectedCategory by remember { mutableStateOf("")}
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
                                                                                           }, onApplicationClicked = {offerViewModel.addApplication(offer.id,"panding")}, onClick = { selectedOffer = it })
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
fun OfferCard(offer: Offer, isFavorite : Boolean , onFavoriteClicked:() -> Unit,onClick: (Offer) -> Unit , onApplicationClicked :() -> Unit) {

    val isFavorite = offer.isFavorite
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(offer) },
        elevation = 4.dp
    ) {
        Column(){
        Row(
            modifier=Modifier.fillMaxWidth(),
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
                onClick = { onFavoriteClicked() }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites " else " Add to favorites"
                )
            }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(end =5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(Modifier.weight(1f)){

                }
                Button(
                    onClick = { onApplicationClicked() },
                    modifier = Modifier
                        .width(90.dp)
                ) {
                    Text(text = "Apply", fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun fetchOffers(category: String? = null): State<List<Offer>> {
    val offersState = remember { mutableStateOf(emptyList<Offer>()) }
    val firestore = FirebaseFirestore.getInstance()
    val collectionRef = firestore.collection("offers")

    LaunchedEffect(true) {
        var query = collectionRef
        if (category != null) {
            query = query.whereEqualTo("category", category) as CollectionReference
        }
        val snapshot = query.get().await()
        val offersList = snapshot.documents.map { document ->
            val id = document.id
            val title = document.getString("title") ?: ""
            val description = document.getString("description") ?: ""
            val price = document.getDouble("price") ?: 0.0
            val category = document.getString("category") ?: ""
            Offer(id= id,title = title, description = description, price = price, category = category)
        }
        offersState.value = offersList
    }

    return offersState
}
