package com.antares.marvelheroes.di

import com.antares.marvelheroes.network.ComicRepository
import com.antares.marvelheroes.network.IComicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        repository: ComicRepository
    ) : IComicRepository
}