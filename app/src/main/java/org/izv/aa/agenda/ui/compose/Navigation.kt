package org.izv.aa.agenda.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.izv.aa.agenda.model.repository.ContactFileRepository
import org.izv.aa.agenda.ui.viewModel.ContactFileViewModel


@Composable
    fun MainScreen() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Navigation(innerPadding)
        }
    }

    @Composable
    fun Navigation(innerPadding: PaddingValues) {
        val context = LocalContext.current //me da el contexto dentro del navigation
        val repository = ContactFileRepository(context)
        val viewModel: ContactFileViewModel = viewModel(
            factory = object : ViewModelProvider.Factory {
                override fun <T: ViewModel> create (modelClass: Class<T>): T{
                    return ContactFileViewModel(repository) as T
                }
            }
        )
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home-screen"
        ) {
            composable("Home-screen") {
                HomeScreen(navController, viewModel, innerPadding)
            }
            composable("Add-contact") {
                AddContact(navController,  viewModel, innerPadding)
            }
            composable("Edit-contact") {
                EditContact(navController,  viewModel, innerPadding)
            }
            composable("ContactItem") {
                ContactItem(navController)
            }

        }
    }
