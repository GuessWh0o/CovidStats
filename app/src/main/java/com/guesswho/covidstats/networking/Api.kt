package com.guesswho.covidstats.networking
import com.guesswho.covidstats.model.CasesResponse
import retrofit2.http.GET

/**
 *
 * @author Maxim on 2020-01-29
 */
interface Api {
    @GET("/all")
    suspend fun getAll(): CasesResponse
}