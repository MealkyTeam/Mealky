package com.teammealky.mealky.data.repository.datasource

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.presentation.commons.extension.isOlderThan
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsLocalSource @Inject constructor() : MealsRepository {

    private val cache: MutableMap<Int, MealsResponseCache> = mutableMapOf()
    val emptyItem = Page.emptyPage<Meal>()

    override fun searchMeals(query: String, page: Int, limit: Int): Single<Page<Meal>> {
        return Single.defer {
            val cacheKey = getKey(query, page, limit)
            val cacheItem = cache[cacheKey]

            if (null == cacheItem) {
                Single.just(emptyItem)
            } else {
                val isCacheStale = cacheItem.createdAt.isOlderThan(CACHE_TTL_MIN)
                if (isCacheStale) {
                    cache.remove(cacheKey)
                    Single.just(emptyItem)
                } else {
                    Single.just(cacheItem.model)
                }
            }
        }
    }

    fun setMeals(
            query: String,
            item: Page<Meal>
    ) {
        val cacheKey = getKey(query, item.pageNumber, item.limit)
        val cacheItem = MealsResponseCache(query, Date(), item)
        cache[cacheKey] = cacheItem
        removeStaleItems()
    }

    private fun getKey(
            query: String,
            page: Int,
            limit: Int
    ): Int = "$query-$page-$limit".hashCode()

    private fun removeStaleItems() {
        val keysToDeletion = ArrayList<Int>()
        cache.forEach {
            val cacheKey = it.key
            val cacheItem = it.value
            val isCacheStale = cacheItem.createdAt.isOlderThan(CACHE_TTL_MIN)
            if (isCacheStale) keysToDeletion.add(cacheKey)
        }

        keysToDeletion.forEach { key -> cache.remove(key) }
    }

    private data class MealsResponseCache(
            val query: String,
            val createdAt: Date,
            val model: Page<Meal>
    )

    override fun invalidate() {
        cache.clear()
    }

    companion object {
        private const val CACHE_TTL_MIN = 15
    }
}