package com.skyqgo.recruitmenttest.di

import android.content.Context
import com.skyqgo.recruitmenttest.data.MovieRepositoryImpl
import com.skyqgo.recruitmenttest.data.NetworkDataSource
import com.skyqgo.recruitmenttest.BuildConfig
import com.skyqgo.recruitmenttest.data.RemoteDataSource
import com.skyqgo.recruitmenttest.data.MovieRepository
import com.skyqgo.recruitmenttest.data.WebService
import com.skyqgo.recruitmenttest.data.network.CacheInterceptor
import com.skyqgo.recruitmenttest.data.network.MovieApi
import com.skyqgo.recruitmenttest.data.network.RetrofitWebService
import com.skyqgo.recruitmenttest.domain.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ActivityComponent::class)
@Module
object AppModule {

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOKHttpClient(@ApplicationContext appContext: Context, loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient().apply {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(appContext.cacheDir, cacheSize)
        OkHttpClient.Builder().run {
            cache(cache)
            addNetworkInterceptor(CacheInterceptor())
            addInterceptor(loggingInterceptor)
        }
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient,
        moshiConverterFactory: GsonConverterFactory
    ): MovieApi =
        Retrofit.Builder().run {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(moshiConverterFactory)
            client(client)
            build()
        }.run {
            create(MovieApi::class.java)
        }

    @Provides
    fun providesRetrofitService(movieApi: MovieApi): WebService =
        RetrofitWebService(movieApi)

    @Provides
    fun providesNetworkDataSource(webService: WebService): RemoteDataSource =
        NetworkDataSource(webService)

    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource): MovieRepository =
        MovieRepositoryImpl(remoteDataSource)

    @Provides
    fun provideUseCase(repository: MovieRepository): GetMoviesUseCase =
        GetMoviesUseCase(repository)
}