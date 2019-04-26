package com.teammealky.mealky.domain.model

import com.google.gson.annotations.SerializedName

data class Images(@SerializedName("images") val urls: List<String>)