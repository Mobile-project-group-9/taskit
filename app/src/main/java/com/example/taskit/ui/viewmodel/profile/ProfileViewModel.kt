package com.example.taskit.ui.viewmodel.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskit.ui.model.repository.StorageRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel(){
    var user = mutableStateOf<FirebaseUser?>(null)

    fun logout(){
        viewModelScope.launch {
            Firebase.auth.signOut()
            user.value = null;
        }
    }


}