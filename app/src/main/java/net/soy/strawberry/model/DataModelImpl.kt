package net.soy.strawberry.model

import io.reactivex.Single
import net.soy.latestproject.network.KakaoSearchService
import net.soy.strawberry.enum.KakaoSearchSortEnum
import net.soy.strawberry.network.model.ImageSearchResponse

class DataModelImpl(private val service: KakaoSearchService): DataModel{
    private val KAKAO_API_KEY = "a4a7dc0b83f626751bdba54aa76936d5"

    override fun getData(query:String, sort: KakaoSearchSortEnum, page:Int, size:Int): Single<ImageSearchResponse> {
        return service.searchImage(auth = "KakaoAK $KAKAO_API_KEY", query = query, sort = sort.sort, page = page, size = size)
    }
}