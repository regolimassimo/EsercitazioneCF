package com.massimoregoli.esercitazionecf.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.massimoregoli.esercitazionecf.ui.theme.BorderColor
import com.massimoregoli.esercitazionecf.ui.theme.TextColor

@Composable
fun MyOutlinedTextField(
    field: MutableState<String>,
    label: String,
    enabled: Boolean = true,
    modifier: Modifier,
    keyboardActions: KeyboardActions = KeyboardActions { },
    onValueChange: () -> Unit = {}
) {
    OutlinedTextField(
        value = field.value,
        onValueChange = {
            field.value = it
            onValueChange()
        },
        modifier = modifier
            .padding(4.dp),
        label = {
            Text(text = label)
        },
        enabled = enabled,
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        textStyle = TextStyle(color = TextColor),
        shape = RoundedCornerShape(20.dp),

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BorderColor,
            unfocusedBorderColor = BorderColor,
            disabledBorderColor = BorderColor
        )

    )
}