package com.celvine.deb.esail.bby.presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorToastMessage(errorMessageId: Int) {
    val context = LocalContext.current
    val errorMessage = stringResource(id = errorMessageId)
    Toast.makeText(
        context, errorMessage, Toast.LENGTH_SHORT
    ).show()
}