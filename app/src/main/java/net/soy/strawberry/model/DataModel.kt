package net.soy.strawberry.model

import io.reactivex.Single
import net.soy.strawberry.enum.KakaoSearchSortEnum
import net.soy.strawberry.network.model.ImageSearchResponse

interface DataModel {
    fun getData(query:String, sort: KakaoSearchSortEnum, page:Int, size:Int): Single<ImageSearchResponse>
}