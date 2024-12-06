package com.practice.teachmint_internship_jetpack.userinterfaces

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.rememberImagePainter
import com.practice.teachmint_internship_jetpack.data.model.Contributor
import com.practice.teachmint_internship_jetpack.data.model.Repository
import com.practice.teachmint_internship_jetpack.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, repo: Repository, context: Context) {
    // Observe the contributors list from the ViewModel
    val contributors by viewModel.contributors.observeAsState(emptyList())

    Column {
        // Repository name and description
        Text(repo.name, style = MaterialTheme.typography.headlineMedium)
        Text(repo.description ?: "No description")

        // Button to open WebView for project link
        TextButton(onClick = { viewModel.fetchContributors(repo) }) {
            Text("Open Project Link")
        }

        // List of contributors
        LazyColumn {
            items(contributors) { contributor ->
                Row {
                    // Contributor's avatar image
                    Image(painter = rememberImagePainter(contributor.avatarUrl), contentDescription = null)

                    // Contributor's login name
                    Text(contributor.login)
                }
            }
        }
    }
}

// Helper function to open the project link in the browser

