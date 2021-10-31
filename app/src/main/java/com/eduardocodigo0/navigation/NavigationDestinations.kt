package com.eduardocodigo0.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mood
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDestination(val destination: String, val icon: ImageVector, val title: String){
    object Home: NavigationDestination("home", Icons.Filled.Mood, "Random Joke")
    object Favorites: NavigationDestination("favorites", Icons.Filled.Favorite, "Saved Jokes")
}