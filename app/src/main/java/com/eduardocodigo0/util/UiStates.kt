package com.eduardocodigo0.util

sealed class UiStates<out T>{
    class Success<T>(val content: T): UiStates<T>()
    class Error(val errorMsg: String): UiStates<Nothing>()
    class Loading(): UiStates<Nothing>()
    class Idle(): UiStates<Nothing>()
}
