package com.teammealky.mealky.data.executor

import com.teammealky.mealky.domain.executor.PostExecutionThread
import com.teammealky.mealky.domain.executor.ThreadExecutor
import com.teammealky.mealky.domain.executor.UseCaseExecutor
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InteractorExecutor @Inject constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
): UseCaseExecutor {

    override fun <R> applySchedulers(): ObservableTransformer<R, R> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.scheduler)
        }
    }

    override fun <R> applyMainThreadSchedulers(): ObservableTransformer<R, R> {
        return ObservableTransformer {
            it.subscribeOn(postExecutionThread.scheduler)
                    .observeOn(postExecutionThread.scheduler)
        }
    }

    override fun <R> applySingleSchedulers(): SingleTransformer<R, R> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.scheduler)
        }
    }

    override fun <R> applySingleMainThreadSchedulers(): SingleTransformer<R, R> {
        return SingleTransformer {
            it.subscribeOn(postExecutionThread.scheduler)
                    .observeOn(postExecutionThread.scheduler)
        }
    }

}