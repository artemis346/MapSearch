package com.mapsearch.uikit.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import com.mapsearch.uikit.utils.emptyField
import com.mapsearch.uikit.R

@Composable
fun SearchTextField(
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit
) {
    var message by remember {
        mutableStateOf(emptyField)
    }

    var showClearIcon by remember {
        mutableStateOf(false)
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        TextField(
            value = message,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.white)
            ),
            placeholder = {
                Text(
                    text = stringResource(id = placeholder),
                    color = colorResource(R.color.white),
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_small_white),
                    tint = colorResource(
                        id = R.color.white,
                    ),
                    contentDescription = "search"
                )
            },
            trailingIcon = {
                if (showClearIcon) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear_white),
                        tint = colorResource(
                            id = R.color.white,
                        ),
                        contentDescription = "search",
                        modifier = Modifier.clickable {
                            message = emptyField
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                if (it.text != message.text) {
                    message = it
                    onValueChange(message.text)
                }
                showClearIcon = it.text.isNotEmpty()
            },
            singleLine = true
        )
    }
}