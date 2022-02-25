package com.example.githubapp.presentation.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

open class BasePresenter<T : MvpView>: MvpPresenter<T>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun unsubscribeOnDestroy(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}