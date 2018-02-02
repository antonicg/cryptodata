package com.antonicastejon.cryptodata.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.antonicastejon.cryptodata.common.SchedulerImmediate
import com.antonicastejon.cryptodata.common.limitCryptoListSizeArrayEmptyCryptoViewModel
import com.antonicastejon.cryptodata.common.mock
import com.antonicastejon.cryptodata.common.whenever
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.presentation.main.crypto_list.*
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 * Created by Antoni Castejón
 * 21/01/2018.
 */

class CryptoListUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val cryptoListUseCases = mock<CryptoListUseCases>()
    val observerState = mock<Observer<CryptoListState>>()

    lateinit var viewmodel:CryptoListViewModel

    @Before
    fun initTest() {
        viewmodel = CryptoListViewModel(cryptoListUseCases, SchedulerImmediate(), SchedulerImmediate())
        reset(cryptoListUseCases, observerState)
    }

    @Test
    fun testCryptoList_updateCryptoList_LoadOnePage() {
        val response = arrayListOf(CryptoViewModel())
        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.just(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val firstPage = 0
        verify(cryptoListUseCases).getCryptoListBy(firstPage)

        val argumentCaptor = ArgumentCaptor.forClass(CryptoListState::class.java)
        val expectedLoadingState = LoadingState(firstPage, false, emptyList())
        val expectedDefaultState = DefaultState(firstPage+1, true, response)
        argumentCaptor.run {
            verify(observerState, times(3)).onChanged(capture())
            val (initialState, loadingState, defaultState) = allValues
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }

    @Test
    fun testCryptoList_updateCryptoList_LoadPagination() {
        val response = limitCryptoListSizeArrayEmptyCryptoViewModel()
        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.just(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()
        viewmodel.updateCryptoList()

        verify(cryptoListUseCases, times(2)).getCryptoListBy(ArgumentMatchers.anyInt())

        val expectedFinalResponse = mutableListOf<CryptoViewModel>()
        expectedFinalResponse.addAll(response)
        expectedFinalResponse.addAll(response)

        val argumentCaptor = ArgumentCaptor.forClass(CryptoListState::class.java)
        val expectedPaginatingState = PaginatingState(1, false, response)
        val expectedFinalState = DefaultState(2, false, expectedFinalResponse)
        argumentCaptor.run {
            verify(observerState, times(5)).onChanged(capture())
            val (initialState, loadingState, defaultState, paginatingState, finalState) = allValues
            assertEquals(expectedPaginatingState, paginatingState)
            assertEquals(expectedFinalState, finalState)
        }
    }

    @Test
    fun testCryptoList_updateCryptoList_Error() {
        val errorMessage = "Error response"
        val response = Throwable(errorMessage)
        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.error(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val page = 0
        verify(cryptoListUseCases).getCryptoListBy(page)

        val argumentCaptor = ArgumentCaptor.forClass(CryptoListState::class.java)
        val expectedLoadingState = LoadingState(page, false, emptyList())
        val expectedErrorState = ErrorState(errorMessage, page, false, emptyList())
        argumentCaptor.run {
            verify(observerState, times(3)).onChanged(capture())
            val (initialState, loadingState, errorState) = allValues
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(errorState, expectedErrorState)
        }
    }
}