package com.kuba.mealky.presentation.commons.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<UI : BaseUI> : Presenter<UI> {

    protected val disposable = CompositeDisposable()

    protected val eventDisposable = CompositeDisposable()

    private val uiExecutor: UICommandExecutor<UI> = UICommandExecutor()

    private var attachedBefore = false

    protected open fun onFirstAttachment() {

    }

    override fun attach(ui: UI) {
        uiExecutor.attach(ui)
        if (!attachedBefore) {
            onFirstAttachment()
            attachedBefore = true
        }
    }

    override fun detach() {
        uiExecutor.detach()
    }


    override fun destroy() {
        disposable.clear()
        eventDisposable.clear()
    }

    protected fun ui(): SafeUI<UI> = uiExecutor
}