package net.soy.latestproject.network

import io.reactivex.Single
import net.soy.strawberry.network.model.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchService {

    @GET("/v2/search/image")
    fun searchImage(@Header("Authorization") auth:String,
                    @Query("query") query:String,
                    @Query("sort") sort:String,
                    @Query("page") page:Int,
                    @Query("size") size:Int): Single<ImageSearchResponse>

}