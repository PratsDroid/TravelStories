package com.forevertea.travelstories.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.forevertea.travelstories.utils.FileUtils
import com.forevertea.travelstories.viewmodels.TravelActivityModel
import java.io.FileInputStream
import java.io.IOException
import kotlin.random.Random

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    action: (Long) -> Unit,
    editAction: () -> Unit,
    travelActivityModel: TravelActivityModel
) {
    val albumList = travelActivityModel.allAlbums.observeAsState(arrayListOf())
    val context = LocalContext.current
    val imageUriList = remember {
        mutableStateListOf<String>()
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetMultipleContents()
    ) { uriList: MutableList<Uri>? ->
        if (!uriList.isNullOrEmpty()) {
            for (imageUri in uriList) {
                var selectedImagePath: String
                try {
                    val stream = if (imageUri.toString()
                            .contains("com.google.android.apps.photos.contentprovider")
                    ) {
                        val ff = context.contentResolver.openFileDescriptor(imageUri, "r")
                        FileInputStream(ff?.fileDescriptor)
                    } else {
                        context.contentResolver.openInputStream(imageUri)
                    }
                    val createFile = FileUtils().createImageFile()
                    stream?.let { FileUtils().copyInputStreamToFile(it, createFile) }
                    selectedImagePath = createFile.absolutePath
                    imageUriList.add(selectedImagePath)

                } catch (e: IOException) {

                }
            }
            var list = imageUriList.map { Image(
                Random.nextLong(),it) }
            travelActivityModel.setNewAlbum(
                SealedAlbum.Album(
                    name = "one",
                    uriList = imageUriList,
                    imageList = list
                )
            )
            editAction()
        }
    }

    Scaffold(
        topBar = { HomeTopAppBar() },
        floatingActionButton =  {
            FloatingActionButton(onClick = {
                launcher.launch("image/*")
            },backgroundColor = MaterialTheme.colors.primary) {
                Icon(Icons.Filled.Add, stringResource(R.string.add_album))
            }
        }
    ) {
        ImageGrids(albumList = albumList.value, context = context, action = action)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGrids(albumList: List<SealedAlbum.Album>, context:Context, action: (Long) -> Unit){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 16.dp),
        content = {
            items(albumList.size) { index ->
                val album = albumList[index]
                Card(
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(album.uriList?.get(0))
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "thumbnail",
                                modifier = Modifier.size(230.dp),
                                contentScale = ContentScale.Crop,
                                error = painterResource(R.drawable.ic_launcher_foreground)
                            )
                        }
                    },
                    modifier = Modifier
                        .padding( top = 10.dp,end = 10.dp)
                        .clickable { action(album.id) },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        })
}

@Composable
fun HomeTopAppBar() {
    val title = stringResource(id = R.string.app_name)
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}
