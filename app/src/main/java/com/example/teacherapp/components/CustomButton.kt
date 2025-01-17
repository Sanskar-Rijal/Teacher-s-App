package com.example.teacherapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun sansButton(
    modifier: Modifier=Modifier,
    text:String,
    onClick: () -> Unit) {
    // Create Class Button
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1490CF)),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp)
    ) {
        Text(text = text,
            modifier = Modifier.padding(end = 10.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White)
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "button")
    }
}