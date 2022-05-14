package com.example.githubapp.models.viewModels

import androidx.lifecycle.*

import androidx.paging.PagingData
import com.example.githubapp.data.network.GetRepositoriesRxRepository
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.searchParams.SearchRepositoriesParams
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModel @Inject constructor(
    private val repository: GetRepositoriesRxRepository
): ViewModel() {

    private val query = MutableLiveData<String>()

    fun setQuery(queryData: String) {
        query.value = queryData
    }

    fun getQuery(): LiveData<String> {
        return query
    }

    fun getRepositories(): Flowable<PagingData<Repository>> {
        return repository
            .getRepositories(SearchRepositoriesParams(q = query.value.toString()))
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<SearchViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SearchViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }
}