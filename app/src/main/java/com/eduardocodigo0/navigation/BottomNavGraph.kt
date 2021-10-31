package com.eduardocodigo0.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eduardocodigo0.screens.FavoritesScreen
import com.eduardocodigo0.screens.HomeScreen
import com.eduardocodigo0.util.NavigationDestination

@Composable
fun BottomNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Home.destination
    ){
        composable(route = NavigationDestination.Home.destination){
            HomeScreen()
        }

        composable(route = NavigationDestination.Favorites.destination){
            FavoritesScreen()
        }
    }
}