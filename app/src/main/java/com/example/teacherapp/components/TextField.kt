package com.example.teacherapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddNotice(modifier: Modifier = Modifier,
              valueState: MutableState<String>,
              labelId:String="Email",
              enabled:Boolean=true,
              keyboardType: KeyboardType = KeyboardType.Text,
              imeaction: ImeAction = ImeAction.Next,
              isSingleLine:Boolean=true,
              onAction: KeyboardActions = KeyboardActions.Default,
              maxlines:Int=1
){
    OutlinedTextField(
        value =valueState.value,
        onValueChange = {valueState.value=it},
        label = {
            Text(text = labelId)
        },
        singleLine = isSingleLine,
        maxLines = maxlines,
        textStyle = TextStyle(fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
            imeAction = imeaction),
        keyboardActions = onAction,
    )
}
