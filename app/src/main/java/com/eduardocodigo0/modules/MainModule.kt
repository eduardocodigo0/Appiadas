package com.eduardocodigo0.modules

import com.eduardocodigo0.db.JokeRepository
import com.eduardocodigo0.viewmodel.FavoriteJokesViewModel
import com.eduardocodigo0.viewmodel.RandomJokeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { JokeRepository(get()) }

    viewModel { RandomJokeViewModel(get()) }
    viewModel { FavoriteJokesViewModel(get()) }
}