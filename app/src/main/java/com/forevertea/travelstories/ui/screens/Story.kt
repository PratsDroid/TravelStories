package com.forevertea.travelstories.ui.screens


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.forevertea.travelstories.R
import com.forevertea.travelstories.data.album.Image
import com.forevertea.travelstories.viewmodels.TravelActivityModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.max
import kotlin.math.min

@ExperimentalFoundationApi
@Composable
fun Story(id: Long?, travelActivityModel: TravelActivityModel) {

    var imageList = remember {
        mutableStateListOf<Image>()
    }
    travelActivityModel.getAlbum(id!!).observe(LocalLifecycleOwner.current) { albumList ->
        if (!albumList.isNullOrEmpty()) {
            imageList.clear()
            albumList[0].imageList?.let { imageList.addAll(it) }
        }
    }
    ShowStories(imageList)
}

@Composable
fun ShowStories(imageList: SnapshotStateList<Image>) {
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Black,
        )
    }
    val stepCount = imageList.size
    val currentStep = remember { mutableStateOf(0) }
    var isPaused = remember { mutableStateOf(false) }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val imageModifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        currentStep.value = if (offset.x < constraints.maxWidth / 2) {
                            max(0, currentStep.value - 1)
                        } else {
                            min(stepCount - 1, currentStep.value + 1)
                        }
                        isPaused.value = false
                    },
                    onPress = {
                        try {
                            isPaused.value = true
                            awaitRelease()
                        } finally {
                            isPaused.value = false
                        }
                    }
                )
            }
        var url = ""
        if(imageList.size>0){
            url = imageList[currentStep.value].uri
        }
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "Edit",
            modifier = imageModifier,
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.FillHeight
        )
        ProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            stepCount = stepCount,
            stepDuration = 2_000,
            unSelectedColor = Color.LightGray,
            selectedColor = Color.White,
            currentStep = currentStep.value,
            onStepChanged = { currentStep.value = it },
            isPaused = isPaused.value,
            onComplete = { }

        )
    }

}

@Composable
fun ProgressIndicator(
    modifier: Modifier, stepCount: Int,
    stepDuration: Int, unSelectedColor: Color,
    selectedColor: Color, currentStep : Int,
    onStepChanged: (Int) -> Unit,
    isPaused: Boolean = false,
    onComplete: () -> Unit
) {
    val currentStepState = remember(currentStep) { mutableStateOf(currentStep) }
    val progress = remember(currentStep) { Animatable(0f) }

    Row(
        modifier = modifier
    ) {
        for (i in 0 until stepCount) {
            val stepProgress = when {
                i == currentStepState.value -> progress.value
                i > currentStepState.value -> 0f
                else -> 1f
            }
            LinearProgressIndicator(
                color = selectedColor,
                backgroundColor = unSelectedColor,
                progress = stepProgress,
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .height(2.dp) //Indicator height
            )
        }
    }
    LaunchedEffect(
        isPaused, currentStep
    ) {
        if (isPaused) {
            progress.stop()
        } else {
            for (i in currentStep until stepCount) {
                progress.animateTo(
                    1f,
                    animationSpec = tween(
                        durationMillis = ((1f - progress.value) * stepDuration).toInt(),
                        easing = LinearEasing
                    )
                )
                if (currentStepState.value + 1 <= stepCount - 1) {
                    progress.snapTo(0f)
                    currentStepState.value += 1
                    onStepChanged(currentStepState.value)
                } else {
                    onComplete()
                }
            }
        }
    }
}

