package com.forevertea.travelstories.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.forevertea.travelstories.R
import com.forevertea.travelstories.data.album.Image
import com.forevertea.travelstories.data.album.SealedAlbum
import com.forevertea.travelstories.ui.reorder.ReorderableItem
import com.forevertea.travelstories.ui.reorder.detectReorderAfterLongPress
import com.forevertea.travelstories.ui.reorder.rememberReorderableLazyListState
import com.forevertea.travelstories.ui.reorder.reorderable
import com.forevertea.travelstories.viewmodels.TravelActivityModel

@Composable
fun PreviewImages(travelActivityModel: TravelActivityModel, navigateBack: () -> Unit, actionAddTextPage: () -> Unit) {

    val album = remember {
        mutableStateOf(travelActivityModel.newAlbum.value)
    }
    ImageList(
        travelActivityModel = travelActivityModel,
        navigateBack,
        album = album.value,
        actionAddTextPage = actionAddTextPage
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageList(
    travelActivityModel: TravelActivityModel,
    navigateBack: () -> Unit,
    album: SealedAlbum.Album,
    actionAddTextPage: () -> Unit
) {
    val imageList = remember {
        mutableStateListOf<Image>()
    }
    imageList.clear()
    imageList.addAll(album.imageList)
    travelActivityModel.onUpdate.value
    val state = rememberReorderableLazyListState(onMove = travelActivityModel::moveImage)

    val context = LocalContext.current
    Scaffold(topBar = { EditImagesTopAppBar(travelActivityModel, navigateBack , actionAddTextPage) }) {
        LazyRow(
            state = state.listState,
            modifier = Modifier
                .reorderable(state)
                .detectReorderAfterLongPress(state)
                .padding(all = 10.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(imageList, { item -> item.id }) { item ->
                ReorderableItem(reorderableState = state, key = item.id) { dragging ->
                    val scale = animateFloatAsState(if (dragging) 1.1f else 1.0f)
                    val elevation = if (dragging) 8.dp else 0.dp

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(item.uri)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .requiredWidth(270.dp),
                            error = painterResource(R.drawable.ic_launcher_foreground),
                            contentScale = ContentScale.Fit
                        )
                        IconButton(onClick = {
                            imageList.remove(item)
                            album.imageList = imageList
                            travelActivityModel.setNewAlbum(album = album)
                        }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Delete",
                                tint = Color.Gray
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun EditImagesTopAppBar(travelActivityModel: TravelActivityModel, navigateBack: () -> Unit, actionAddTextPage: () -> Unit) {
    TopAppBar(
        title = { "" },
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_arrow)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                travelActivityModel.insertAlbum()
                navigateBack()
            }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(R.string.Done)
                )
            }
            IconButton(onClick = {
                actionAddTextPage()
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.Done)
                )
            }
        }
    )
}

