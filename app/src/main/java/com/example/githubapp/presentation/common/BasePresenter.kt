package com.example.githubapp.presentation.common

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView
import timber.log.Timber

open class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun unsubscribeOnDestroy(disposable: Disposable, num: Int) {
        compositeDisposable.add(disposable)
        Timber.w("Reposi num disposable = $num")
        Timber.w("Reposi count of disposable = ${compositeDisposable.size()}")
    }

    override fun destroyView(view: T) {
        super.destroyView(view)
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}