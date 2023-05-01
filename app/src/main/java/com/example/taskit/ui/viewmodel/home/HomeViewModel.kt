package com.example.taskit.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import com.example.taskit.ui.model.repository.StorageRepository

class HomeViewModel(
    private val repository: StorageRepository = StorageRepository(),
) : ViewModel() {
    fun signOut() = repository.signOut()
}

data class Offer(
    val id:String ,
    val title: String,
    val description: String,
    val price: Double,
    val category: String ,
    var isFavorite: Boolean = false
)
