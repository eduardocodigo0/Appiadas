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

class RandomJokeViewModel(val repository: JokeRepository): ViewModel() {


    private val _viewState = MutableStateFlow<UiStates<Joke>>(UiStates.Idle())
    val viewState get() = _viewState

    private val _jokeSaved = MutableStateFlow<Boolean>(false)
    val jokeSaved get() = _jokeSaved


    fun saveJoke(joke: Joke){
        viewModelScope.launch(Dispatchers.IO){
            _jokeSaved.value = try {
                repository.saveJoke(joke)
                true
            }catch (err: Exception){
                false
            }
        }
    }


    fun getRandomJoke(){
        viewModelScope.launch(Dispatchers.IO){
            setLoadingState()

            try{
                requestAndEmitJoke()
            } catch(err: Exception){
                emitErrorMsg(err)
            }
        }
    }

    private suspend fun requestAndEmitJoke(){
        var joke = repository.getRandomJoke().toJoke()
        var count = 10
        while(joke.setup == null && count > 0){
            joke = repository.getRandomJoke().toJoke()
            count -= 1
        }
        _viewState.value = UiStates.Success(joke)
    }

    private fun emitErrorMsg(err: Exception){
        _viewState.value = UiStates.Error(
            err.message.toString()
        )
    }

    private fun setLoadingState(){
        _viewState.value = UiStates.Loading()
    }

    fun setIdleState(){
        _viewState.value = UiStates.Idle()
    }

    fun resetJokeSavedState(){
        _jokeSaved.value = false
    }


}