package com.kuba.mealky.database.repositories

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface Repository{
    var compositeDisposable:CompositeDisposable
    var observable:Observable
}