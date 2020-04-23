package net.soy.strawberry.di

import net.soy.strawberry.network.KakaoSearchService
import net.soy.strawberry.network.RetrofitFactory
import net.soy.strawberry.model.DataModel
import net.soy.strawberry.model.DataModelImpl
import net.soy.strawberry.adapter.MainSearchRecyclerViewAdapter
import net.soy.strawberry.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

//single{} -> 싱글톤패턴처럼 어플리케이션에서 단 하나만 만듭니다. 저는 보통 Retrofit을 통해 만든 서비스 클래스를 single로 만듭니다.
val retrofitPart = module {
    single<KakaoSearchService> {
        RetrofitFactory.build().create(KakaoSearchService::class.java)
    }
}

var adapterPart = module {
    factory {
        MainSearchRecyclerViewAdapter()
    }
}

//factory{} -> DataModelImpl() 를 만들어줌
var modelPart = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel(get())
    }
}

var myDiModule = listOf(retrofitPart, adapterPart, modelPart, viewModelPart)