package com.eduardocodigo0.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardocodigo0.db.Joke
import com.eduardocodigo0.db.JokeRepository
import com.eduardocodigo0.util.UiStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteJokesViewModel(val repository: JokeRepository): ViewModel() {

    private val _viewState = MutableStateFlow<UiStates<List<Joke>>>(UiStates.Idle())
    val viewState get() = _viewState

    private val _jokeDeleted = MutableStateFlow<Boolean>(false)
    val jokeDeleted get() = _jokeDeleted


    fun deleteJoke(joke: Joke){
        viewModelScope.launch(Dispatchers.IO){
            _jokeDeleted.value = try {
                repository.deleteJoke(joke)
                true
            }catch (err: Exception){
                false
            }
        }
    }

    fun getJokeList(){
        viewModelScope.launch(Dispatchers.IO){
            setLoadingState()

            try{
                emitSavedJokes()
            } catch(err: Exception){
                emitErrorMsg(err)
            }
        }
    }

    private suspend fun emitSavedJokes(){
        _viewState.value = UiStates.Success(
            repository.getJokes()
        )
    }

    private fun emitErrorMsg(err: Exception){
        _viewState.value = UiStates.Error(
            err.message.toString()
        )
    }

    private fun setLoadingState(){
        _viewState.value = UiStates.Loading()
    }

    fun resetDeletedJokesState(){
        _jokeDeleted.value = false
    }

    fun setIdleState(){
        _viewState.value = UiStates.Idle()
    }
}