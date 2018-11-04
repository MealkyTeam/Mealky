package com.teammealky.mealky.domain.usecase

import com.teammealky.mealky.domain.executor.UseCaseExecutor
import com.teammealky.mealky.domain.model.ErrorResponse
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class SingleUseCase<Param, Result> {

    @Inject open lateinit var executor: UseCaseExecutor

    protected abstract fun doWork(param: Param): Single<Result>

    open fun asSingle(param: Param): Single<Result> = doWork(param).compose(executor.applySingleSchedulers())

    fun execute(param: Param,
                onNext: (t: Result) -> Unit,
                onError: (e: Throwable) -> Unit,
                onUnavailableError: ((e: ErrorResponse) -> Unit)? = null
    ): Disposable = asSingle(param)
            .doOnError { e ->
                if (e is ErrorResponse && e.isUnavailable) onUnavailableError?.invoke(e)
            }
            .subscribe(onNext, onError)
}