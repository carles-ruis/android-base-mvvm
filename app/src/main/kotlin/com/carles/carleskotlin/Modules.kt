package com.carles.carleskotlin

import android.arch.persistence.room.Room
import android.preference.PreferenceManager
import com.carles.carleskotlin.poi.datasource.PoiCloudDatasource
import com.carles.carleskotlin.poi.datasource.PoiLocalDatasource
import com.carles.carleskotlin.poi.datasource.PoiService
import com.carles.carleskotlin.poi.repository.PoiRepository
import com.carles.carleskotlin.poi.ui.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://t21services.herokuapp.com"

val appModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single("uiScheduler") { AndroidSchedulers.mainThread() }
    single("processScheduler") { Schedulers.io() }
    single {
        Room.databaseBuilder(androidContext(), KotlinDatabase::class.java, "kotlin_database").fallbackToDestructiveMigration().build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

val poiModule = module {
    single { (get() as KotlinDatabase).PoiDao() }
    single { PoiLocalDatasource(get(), get()) }
    single { (get() as Retrofit).create(PoiService::class.java) }
    single { PoiCloudDatasource(get(), get()) }
    single { PoiRepository(get(), get()) }
    viewModel { PoiListViewModel(get()) }
    viewModel { (id: String) -> PoiDetailViewModel(id,get()) }
    factory { (view: PoiListView) -> PoiListPresenter(view, get("uiScheduler"), get("processScheduler"), get()) }
    factory { (view: PoiDetailView, id: String) -> PoiDetailPresenter(view, id, get("uiScheduler"), get("processScheduler"), get()) }
}

val modules = listOf(appModule, poiModule)