package com.practice.teachmint_internship_jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.teachmint_internship_jetpack.data.model.Contributor
import com.practice.teachmint_internship_jetpack.data.model.Repository
import com.practice.teachmint_internship_jetpack.data.repository.GitHubRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsViewModel(private val repository: GitHubRepository) : ViewModel() {

    // LiveData for contributors
    val contributors: LiveData<List<Contributor>> = MutableLiveData()

    // Fetch contributors for a given repository
    fun fetchContributors(repo: Repository) {
        repository.getContributors(repo.owner, repo.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ contributorsList ->
                (contributors as MutableLiveData).value = contributorsList
            }, { error ->
                // Handle error
            })
    }
}
