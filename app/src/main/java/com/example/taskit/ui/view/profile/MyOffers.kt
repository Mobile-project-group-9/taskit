package com.example.taskit.ui.view.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskit.ui.theme.TaskitTheme
import com.example.taskit.ui.view.home.Home

@Composable
fun OfferScreen(navController: NavController){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Blue)
    ){
        TopAppBar(
            modifier = Modifier.background(Color.Blue),
            title = { Text(
                text="Offers" ,
                fontSize = 25.sp ,
                fontWeight= FontWeight.Bold,
                color= Color.White)

            },
            navigationIcon = {
                IconButton(onClick = {}
                ) {
                    Icon(painter = painterResource(id = com.example.taskit.R.drawable.baseline_arrow_back_24) , contentDescription = "Go back Icon")
                }
            }
        )
        OfferBox()
    }

}

@Composable
fun OfferBox() {
    Box(
        modifier = Modifier
            .padding(vertical = 150.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color.White)

    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 120.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Offers",
                style= TextStyle(
                    fontSize=20.sp,
                    shadow = Shadow(
                        offset = Offset(2.2f,2.2f),
                        blurRadius = 1.5f
                    )
                )

            )
            Text(
                text = "Tasks",
                fontSize = 20.sp,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier= Modifier.padding(vertical = 70.dp, horizontal = 20.dp),
        ){
            OfferCard("offer 1"," Assyriis victum distenta unius quaeritat idem placet pube sine sed.",com.example.taskit.R.drawable.image_offer)
            OfferCard("offer 2"," Assyriis victum distenta unius quaeritat idem placet pube sine sed.",com.example.taskit.R.drawable.image_offer)
            OfferCard("offer 3"," Assyriis victum distenta unius quaeritat idem placet pube sine sed.",com.example.taskit.R.drawable.image_offer)
        }

    }
}


@Composable
fun OfferCard(OfferName:String,Description:String,photoId:Int){
    Card(

        backgroundColor= Color(color=0xFFF6EFEF),
        shape= RoundedCornerShape(20.dp),
        modifier= Modifier
            .height(100.dp)
            .fillMaxWidth(0.95f)
            .clickable { }
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp)
        ){
            Image(
                modifier = Modifier
                    .clip(RectangleShape)
                    .size(120.dp),
                painter = painterResource(id = photoId),
                contentDescription = "Offer Image "
            )
            Column() {
                Text(
                    text=OfferName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text=Description,
                    fontSize = 12.sp,
                )
            }
        }

    }

}