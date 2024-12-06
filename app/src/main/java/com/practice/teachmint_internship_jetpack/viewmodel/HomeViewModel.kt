package com.practice.teachmint_internship_jetpack.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.teachmint_internship_jetpack.data.model.Repository
import com.practice.teachmint_internship_jetpack.data.repository.GitHubRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(
    private val repository: GitHubRepository
) : ViewModel() {

    // LiveData for the repository list fetched from the API
    val repositories = MutableLiveData<List<Repository>>()

    // LiveData for offline repositories stored in the Room DB
    val offlineRepositories = MutableLiveData<List<Repository>>()

    // Function to search repositories from GitHub API
    fun searchRepositories(query: String, page: Int) {
        repository.searchRepositories(query, page)
            .subscribeOn(Schedulers.io()) // Perform network operations on IO thread
            .observeOn(AndroidSchedulers.mainThread()) // Observe results on the main thread
            .subscribe({ result ->
                // Log the result to check the data being returned
                Log.d("HomeViewModel", "Search result: ${result.size} repositories found")

                // Map RepositoryEntity to Repository before updating LiveData
                repositories.value = result.map { entity ->
                    Repository(
                        id = entity.id,
                        name = entity.name,
                        description = entity.description,
                        projectLink = entity.projectLink,
                        owner = entity.owner
                    )
                }
            }, { error ->
                Log.e("HomeViewModel", "Error fetching repositories: ${error.message}")
            })
    }


    // Function to load offline repositories from Room DB
    fun loadOfflineData() {
        repository.getOfflineRepositories()
            .subscribeOn(Schedulers.io()) // Perform DB operations on IO thread
            .observeOn(AndroidSchedulers.mainThread()) // Observe results on the main thread
            .subscribe({ result ->
                // Map RepositoryEntity to Repository before updating LiveData
                offlineRepositories.value = result.map { entity ->
                    Repository(
                        id = entity.id,
                        name = entity.name,
                        description = entity.description,
                        projectLink = entity.projectLink,
                        owner = entity.owner
                    )
                }
            }, { error ->
                // Handle error
            })
    }
}
