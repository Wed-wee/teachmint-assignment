package com.practice.teachmint_internship_jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.practice.teachmint_internship_jetpack.data.local.DatabaseProvider
import com.practice.teachmint_internship_jetpack.ui.theme.Teachmint_internship_jetpackTheme
import com.practice.teachmint_internship_jetpack.userinterfaces.HomeScreen
import com.practice.teachmint_internship_jetpack.userinterfaces.DetailsScreen
import com.practice.teachmint_internship_jetpack.data.model.Repository
import com.practice.teachmint_internship_jetpack.data.remote.GitHubApi
import com.practice.teachmint_internship_jetpack.viewmodel.DetailsViewModel
import com.practice.teachmint_internship_jetpack.viewmodel.HomeViewModel
import com.practice.teachmint_internship_jetpack.data.repository.GitHubRepository
import com.practice.teachmint_internship_jetpack.data.local.RepositoryDao
import com.practice.teachmint_internship_jetpack.data.remote.ApiClient

class MainActivity : ComponentActivity() {

    private lateinit var gitHubRepository: GitHubRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the API and DAO
        val gitHubApi: GitHubApi = ApiClient.api
        val dao: RepositoryDao = DatabaseProvider.getInstance(applicationContext).repositoryDao()

        // Initialize GitHubRepository
        gitHubRepository = GitHubRepository(gitHubApi, dao)

        setContent {
            Teachmint_internship_jetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Set up the navigation
                    val navController = rememberNavController()

                    // Initialize HomeViewModel and load offline data
                    val homeViewModel = HomeViewModel(gitHubRepository)
                   // Call to load offline data

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                viewModel = homeViewModel, // Pass HomeViewModel to HomeScreen
                                onRepoClick = { repo ->
                                    // Navigate to details screen and pass the repository ID
                                    navController.navigate("details/${repo.id}")
                                }
                            )
                        }
                        composable("details/{repoId}") { backStackEntry ->
                            val repoId = backStackEntry.arguments?.getString("repoId")?.toInt()
                            val repo = getRepositoryById(repoId) // Fetch your repository by ID
                            if (repo != null) {
                                DetailsScreen(
                                    viewModel = DetailsViewModel(gitHubRepository), // Pass GitHubRepository to DetailsViewModel
                                    repo = repo,
                                    context = applicationContext
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Mock function to get repository by ID
    private fun getRepositoryById(id: Int?): Repository? {
        // Replace with your actual logic to retrieve the repository
        return id?.let {
            Repository(id = it, name = "Repo #$id", description = "Description of Repo #$id", projectLink = "http://projectlink.com", owner = "Owner Name")
        }
    }
}
