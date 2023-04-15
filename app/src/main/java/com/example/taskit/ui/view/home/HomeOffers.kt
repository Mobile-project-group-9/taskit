import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
data class Offer(val title: String, val description: String, val price: Double)

@Composable
fun OfferListScreen() {
    val offers by fetchOffers()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Offers",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        DropdownMenu()
        Spacer(modifier = Modifier.height(30.dp))
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

//onClick of a category -> new screen that displays only the offers related to the clicked cat.
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownMenu() {
    val listItems = arrayOf("Household", "Babysitting", "Gardening", "Other")
    val contextForToast = LocalContext.current.applicationContext

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem by remember {
        mutableStateOf(listItems[0])
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
                value = selectedItem,
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
                listItems.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(onClick = {
                        selectedItem = selectedOption
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
fun fetchOffers(): State<List<Offer>> {
    val offersState = remember { mutableStateOf(emptyList<Offer>()) }
    val firestore = FirebaseFirestore.getInstance()
    val collectionRef = firestore.collection("offers")

    LaunchedEffect(true) {
        val snapshot = collectionRef.get().await()
        val offersList = snapshot.documents.map { document ->
            val title = document.getString("title") ?: ""
            val description = document.getString("description") ?: ""
            val price = document.getDouble("price") ?: 0.0
            Offer(title = title, description = description, price = price)
        }
        offersState.value = offersList
    }

    return offersState
}

