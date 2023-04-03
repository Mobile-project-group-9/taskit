package com.example.taskit.ui.viewmodel.profile

import androidx.lifecycle.ViewModel
import com.example.taskit.ui.model.repository.StorageRepository

class ProfileViewModel(
    private val repository: StorageRepository = StorageRepository(),) : ViewModel() {
        fun signOut() = repository.signOut()
}