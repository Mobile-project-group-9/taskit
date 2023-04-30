package com.example.taskit.ui.viewmodel.home

import Offer
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.taskit.ui.model.repository.StorageRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OfferViewModel (private val repository: StorageRepository = StorageRepository()): ViewModel() {

    fun onFavoriteClicked(offer:Offer) {
        val isFavorite = offer.isFavorite

        if (isFavorite) {
            removeFromFavorites(offer.id){
                offer.isFavorite = false
            }

        } else {
            addToFavorites(offer.id) {
                offer.isFavorite = true
            }
        }

        offer.isFavorite = !isFavorite
    }

    private fun addToFavorites(offerId : String , onSuccess:() ->Unit){
        val db = Firebase.firestore
        val collectionRef = db.collection("favorites")
        val favoritesRef = collectionRef.document(repository.getUserId())





        favoritesRef.get()
            .addOnSuccessListener {documentSnapshot ->
                if(documentSnapshot.exists()) {
                    favoritesRef.update("offerIds", FieldValue.arrayUnion(offerId))
                        .addOnSuccessListener {
                            Log.d("******", "Offer added to favorites successfully")
                            onSuccess()
                        }
                        .addOnFailureListener {exception ->
                            Log.e("******",exception.message.toString())
                        }

                }else {
                    val userData = hashMapOf(
                        "offerIds" to arrayListOf(offerId)
                            )
                    favoritesRef.set(userData)
                        .addOnSuccessListener {
                            Log.d("******","offer added to favorites successfuly")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("******", exception.message.toString())
                        }

                }
            }
            .addOnFailureListener {
                Log.e("******",it.message.toString())
            }


    }

    private fun removeFromFavorites(offerId : String , onSuccess: () -> Unit) {
        val db = Firebase.firestore
        val collectionRef = db.collection("favorites")
        val favoritesRef = collectionRef.document(repository.getUserId())


        favoritesRef.update("offerIds", FieldValue.arrayRemove(offerId))
            .addOnSuccessListener {
                Log.d("******", "offer removed from favorites successfully")
                onSuccess()
            }
            .addOnFailureListener {
                Log.e("******",it.message.toString())
            }

    }

}