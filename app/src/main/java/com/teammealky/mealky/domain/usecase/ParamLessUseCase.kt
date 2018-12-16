package com.teammealky.mealky.domain.usecase

import com.teammealky.mealky.domain.executor.UseCaseExecutor
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject


abstract class ParamLessUseCase<Result> {

    @Inject open lateinit var executor: UseCaseExecutor

    protected abstract fun doWork(): Single<Result>

    open fun asSingle(): Single<Result> = doWork()
            .compose(executor.applySingleSchedulers())

    fun execute(
            onNext: (t: Result) -> Unit,
            onError: (e: Throwable) -> Unit
    ): Disposable = asSingle()
            .subscribe(onNext, onError)

}