package com.carles.carleskotlin

import androidx.preference.PreferenceManager
import androidx.room.Room
import com.carles.carleskotlin.common.model.LiveDataCallAdapterFactory
import com.carles.carleskotlin.poi.model.PoiService
import com.carles.carleskotlin.poi.model.PoiRepository
import com.carles.carleskotlin.poi.viewmodel.PoiDetailViewModel
import com.carles.carleskotlin.poi.viewmodel.PoiListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

private const val BASE_URL = "https://t21services.herokuapp.com"

val appModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single { AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), MainThreadExecutor()) }
    single {
        Room.databaseBuilder(androidContext(), KotlinDatabase::class.java, "kotlin_database")
                .fallbackToDestructiveMigration()
                .build()
    }

    single {
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()
    }
}

val poiModule = module {
    single { (get() as KotlinDatabase).poiDao() }
    single { (get() as Retrofit).create(PoiService::class.java) }
    single { PoiRepository(get(), get(), get(), get()) }

    viewModel { PoiListViewModel(androidApplication(), get()) }
    viewModel { (id: String) -> PoiDetailViewModel(androidApplication(), id, get()) }
}

val modules = listOf(appModule, poiModule)