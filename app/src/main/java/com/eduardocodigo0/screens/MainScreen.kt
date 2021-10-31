package com.eduardocodigo0.screens


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eduardocodigo0.R
import com.eduardocodigo0.navigation.BottomNavGraph
import com.eduardocodigo0.util.NavigationDestination

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name), fontSize = 18.sp)
        },
        backgroundColor = colorResource(id = R.color.black),
        contentColor = colorResource(id = R.color.white)
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val items = listOf(
        NavigationDestination.Home,
        NavigationDestination.Favorites
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.black),
        contentColor = colorResource(id = R.color.white)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        contentDescription = item.title,
                        imageVector = item.icon
                    )
                },
                label = { Text(text = item.title) },
                selectedContentColor = Color.Yellow,
                unselectedContentColor = Color.White,
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.destination
                } == true,
                onClick = {
                    navController.navigate(item.destination)
                })
        }
    }
}