package com.forevertea.travelstories.ui.screens


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.forevertea.travelstories.R
import com.forevertea.travelstories.viewmodels.TravelActivityModel

@Composable
fun TextPage(travelActivityModel: TravelActivityModel,navigateBack: () -> Unit) {
    Scaffold(topBar = { TextPageTopBar(navigateBack) }) {
        TxtField()
    }
}


@Composable
fun TxtField() {
    Log.d("TextPage"," inside TxtField ")

    var text by remember { mutableStateOf(TextFieldValue()) }
    val colorList = listOf(White, Red, Cyan, Blue, Green, Yellow, Magenta)
    val lst by remember { mutableStateOf(colorList) }
    var lstPosition = remember { mutableStateOf(0) }
    val backgroundColor = lst[lstPosition.value]

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {

            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .background(Transparent),
                label = {Text("Sample text")},
                placeholder = { Text(text = "Type in ur status") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Transparent,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
        }
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Change Background",
            modifier = Modifier
                .padding(end = 40.dp, bottom = 40.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    if (lstPosition.value == lst.size - 1)
                        lstPosition.value = 0
                    else
                        lstPosition.value = lstPosition.value + 1
                } )
    }
}

@Composable
fun TextPageTopBar(navigateBack: () -> Unit){
    TopAppBar(
    title = { "" },
    navigationIcon = {
        IconButton(onClick = { navigateBack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_arrow)
            )
        }
    })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TxtField()
}
