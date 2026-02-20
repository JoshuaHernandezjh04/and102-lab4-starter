package com.codepath.campgrounds

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CampgroundResponse(
    @SerialName("data")
    val data: List<Campground>? = null
)

@Keep
@Serializable
data class Campground(
    @SerialName("name")
    val name: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("latLong")
    val latLong: String? = null,

    @SerialName("images")
    val images: List<CampgroundImage>? = null
) : java.io.Serializable {

    // first valid image url (or empty)
    val imageUrl: String
        get() = images?.firstOrNull { !it.url.isNullOrEmpty() }?.url ?: ""
}

@Keep
@Serializable
data class CampgroundImage(
    @SerialName("url") val url: String? = null,
    @SerialName("title") val title: String? = null
) : java.io.Serializable