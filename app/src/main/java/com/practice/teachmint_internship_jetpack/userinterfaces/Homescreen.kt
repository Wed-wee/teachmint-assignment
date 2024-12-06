package com.practice.teachmint_internship_jetpack.userinterfaces

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import com.practice.teachmint_internship_jetpack.data.model.Repository
import com.practice.teachmint_internship_jetpack.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, onRepoClick: (Repository) -> Unit) {
    // State to hold the query entered by the user
    val query = remember { mutableStateOf("") }

    // Observing repositories from the ViewModel
    val repositories by viewModel.repositories.observeAsState(emptyList())

    Log.d("HomeScreen", "Repositories: ${repositories.size}")

    Column {
        // Search bar to enter query
        TextField(
            value = query.value,
            onValueChange = { newQuery ->
                query.value = newQuery // Update query state
                Log.d("HomeScreen", "Search query: $newQuery") // Log query to check if it's updating correctly
                viewModel.searchRepositories(newQuery, 1) // Trigger search
            },
            label = { Text("Search Repositories") },
            modifier = Modifier.padding(16.dp)
        )

        // Handle case where no results are found
        if (repositories.isEmpty()) {
            Log.d("HomeScreen", "No repositories found")
            Text("No repositories found for your search u need to search in searchbar", modifier = Modifier.padding(16.dp))
        } else {
            Log.d("HomeScreen", "Displaying ${repositories.size} repositories")
        }

        // Displaying the list of repositories
        LazyColumn {
            items(repositories) { repo ->
                Log.d("HomeScreen", "Rendering repository: ${repo.name}")
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onRepoClick(repo) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(repo.name, style = MaterialTheme.typography.bodyLarge)
                        Text(repo.description ?: "No description", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
