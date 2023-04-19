import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
data class Offer(val title: String, val description: String, val price: Double, val category: String)

@Composable
fun OfferListScreen(category: String? = null) {
    val offers by fetchOffers()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(offers) { offer ->
                OfferCard(offer = offer)
            }
        }
    }
}

@Composable
fun OfferCard(offer: Offer) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
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
            val title = document.getString("title") ?: ""
            val description = document.getString("description") ?: ""
            val price = document.getDouble("price") ?: 0.0
            val category = document.getString("category") ?: ""
            Offer(title = title, description = description, price = price, category = category)
        }
        offersState.value = offersList
    }

    return offersState
}