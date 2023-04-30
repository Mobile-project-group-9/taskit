import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taskit.ui.viewmodel.home.OfferViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
data class Offer(val id:String ,val title: String, val description: String, val price: Double, val category: String , var isFavorite: Boolean = false)

@Composable
fun OfferListScreen(category: String? = null , offerViewModel: OfferViewModel) {
    val offers by fetchOffers(category)
    var selectedCategory by remember { mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //DropdownMenu(onItemSelected = { selectedCategory = it })
        //Spacer(modifier = Modifier.height(30.dp))
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
        AlertDialog(
            onDismissRequest = { selectedOffer = null },
            title = { Text(text = "Offer Details") },
            text = {
                Column {
                    Text(text = "Title: ${selectedOffer!!.title}")
                    Text(text = "Description: ${selectedOffer!!.description}")
                    Text(text = "Price: ${selectedOffer!!.price}€")
                    Text(text = "Category: ${selectedOffer!!.category}")
                }
            },
            confirmButton = {
                Button(onClick = { selectedOffer = null }) {
                    Text("Close")
                }
            }
        )
    }

    }
}

@Composable
fun OfferCard(offer: Offer, isFavorite : Boolean , onFavoriteClicked:() -> Unit,onClick: (Offer) -> Unit) {
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

/*
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownMenu(onItemSelected: (String) -> Unit) {
    val listCategories = arrayOf("Household", "Babysitting", "Gardening", "Other")
    val contextForToast = LocalContext.current.applicationContext

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedCategory by remember {
        mutableStateOf(listCategories[0])
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // text field
            TextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Label") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // this is a column scope
                // all the items are added vertically
                listCategories.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(onClick = {
                        selectedCategory = selectedOption
                        onItemSelected(selectedOption)
                        Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                        expanded = false
                    }) {
                        Text(text = selectedOption)
                    }
                }

            }
        }
    }
}
 */