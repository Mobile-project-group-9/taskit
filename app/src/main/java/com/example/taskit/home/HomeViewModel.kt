package com.example.taskit.home



import androidx.lifecycle.ViewModel
import com.example.taskit.repository.StorageRepository



class HomeViewModel(
    private val repository: StorageRepository = StorageRepository(),
) : ViewModel() {

    fun signOut() = repository.signOut()









}

