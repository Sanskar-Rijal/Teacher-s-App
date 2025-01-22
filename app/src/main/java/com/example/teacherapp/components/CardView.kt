package com.example.teacherapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teacherapp.model.getAddedData.Subject

//to show subject name

@Composable
fun CardView(
    eachsubj: Subject,
    size:Int=50,
    title:String="hehehe",
    onClick: () -> Unit={}
){
    Card(modifier = Modifier
        .height(130.dp)
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {
            onClick()
        },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Text(
                text = eachsubj.name?:"No Data",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                letterSpacing = 0.5.sp
            )
            Log.d("sagar", "$eachsubj ")
            Text(
                text = "${eachsubj.faculty?:"No Data"} ${eachsubj.section?:"No Data"} / sem-${eachsubj.semester?:"No Data}"}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp
            )

            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom) {


                roundedButton(label = " ${eachsubj.semester} semester"
                    , radius = 70)

            }

        }
    }
}



@Composable
@Preview
fun roundedButton(
    label:String="Reading",
    radius:Int=30,
    onPress:()->Unit ={}
){
    Surface(modifier = Modifier
        .clip(RoundedCornerShape(bottomEndPercent = 40,
            topStartPercent = radius)),
        color = MaterialTheme.colorScheme.tertiaryContainer){
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable {
                onPress.invoke()
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { //heightin means it will not be smaller
            // than minimum and not be greater than maximum
            /**
             * If the content naturally requires less space than the min height, it will expand to meet the min height.
             * If the content requires more space than the max height, it will shrink to fit within the max height.
             * If the content naturally fits within the range, it will take its natural height.
             */
            /**
             * If the content naturally requires less space than the min height, it will expand to meet the min height.
             * If the content requires more space than the max height, it will shrink to fit within the max height.
             * If the content naturally fits within the range, it will take its natural height.
             */
            Text(text = label,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
