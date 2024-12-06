package com.practice.teachmint_internship_jetpack.data.repository

import android.util.Log
import com.practice.teachmint_internship_jetpack.data.local.RepositoryDao
import com.practice.teachmint_internship_jetpack.data.local.RepositoryEntity
import com.practice.teachmint_internship_jetpack.data.model.Contributor
import com.practice.teachmint_internship_jetpack.data.remote.GitHubApi
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class GitHubRepository(
    private val api: GitHubApi,
    private val dao: RepositoryDao
) {
    fun searchRepositories(query: String, page: Int): Single<List<RepositoryEntity>> {
        return api.searchRepositories(query, page)
            .doOnSuccess { response ->
                Log.d("GitHubRepository", "API Response: ${response.items.size} repositories found")
            }
            .map { response ->
                // Map the API response to RepositoryEntity
                response.items.map {
                    RepositoryEntity(
                        id = it.id,
                        name = it.name,
                        description = it.description ?: "No description", // Add default if null
                        projectLink = it.html_url ?: "No project link", // Map html_url to projectLink
                        owner = it.owner.login // Map owner.login to owner string
                    )
                }
            }
    }

    fun getContributors(owner: String, repo: String): Single<List<Contributor>> {
        return api.getContributors(owner, repo)
    }

    fun getOfflineRepositories(): Flowable<List<RepositoryEntity>> {
        return dao.getAllRepositories()
    }
}
