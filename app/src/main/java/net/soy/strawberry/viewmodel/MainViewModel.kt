package net.soy.strawberry.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.soy.strawberry.enum.KakaoSearchSortEnum
import net.soy.strawberry.model.DataModel
import net.soy.strawberry.network.model.ImageSearchResponse
import net.soy.strawberry.base.BaseKtViewModel

class MainViewModel(private val model: DataModel): BaseKtViewModel(){

    companion object{
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val _imageSearchResponseLiveData = MutableLiveData<ImageSearchResponse>()
    val imageSearchResponseLiveData: LiveData<ImageSearchResponse>
        get() = _imageSearchResponseLiveData

    fun getImageSearch(query: String, page: Int, size: Int){
        addDisposable(model.getData(query, KakaoSearchSortEnum.Recency, page, size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    if(documents.size > 0) {
                        Log.d(TAG, "document: $documents")
                        _imageSearchResponseLiveData.postValue(this)
                    }
                    Log.d(TAG, "meta : $meta")
                }
            },{
                Log.d(TAG, "response error, message : ${it.message}")
            }))
    }

}