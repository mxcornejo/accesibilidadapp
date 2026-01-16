package com.example.accesibilidadapp.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    val pink = Color(0xFFE91E63)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,

        singleLine = singleLine,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },

        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,

        shape = RoundedCornerShape(16.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = pink,
            unfocusedBorderColor = pink.copy(alpha = 0.5f),
            cursorColor = pink,
            focusedLabelColor = pink,
            focusedLeadingIconColor = pink,
            focusedTrailingIconColor = pink,
        ),

        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null
                )
            }
        },

        trailingIcon = {
            if (trailingIcon != null) {
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = trailingIconDescription
                        )
                    }
                } else {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = trailingIconDescription
                    )
                }
            }
        }
    )
}
