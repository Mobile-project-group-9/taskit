package com.example.taskit.ui.viewmodel.profile

import androidx.lifecycle.ViewModel
import com.example.taskit.ui.model.repository.AuthRepository
import com.example.taskit.ui.model.repository.StorageRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileViewModel(
    private val repository: StorageRepository = StorageRepository(),
) : ViewModel() {

    val userId= repository.getUserId()
    val user = repository.user()
    val hasUser:Boolean
        get() = repository.hasUser()

    fun signOut() = repository.signOut()

}