package com.antares.marvelheroes.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.antares.marvelheroes.data.characters.CharacterResponse
import com.antares.marvelheroes.data.characters.Result
import com.antares.marvelheroes.network.CharactersException
import com.antares.marvelheroes.network.IComicRepository
import com.antares.marvelheroes.ui.utils.ResponseFactory
import com.antares.marvelheroes.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ComicViewModelTest {

    @get: Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var comicRepository: IComicRepository

    private var testCoroutineDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: ComicViewModel

    @Mock
    private lateinit var characterListObserver: Observer<Resource<List<Result>>>

    @Before
    fun setUp(){
        viewModel = ComicViewModel(comicRepository)
    }

    @Test
    fun `when calling for characters, then return loading` () = runTest {
        viewModel.characters.observeForever(characterListObserver)
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel.getCharacters()
        verify(characterListObserver).onChanged(Resource.Loading)
    }

    @Test
    fun `when calling form characters, then return failure`() = runTest {
        val expected = Resource.Failure(CharactersException().message)
        viewModel.characters.observeForever(characterListObserver)
        whenever(comicRepository.getCharacters()).thenThrow(CharactersException())
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel.getCharacters()
        assertEquals(expected,Resource.Failure(null))
    }

    @Test
    fun `when calling for upcoming movies, then return data successfully` () = runTest {
        val expected = ResponseFactory.characterResponse()
        viewModel.characters.observeForever(characterListObserver)
        whenever(comicRepository.getCharacters()).thenReturn(expected)
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel.getCharacters()
        assertNotNull(viewModel.characters.value)
        assertEquals(Resource.Success(expected.data.results), viewModel.characters.value)
    }

    private fun removeObservers(){
        viewModel.characters.removeObserver(characterListObserver)
    }

    @After
    fun tearDown(){
        removeObservers()
    }
}