package com.example.githubapp.presentation.githubRepositories

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.signIn.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentRepositoriesBinding
import com.example.githubapp.models.SearchViewModel
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.presentation.githubRepositories.pageAdapter.RepositoriesPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class RepositoriesFragment : MvpAppCompatFragment(R.layout.fragment_repositories),
    RepositoriesView {

    private lateinit var binding: FragmentRepositoriesBinding

    @Inject
    lateinit var presenterProvider: Provider<RepositoriesPresenter>
    private val repositoriesPresenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var signInGoogleHandler: SignInGoogleHandler

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach()")
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")
        binding = FragmentRepositoriesBinding.bind(view)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        Timber.i("onDestroy()")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_repository, menu)
        Timber.i("onCreateOptionsMenu()")
        //Search
        val findItem = menu.findItem(R.id.menu_search)
        val searchView = findItem.actionView as SearchView
        searchView.queryHint = "Type here to search"

        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

        val disposable = Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    Timber.d("onQueryTextChange: $newText")
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    Timber.d("onQueryTextSubmit: $query")
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
            //debounce(timeout, unit, Schedulers.computation())
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { text -> text.isNotBlank() }
            .observeOn(schedulersProvider.ui())
            .doOnNext { searchViewModel.setQuery(it) }
            .subscribe { text ->
                Timber.d("subscriber: $text")
            }
        compositeDisposable.add(disposable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                if (signInGoogleHandler.isClientSigned()) signInGoogleHandler.signOut()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading(show: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun displaySavedRepositories(authorizedUser: Boolean) {
        val repositoriesPageAdapter =
            RepositoriesPageAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                authorizedUser
            )
        binding.viewPager2.adapter = repositoriesPageAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.search)
                else -> tab.text = getString(R.string.saved)
            }
        }.attach()
    }
}
