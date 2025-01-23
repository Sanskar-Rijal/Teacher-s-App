package com.example.teacherapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Alert(
    subjectName: String = "Math",
    title1: String = "Add  Marks",
    title2: String = "Show  Marks",
    onAdd: () -> Unit = {},
    onShow: () -> Unit = {},
    onTapOutside: () -> Unit = {}
) {
    AlertDialog(
        title = {
            Text(
                text = subjectName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            Text(
                text = "What would you like to do?",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onTapOutside()
        },
        confirmButton = {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                TextButton(
                    colors = ButtonDefaults.buttonColors(Color(0xFF1490CF)),
                    onClick = {
                        // Action for Add Internal Marks
                        onAdd()
                    }
                ) {
                    Text(text = title1, color = Color.White)
                }
                TextButton(
                    colors = ButtonDefaults.buttonColors(Color(0xFF1490CF)),
                    onClick = {
                        // Action for Show Internal Marks
                        onShow()
                    }
                ) {
                    Text(text = title2, color = Color.White)
                }
            }
        },
        containerColor = Color(0xFFbde0fe)
    )
}
