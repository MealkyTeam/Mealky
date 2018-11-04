package com.teammealky.mealky.domain.executor

import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

interface UseCaseExecutor {
    fun <R> applySchedulers(): ObservableTransformer<R, R>
    fun <R> applyMainThreadSchedulers(): ObservableTransformer<R, R>
    fun <R> applySingleSchedulers(): SingleTransformer<R, R>
    fun <R> applySingleMainThreadSchedulers(): SingleTransformer<R, R>
}