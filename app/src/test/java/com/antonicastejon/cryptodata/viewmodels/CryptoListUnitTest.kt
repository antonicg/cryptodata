package com.antonicastejon.cryptodata.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.antonicastejon.cryptodata.common.SchedulerImmediate
import com.antonicastejon.cryptodata.common.mock
import com.antonicastejon.cryptodata.common.oneSizeArrayEmptyCryptoViewModel
import com.antonicastejon.cryptodata.common.whenever
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.presentation.main.crypto_list.*
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify

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

    val viewmodel by lazy { CryptoListViewModel(cryptoListUseCases, SchedulerImmediate(), SchedulerImmediate()) }

    @Test
    fun testCryptoList_updateCryptoList_LoadingState() {
        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.just(emptyList()));

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val firstPage = 0
        verify(cryptoListUseCases).getCryptoListBy(firstPage)
        verify(observerState, atLeastOnce()).onChanged(CryptoListState(LOADING, firstPage, false, emptyList()))
    }

    @Test
    fun testCryptoList_updateCryptoList_Success() {
        val response = oneSizeArrayEmptyCryptoViewModel()
        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.just(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val firstPage = 0
        verify(cryptoListUseCases).getCryptoListBy(firstPage)
        verify(observerState, atLeastOnce()).onChanged(CryptoListState(DEFAULT, firstPage + 1, true, response))
    }

    // TODO: Test state pagination not working
//    @Test
//    fun testCryptoList_updateCryptoList_Pagination() {
//        val response = limitCryptoListSizeArrayEmptyCryptoViewModel()
//        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
//                .thenReturn(Single.just(response))
//
//        viewmodel.stateLiveData.observeForever(observerState)
//        viewmodel.updateCryptoList()
//        viewmodel.updateCryptoList()
//
//        val currentExpectedCryptoList = mutableListOf<CryptoViewModel>()
//        currentExpectedCryptoList.addAll(response)
//        currentExpectedCryptoList.addAll(response)
//
//        val pageNum = 1
//        verify(cryptoListUseCases).getCryptoListBy(pageNum)
//        verify(observerState, atLeastOnce()).onChanged(CryptoListState(PAGINATING, pageNum, false, response))
//    }

    @Test
    fun testCryptoList_updateCryptoList_Error() {
        val response = Throwable("Error response")
        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.error(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val page = 0
        verify(cryptoListUseCases).getCryptoListBy(page)
        verify(observerState, atLeastOnce()).onChanged(CryptoListState(ERROR_API, page, false, emptyList()))
    }
}