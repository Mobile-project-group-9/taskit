package com.example.taskit.ui.view.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.taskit.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun ProfileImage1(navController: NavController.Companion) {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifEmpty { R.drawable.ic_user }
    )
    var imageRef = Firebase.storage.reference.child("profile.jpg")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
        uri?.let{ u ->
            imageRef.putFile(u)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { remoteUri ->
                        Log.d("***", remoteUri.toString())
                    }
                }
                .addOnFailureListener{
                    Log.e("***", it.message.toString())
                }
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }
}
