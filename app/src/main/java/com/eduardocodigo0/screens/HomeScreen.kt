package com.eduardocodigo0.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eduardocodigo0.R
import com.eduardocodigo0.db.Joke
import com.eduardocodigo0.util.UiStates
import com.eduardocodigo0.viewmodel.RandomJokeViewModel
import org.koin.androidx.compose.viewModel

@Preview(showBackground = true)
@Composable
fun HomeScreen() {

    val jokeViewModel: RandomJokeViewModel by viewModel()
    val stateView: UiStates<Joke> by jokeViewModel.viewState.collectAsState()
    val wasJokeSaved by jokeViewModel.jokeSaved.collectAsState()



    when (stateView) {
        is UiStates.Idle -> {
        }
        is UiStates.Error -> {
            Toast.makeText(LocalContext.current, stringResource(id = R.string.internet_error), Toast.LENGTH_SHORT).show()
        }
        is UiStates.Loading -> {
        }
        is UiStates.Success -> {
            val joke = (stateView as UiStates.Success<Joke>).content
            JokeDialog(joke = joke, save = {
                jokeViewModel.saveJoke(joke)
            }, dismis = {
                jokeViewModel.setIdleState()
            })
        }
    }


    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (stateView is UiStates.Loading) {
                ProgressIndicator(state = stateView,
                    Modifier
                        .size(150.dp)
                        .padding(8.dp))
            } else {
                Button(
                    onClick = { jokeViewModel.getRandomJoke() },
                    shape = CircleShape,
                    contentPadding = PaddingValues(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple_500)),
                    modifier = Modifier
                        .padding(16.dp)
                        .size(160.dp)

                ) {
                    Text(stringResource(id = R.string.main_button_text),
                        textAlign = TextAlign.Center,
                        color = Color.White)
                }
            }


            if (wasJokeSaved) {
                jokeViewModel.resetJokeSavedState()
                Toast.makeText(LocalContext.current, stringResource(id = R.string.save_success), Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun JokeDialog(joke: Joke, save: () -> Unit, dismis: () -> Unit) {
    var isOpen by remember {
        mutableStateOf(true)
    }
    if (isOpen) {
        AlertDialog(
            onDismissRequest = {
                isOpen = false
                dismis()
            },
            title = { Text(text = stringResource(id = R.string.random_joke_card_title)) },
            text = { Text(text = "${joke.setup}\n${joke.delivery}") },
            confirmButton = {
                Button(onClick = {
                    save()
                    isOpen = false
                }, contentPadding = PaddingValues(8.dp)) {
                    Image(

                        painter = painterResource(R.drawable.ic_heart),
                        contentDescription = stringResource(id = R.string.heart_img_content_description),
                        Modifier.padding(8.dp))

                    Text(text = stringResource(id = R.string.save_button_text), Modifier.padding(8.dp))
                }
            }
        )
    }
}


@Composable
fun ProgressIndicator(state: UiStates<Joke>, modifier: Modifier = Modifier) {
    when (state) {
        is UiStates.Loading -> {
            CircularProgressIndicator(modifier = modifier, strokeWidth = 12.dp)
        }
    }
}