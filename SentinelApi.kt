package com.zenith.sentinel

import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Universal Sentinel Bridge.
 * Links 5 diverse API domains into a single orchestration interface.
 */
interface SentinelApi {

    @GET
    suspend fun getIpLocation(@Url url: String): IpApiResponse

    @GET
    suspend fun getTime(@Url url: String): WorldTimeResponse

    @GET
    suspend fun getZipData(@Url url: String): ZipResponse

    @GET
    suspend fun searchBooks(@Url url: String): OpenLibraryResponse

    @GET
    suspend fun getJoke(@Url url: String): JokeResponse
}