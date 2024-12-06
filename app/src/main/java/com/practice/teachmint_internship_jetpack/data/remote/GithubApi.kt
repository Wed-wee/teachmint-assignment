package com.practice.teachmint_internship_jetpack.data.remote

import com.practice.teachmint_internship_jetpack.data.model.Contributor
import com.practice.teachmint_internship_jetpack.data.model.SearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun searchRepositories(

        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): Single<SearchResponse>

    @GET("repos/{owner}/{repo}/contributors")
    fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<Contributor>>
}
