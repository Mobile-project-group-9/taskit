package com.example.taskit.ui.view.profile

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.taskit.R
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel
import com.google.accompanist.permissions.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionsView(profileViewModel: ProfileViewModel) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    when(permissionState.status){
        is PermissionStatus.Granted -> {
            CameraMainView(profileViewModel)
        }
        else -> {
            if (permissionState.status.shouldShowRationale) {
                //Denied once and should show the dialog again
                Text(text = "You should really give permission to use this feature")

            } else {
                //When first time starting the application and if permission denied twice (or in setting)
                Text(text = "You've denied permission for camera")
            }
            SideEffect {
                permissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun CameraMainView(profileViewModel: ProfileViewModel) {

    var lensF = CameraSelector.LENS_FACING_FRONT
    var lensB = CameraSelector.LENS_FACING_BACK


    var imageUri: Uri? by remember{ mutableStateOf(null) }
    var lensFacing: Int by remember { mutableStateOf(lensB) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }

    val camSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    if(imageUri == null){
        SideEffect {
            ProcessCameraProvider.getInstance(context).apply {
                addListener({
                    val camProvider = get()
                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    camProvider.unbindAll()
                    camProvider.bindToLifecycle(lifecycleOwner, camSelector, preview, imageCapture  )
                }, ContextCompat.getMainExecutor(context))
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()){
        if(imageUri == null){
            CameraPreview(previewView, {
                lensFacing = if(lensFacing == lensB ) lensF else lensB
            }) {
                takePhoto(context, imageCapture) { imageUri = it }
            }
        }else{
            ShowImageView(imageUri, profileViewModel = profileViewModel){imageUri = null}
        }
    }
}
@Composable
fun CameraPreview(
    previewView: PreviewView,
    changeLens: () -> Unit,
    takePhoto: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
        Row() {
            Icon(
                Icons.Sharp.CheckCircle,
                "takePhoto",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { takePhoto() },
                tint = Color.White
            )
            Icon(
                Icons.Sharp.ArrowForward,
                "changeLens",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { changeLens() },
                tint = Color.White
            )
        }
    }
}


fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (Uri?) -> Unit
){
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()

    imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object: ImageCapture.OnImageSavedCallback{
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Log.d("------", outputFileResults.savedUri.toString() )
            onImageCaptured(outputFileResults.savedUri)
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("", "Take photo error", exception)
        }
    })
}

@Composable
fun ShowImageView(imageUri: Uri?, profileViewModel: ProfileViewModel,takeImage: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUri) ,
            contentDescription = "image",
            modifier = Modifier.border(2.dp, Color.Black)
        )
        Row(){
            Button(onClick = { takeImage() }) {
                Text(text = "Take another photo")
            }
            Button(onClick = {
                imageUri?.let { uri ->
                    uploadImageToFirebase(
                        imageUri = uri,
                        onSuccess = { downloadUrl ->
                            // Handle successful upload
                            // For example, display a success message or navigate to another screen
                            println("Image uploaded successfully. Download URL: $downloadUrl")
                        },
                        onError = { exception ->
                            // Handle error during upload
                            // For example, display an error message
                            println("Error uploading image: ${exception.message}")
                        },
                        profileViewModel = profileViewModel
                    )
                }
            }){
                Text(text="Save Photo")
            }
        }

    }
}




fun uploadImageToFirebase(imageUri: Uri, onSuccess: (String) -> Unit, onError: (Exception) -> Unit,profileViewModel: ProfileViewModel) {

    val storageRef = FirebaseStorage.getInstance().reference
    val imageName = "image_${System.currentTimeMillis()}.jpg"
    val imageRef = storageRef.child("photosProfile/${profileViewModel.userId}/$imageName")

    val uploadTask = imageRef.putFile(imageUri)
    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            throw task.exception!!
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            val imageUrl = downloadUri.toString()
            val db = Firebase.firestore
            val collectionRef = db.collection("users") //
            val documentRef = collectionRef.document(profileViewModel.userId)
            val updatedData = hashMapOf<String,Any>(
                "photo" to imageUrl
            )
            documentRef.update(updatedData)
                .addOnSuccessListener {
                    onSuccess(imageUrl)
                }
                .addOnFailureListener{
                    exception -> onError(exception)
                }

        } else {
            onError(task.exception!!)
        }
    }
}



@Composable
fun DeviceImage(navController: NavController,profileViewModel: ProfileViewModel) {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val imageName = "image_${System.currentTimeMillis()}.jpg"
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifEmpty { R.drawable.ic_user }
    )
    var imageRef = Firebase.storage.reference.child("photosProfile/${profileViewModel.userId}/$imageName")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
        uri?.let { u ->
            imageRef.putFile(u)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { remoteUri ->
                        Log.d("***", remoteUri.toString())
                    }
                }
                .addOnFailureListener {
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
        Button(onClick = {
                val imageUrl = imageUri.value?.toString()
                if (imageUrl != null) {
                    val db = Firebase.firestore
                    val collectionRef = db.collection("users")
                    val documentRef = collectionRef.document(profileViewModel.userId)
                    val updatedData = hashMapOf(
                        "photo" to imageUrl
                    )
                    documentRef.update(updatedData as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d("******","photo updated")
                        }
                        .addOnFailureListener { e ->
                            Log.e("******",e.message.toString())
                        }
                } else {
                    Log.d("******","no url photo")
                }
            }) {
            Text("save the photo")
        }
    }
}