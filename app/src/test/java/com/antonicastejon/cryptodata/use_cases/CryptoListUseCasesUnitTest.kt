package com.antonicastejon.cryptodata.use_cases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.antonicastejon.cryptodata.common.cryptoPOJOmodel
import com.antonicastejon.cryptodata.common.cryptoViewModelFrom
import com.antonicastejon.cryptodata.common.mock
import com.antonicastejon.cryptodata.common.whenever
import com.antonicastejon.cryptodata.domain.CryptoListInteractor
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class CryptoListUseCasesUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val coinMarketCapRepository = mock<CoinMarketCapRepository>()

    val cryptoListUseCases by lazy { CryptoListInteractor(coinMarketCapRepository) }

    @Test
    fun testCryptoListUseCases_getCryptoList_Completed() {
        whenever(coinMarketCapRepository.getCryptoList(anyInt(), anyInt()))
                .thenReturn(Single.just(emptyList()))

        cryptoListUseCases.getCryptoListBy(0)
                .test()
                .assertComplete()
    }

    @Test
    fun testCryptoListUseCases_getCryptoList_Error() {
        val response = Throwable("Error response")
        whenever(coinMarketCapRepository.getCryptoList(anyInt(), anyInt()))
                .thenReturn(Single.error(response))

        cryptoListUseCases.getCryptoListBy(0)
                .test()
                .assertError(response)

    }

    @Test
    fun testCryptoListUseCases_getCryptoList_response() {
        val response = arrayListOf(cryptoPOJOmodel())
        whenever(coinMarketCapRepository.getCryptoList(anyInt(), anyInt()))
                .thenReturn(Single.just(response))

        val expectedList = arrayListOf(cryptoViewModelFrom(cryptoPOJOmodel()))

        cryptoListUseCases.getCryptoListBy(0)
                .test()
                .assertValue(expectedList)
    }
}