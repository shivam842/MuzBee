package app.mcoders.muzbee.di

import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.datasource.RemoteDataSource
import app.mcoders.muzbee.data.datasource_impl.LocalDataSourceImpl
import app.mcoders.muzbee.data.datasource_impl.RemoteDataSourceImpl
import app.mcoders.muzbee.data.repository.MusicRepositoryImpl
import app.mcoders.muzbee.data.repository_impl.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMusicRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MusicRepository = MusicRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideLocalDataSource(): LocalDataSource = LocalDataSourceImpl()

    @Provides
    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl()
}
