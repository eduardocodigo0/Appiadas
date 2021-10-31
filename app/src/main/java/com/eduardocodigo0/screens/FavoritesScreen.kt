package com.eduardocodigo0.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardocodigo0.db.Joke
import com.eduardocodigo0.util.UiStates
import com.eduardocodigo0.viewmodel.FavoriteJokesViewModel
import org.koin.androidx.compose.viewModel
import com.eduardocodigo0.R

@Preview(showBackground = true)
@Composable
fun FavoritesScreen() {

    val favoriteViewModel: FavoriteJokesViewModel by viewModel()
    val stateView: UiStates<List<Joke>> by favoriteViewModel.viewState.collectAsState()
    val wasJokeDeleted by favoriteViewModel.jokeDeleted.collectAsState()



    when (stateView) {
        is UiStates.Idle -> {
            favoriteViewModel.getJokeList()
        }
        is UiStates.Success -> {
            val jokeList = (stateView as UiStates.Success<List<Joke>>).content
            JokeList(list = jokeList) {
                favoriteViewModel.deleteJoke(it)
            }

        }

        is UiStates.Error -> {
            Toast.makeText(LocalContext.current,
                stringResource(id = R.string.database_error),
                Toast.LENGTH_SHORT).show()
        }
    }

    if (wasJokeDeleted) {
        Toast.makeText(LocalContext.current, R.string.delete_success, Toast.LENGTH_SHORT).show()
        favoriteViewModel.resetDeletedJokesState()
        favoriteViewModel.getJokeList()
    }


}


@Composable
fun JokeListItem(joke: Joke, action: (Joke) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    if (isDialogOpen) {
        DeleteJokeDialog(delete = {
            action(joke)
        }, {
            isDialogOpen = false
        })
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Column() {
            Column(verticalArrangement = Arrangement.Top) {
                Box(Modifier
                    .background(colorResource(id = R.color.purple_500))
                    .fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.card_id,"${joke.uid}"),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(8.dp))


                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = stringResource(id = R.string.delete_img_content_description),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterEnd)
                            .clickable(
                                onClick = {
                                    isDialogOpen = true
                                }
                            ),
                    )
                }
            }
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(text = "${joke.setup}")

                if (expanded) {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                    Text(text = "${joke.delivery}")
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_expand),
                        contentDescription = stringResource(id = R.string.expand_img_content_description),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    expanded = true
                                }
                            ),
                    )
                }
            }
        }

    }
}

@Composable
fun JokeList(list: List<Joke>, action: (Joke) -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = Modifier.padding(bottom = 60.dp)) {
        list.forEach { joke ->
            item {
                JokeListItem(joke = joke, action = action)
            }
        }
    }
}

@Composable
fun DeleteJokeDialog(delete: () -> Unit, dismiss: () -> Unit) {
    var isOpen by remember {
        mutableStateOf(true)
    }
    if (isOpen) {
        AlertDialog(
            onDismissRequest = {
                isOpen = false
                dismiss()
            },
            title = { Text(text = stringResource(id = R.string.delete_card_title)) },
            text = { Text(text = stringResource(id = R.string.delete_card_msg)) },
            confirmButton = {
                Button(onClick = {
                    delete()
                    isOpen = false
                    dismiss()
                }, contentPadding = PaddingValues(8.dp)) {
                    Text(text = stringResource(id = R.string.yes_button_text),
                        Modifier.padding(8.dp))
                }
            },
            dismissButton = {
                Button(onClick = {
                    isOpen = false
                    dismiss()
                }, contentPadding = PaddingValues(8.dp)) {
                    Text(text = stringResource(id = R.string.no_button_text),
                        Modifier.padding(8.dp))
                }
            }
        )
    }
}