package com.example.githubapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.githubapp.models.mappers.RepositoryMapper
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.searchParams.SearchRepositoriesParams
import com.example.githubapp.presentation.common.SchedulersProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import javax.inject.Inject

class PageSource @AssistedInject constructor(
    private val apiService: GithubApiService,
    @Assisted("searchRepositoriesParams") private val searchRepositoriesParams: SearchRepositoriesParams,
    private val repositoryMapper: RepositoryMapper,
    private val schedulersProvider: SchedulersProvider
) : RxPagingSource<Int, Repository>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Repository>> {
        if (searchRepositoriesParams.q.isEmpty()) {
            LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        return apiService.getRepositories(
            q = searchRepositoriesParams.q,
            per_page = pageSize,
            page = page
        )
            .subscribeOn(schedulersProvider.io())
            .doOnSuccess {
                Timber.d("loadSingle(): #1 repositories \"${searchRepositoriesParams.q}\" from API = ${it.items}") }
            .map { repositoryResponse ->
                repositoryResponse.items
            }
            .doOnSuccess {
                Timber.d("loadSingle(): #2 repositories \"${searchRepositoriesParams.q}\" from API = $it") }
            .map { listRepositoriesFromApi ->
                listRepositoriesFromApi.map { repository ->
                    repositoryMapper.fromApiRepository(repository)
                }
            }
            .doOnSuccess {
                Timber.d("loadSingle(): #3 repositories \"${searchRepositoriesParams.q}\" from API = $it") }
            .map { toLoadResult(it, page, pageSize) }
            .doOnSuccess {
                Timber.d("loadSingle(): #3 repositories \"${searchRepositoriesParams.q}\" from API = $it") }
            .onErrorReturn {
                    it -> Timber.e("getRepositoriesFromGithubApiService(): ${it.localizedMessage}")
                LoadResult.Error(it) }
    }

    private fun toLoadResult(it: List<Repository>, page: Int, pageSize: Int): LoadResult<Int, Repository> {
        return LoadResult.Page(
            data = it,
            nextKey = if (it.size < pageSize) null else page + 1,
            prevKey = if (page == 1) null else page - 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        val anchorPosition = state.anchorPosition?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("searchRepositoriesParams") searchRepositoriesParams: SearchRepositoriesParams): PageSource
    }
}