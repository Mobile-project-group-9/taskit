package com.example.taskit.ui.viewmodel.home

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.taskit.ui.model.repository.StorageRepository
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class MyOffers(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val location: String,
    val userID: String,
    var isFavorite: Boolean = false
)

class OfferViewModel (private val repository: StorageRepository = StorageRepository()): ViewModel() {

    private val offersFav = mutableStateOf<List<Offer>>(emptyList())
    fun onFavoriteClicked(offer: Offer) {
        val isFavorite = offer.isFavorite


        if (isFavorite) {
            removeFromFavorites(offer.id) {
                offer.isFavorite = false
                updateOffer(offer)
            }

        } else {
            addToFavorites(offer.id) {
                offer.isFavorite = true
                updateOffer(offer)
            }
        }


    }

    private fun updateOffer(updateOffer : Offer){
        offersFav.value =offersFav.value.map {
            offer ->
            if( offer.id == updateOffer.id) {
                updateOffer
            }else {
                offer
            }
        }

    }

    private fun addToFavorites(offerId: String, onSuccess: () -> Unit) {
        val db = Firebase.firestore
        val collectionRef = db.collection("favorites")
        val favoritesRef = collectionRef.document(repository.getUserId())




        favoritesRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    favoritesRef.update("offerIds", FieldValue.arrayUnion(offerId))
                        .addOnSuccessListener {
                            Log.d("******", "Offer added to favorites successfully")
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            Log.e("******", exception.message.toString())
                        }

                } else {
                    val userData = hashMapOf(
                        "offerIds" to arrayListOf(offerId)
                    )
                    favoritesRef.set(userData)
                        .addOnSuccessListener {
                            Log.d("******", "offer added to favorites successfuly")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("******", exception.message.toString())
                        }

                }
            }
            .addOnFailureListener {
                Log.e("******", it.message.toString())
            }


    }

    private fun removeFromFavorites(offerId: String, onSuccess: () -> Unit) {
        val db = Firebase.firestore
        val collectionRef = db.collection("favorites")
        val favoritesRef = collectionRef.document(repository.getUserId())

        db.runTransaction { transaction ->
            val snapshot = transaction.get(favoritesRef)
            val offerIds = snapshot.get("offerIds") as? List<String>

            if (offerIds != null) {
                val updatedOfferIds = offerIds.toMutableList().apply { remove(offerId) }
                transaction.update(favoritesRef, "offerIds", updatedOfferIds)
                onSuccess()
                // Update the offersState by setting the isFavorite flag to false
                offersFav.value = offersFav.value.map { offer ->
                    if (offer.id == offerId) {
                        offer.copy(isFavorite = false)
                    } else {
                        offer
                    }
                }
            }
        }
            .addOnSuccessListener {
                Log.d("******", "Offer removed from favorites successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("******", exception.message.toString())
            }
    }

    @Composable
    fun favouriteOffers(userId: String): State<List<Offer>> {

        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("favorites").document(userId)


        LaunchedEffect(userId) {
            collectionRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val offerIds = documentSnapshot.get("offerIds") as? List<String>
                    if (offerIds != null) {
                        fetchoffersByIds(offerIds,offersFav)
                    }
                } else {
                    offersFav.value = emptyList()
                }

            }
                .addOnFailureListener { exception ->
                    Log.e("******", exception.message.toString())
                    offersFav.value = emptyList()
                }
        }
        return offersFav
    }

    private fun fetchoffersByIds(offerIds : List<String> , offersFav : MutableState<List<Offer>>){

        val firestore = FirebaseFirestore.getInstance()
        val offerRef = firestore.collection("offers")

        offerRef.whereIn(FieldPath.documentId(),offerIds)
            .get()
            .addOnSuccessListener { querySnapshot->
                val offersList = querySnapshot.documents.mapNotNull { document ->
                    val id = document.id
                    val title = document.getString("title") ?: ""
                    val description = document.getString("description") ?: ""
                    val price = document.getDouble("price") ?: 0.0
                    val category = document.getString("category") ?: ""
                    Offer(
                        id = id,
                        title = title,
                        description = description,
                        price = price,
                        category = category
                    )
                }
                offersFav.value = offersList
                }
            .addOnFailureListener{exception ->
                 Log.e("******",exception.message.toString())
                offersFav.value= emptyList()
            }
    }

    fun addApplication(offerId : String ,status:String ) {
        val firestore = Firebase.firestore
        val applicationCollectionRef = firestore.collection("applications")
        val userRef = applicationCollectionRef.document(repository.getUserId())


        val applicationData = hashMapOf<String, Any>(
            "offerIds.$offerId" to status
        )


        userRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    userRef.update(applicationData)
                        .addOnSuccessListener {
                            Log.d("******", "Application added  successfully")

                        }
                        .addOnFailureListener { exception ->
                            Log.e("******", exception.message.toString())
                        }

                } else {

                    val userData = hashMapOf(
                        "offerIds" to hashMapOf(offerId to status)
                    )

                    userRef.set(userData)
                        .addOnSuccessListener {
                            Log.d("******", "application added to favorites successfuly")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("******", exception.message.toString())
                        }

                }
            }
            .addOnFailureListener {
                Log.e("******", it.message.toString())
            }
    }

}

