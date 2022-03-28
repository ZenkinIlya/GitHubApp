package com.example.githubapp.presentation.menuRepositories

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.signIn.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentRepositoriesBinding
import com.example.githubapp.models.viewModels.SearchViewModel
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.presentation.adapters.RepositoriesPageAdapter
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

    private var showMenuItems: Boolean = false
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView()")
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        repositoriesPresenter.init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")
        setHasOptionsMenu(true)
    }
    override fun onStart() {
        super.onStart()
        Timber.i("onStart()")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop()")
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        compositeDisposable.clear()
        Timber.i("onDestroyOptionsMenu()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.i("onDetach()")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_delete_base).isVisible = showMenuItems
        menu.findItem(R.id.menu_delete_all_base).isVisible = showMenuItems
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_repository, menu)
        Timber.i("onCreateOptionsMenu()")
        //Search
        val findItem = menu.findItem(R.id.menu_search)
        val searchView = findItem.actionView as SearchView
        searchView.queryHint = "Type here to search"

        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        if (searchViewModel.getQuery().value != null){
            findItem.expandActionView()
            searchView.clearFocus()
            Timber.d("searchViewModel.getQuery().value=${searchViewModel.getQuery().value}")
            searchView.setQuery(searchViewModel.getQuery().value, false)
        }

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
            .observeOn(schedulersProvider.ui())
            .doOnNext { searchViewModel.setQuery(it) }
            .subscribe(
                { text ->
                    Timber.d("subscriber: $text")
                },
                { Timber.e("onError: $it") })
        compositeDisposable.add(disposable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                if (signInGoogleHandler.isClientSigned()) signInGoogleHandler.signOut()
                findNavController().popBackStack()
                true
            }
            R.id.menu_delete_base -> {
                repositoriesPresenter.onDeleteSavedRepositories()
                true
            }
            R.id.menu_delete_all_base -> {
                repositoriesPresenter.onDeleteSavedRepositoriesByAllUsers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading(show: Boolean) {

    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        Timber.e(error)
    }

    override fun displayViewPageRepositories(authorized: Boolean) {
        Timber.d("displayViewPageRepositories(): $authorized")
        val repositoriesPageAdapter =
            RepositoriesPageAdapter(
                childFragmentManager,
                viewLifecycleOwner.lifecycle,
                authorized
            )
        binding.viewPager2.adapter = repositoriesPageAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.search)
                else -> tab.text = getString(R.string.saved)
            }
        }.attach()

        showMenuItems = authorized
        activity?.invalidateOptionsMenu()
    }
}
