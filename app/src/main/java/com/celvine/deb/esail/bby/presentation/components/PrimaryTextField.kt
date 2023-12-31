package com.celvine.deb.esail.bby.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.celvine.deb.esail.bby.common.theme.DodgerBlue
import com.celvine.deb.esail.bby.common.theme.SoftGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextField(
    placeholder: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = SoftGray,
            unfocusedBorderColor = SoftGray,
            focusedBorderColor = DodgerBlue,
            cursorColor = DodgerBlue
        ),
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(63.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = {
            Text(text = placeholder)
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun PrimaryTextFiledPreview() {
//    Column(modifier = Modifier.padding(10.dp)) {
//        PrimaryTextField(placeholder = "Test Component", KeyboardType.Text)
//    }
//}