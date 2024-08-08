package app.mcoders.muzbee.di

import android.content.ContentResolver
import android.content.Context
import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.datasource.RemoteDataSource
import app.mcoders.muzbee.data.datasource_impl.LocalDataSourceImpl
import app.mcoders.muzbee.data.datasource_impl.RemoteDataSourceImpl
import app.mcoders.muzbee.data.repository_impl.MusicRepositoryImpl
import app.mcoders.muzbee.data.repository.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideMusicRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MusicRepository = MusicRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideLocalDataSource(contentResolver: ContentResolver): LocalDataSource = LocalDataSourceImpl(contentResolver)

    @Provides
    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl()
}
