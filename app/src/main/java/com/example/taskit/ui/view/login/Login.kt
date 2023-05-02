package com.example.taskit.ui.view.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskit.R
import com.example.taskit.ui.theme.TaskitTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.taskit_login),
            contentDescription = "Login image",
            modifier=Modifier.fillMaxSize(),
            contentScale= ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 220.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
                .alpha(0.6f)
                .clip(
                    CutCornerShape(
                        topStart = 8.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    fontSize=36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text="Sign In to your Account",
                    fontSize=18.sp,
                    fontWeight=FontWeight.SemiBold,
                    color=Color.Blue
                )

                if (isError) {
                    Text(
                        text = loginUiState?.loginError ?: "unknown error",
                        color = Color.Red,
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 22.dp),
                    value = loginUiState?.userName ?: "",
                    onValueChange = { loginViewModel?.onUserNameChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Email")
                    },
                    isError = isError
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    value = loginUiState?.password ?: "",
                    onValueChange = { loginViewModel?.onPasswordNameChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Password")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isError
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(onClick = {
                    loginViewModel?.loginUser(context)

                }) {
                    Text(text = "Sign In")
                }
                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Don't have an Account?")
                    Spacer(modifier = Modifier.size(10.dp))
                    TextButton(onClick = { onNavToSignUpPage.invoke() }) {
                        Text(text = "SignUp")
                    }


                }

                if (loginUiState?.isLoading == true) {
                    CircularProgressIndicator()
                }

                LaunchedEffect(key1 = loginViewModel?.hasUser) {
                    if (loginViewModel?.hasUser == true) {
                        onNavToHomePage.invoke()
                    }
                }

            }
        }

    }




}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToLoginPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.signup_background),
            contentDescription = "Login image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
                .alpha(0.6f)
                .clip(
                    CutCornerShape(
                        topStart = 8.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign Up",
                    fontSize=36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text="Create your Account",
                    fontSize=18.sp,
                    fontWeight=FontWeight.SemiBold,
                    color=Color.Blue
                )

                if (isError) {
                    Text(
                        text = loginUiState?.signUpError ?: "unknown error",
                        color = Color.Red,
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.firstNameSignUp ?: "",
                    onValueChange = { loginViewModel?.onFirstNameChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "First Name ")
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()} )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.lastNameSignUp ?: "",
                    onValueChange = { loginViewModel?.onLastNameChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Last Name ")
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()} )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.userNameSignUp ?: "",
                    onValueChange = { loginViewModel?.onUserNameChangeSignup(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Email")
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()} )
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.passwordSignUp ?: "",
                    onValueChange = { loginViewModel?.onPasswordChangeSignup(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Password")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()} )
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.confirmPasswordSignUp ?: "",
                    onValueChange = { loginViewModel?.onConfirmPasswordChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Confirm Password")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()} )
                )

                Button(onClick = { loginViewModel?.createUser(context)}) {
                    Text(text = "Sign Up")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Already have an Account?")
                    Spacer(modifier = Modifier.size(8.dp))
                    TextButton(onClick = { onNavToLoginPage.invoke() }) {
                        Text(text = "Sign In")
                    }

                }

                if (loginUiState?.isLoading == true) {
                    CircularProgressIndicator()
                }

                LaunchedEffect(key1 = loginViewModel?.hasUser) {
                    if (loginViewModel?.hasUser == true) {
                        onNavToHomePage.invoke()
                    }
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    TaskitTheme() {
        LoginScreen(onNavToHomePage = { /*TODO*/ }) {

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PrevSignUpScreen() {
    TaskitTheme() {
        SignUpScreen(onNavToHomePage = { /*TODO*/ }) {

        }
    }
}
