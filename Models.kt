
package com.zenith.sentinel

import com.google.gson.annotations.SerializedName

// 1. IP-API Response
data class IpApiResponse(
    val status: String,
    val country: String,
    val countryCode: String,
    val regionName: String,
    val city: String,
    val zip: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val isp: String,
    val org: String,
    val query: String
)

// 2. WorldTimeAPI Response
data class WorldTimeResponse(
    val datetime: String,
    @SerializedName("day_of_week") val dayOfWeek: Int,
    @SerializedName("utc_datetime") val utcDatetime: String,
    val timezone: String
)

// 3. Zippopotam Response
data class ZipResponse(
    @SerializedName("post code") val postCode: String,
    val country: String,
    val places: List<ZipPlace>
)

data class ZipPlace(
    @SerializedName("place name") val placeName: String,
    val longitude: String,
    val state: String,
    val latitude: String
)

// 4. OpenLibrary Response
data class OpenLibraryResponse(
    val numFound: Int,
    val docs: List<BookDoc>
)

data class BookDoc(
    val title: String,
    @SerializedName("author_name") val authorName: List<String>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?
)

// 5. JokeAPI Response
data class JokeResponse(
    val error: Boolean,
    val category: String,
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?
)

// Internal Logic Model
data class SentinelResult(
    val mode: String,
    val status: String,
    val location: String,
    val contextInfo: String,
    val recommendation: String
)
