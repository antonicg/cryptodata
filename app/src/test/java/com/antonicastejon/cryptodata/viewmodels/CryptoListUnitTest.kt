package com.antonicastejon.cryptodata.viewmodels

class CryptoListUnitTest {
//    @Rule
//    @JvmField
//    val rule = InstantTaskExecutorRule()
//
//    val cryptoListUseCases = mock<CryptoListUseCase>()
//    val observerState = mock<Observer<CryptoListState>>()
//
//    val viewmodel by lazy { CryptoListViewModel(cryptoListUseCases, Schedulers.trampoline(), Schedulers.trampoline()) }
//
//    @Before
//    fun initTest() {
//        reset(cryptoListUseCases, observerState)
//    }
//
//    @Test
//    fun testCryptoList_updateCryptoList_LoadOnePage() {
//        val response = arrayListOf(CryptoViewModel())
//        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
//                .thenReturn(Single.just(response))
//
//        viewmodel.stateLiveData.observeForever(observerState)
//        viewmodel.updateCryptoList()
//
//        val firstPage = 0
//        verify(cryptoListUseCases).getCryptoListBy(firstPage)
//
//        val argumentCaptor = ArgumentCaptor.forClass(CryptoListState::class.java)
//        val expectedLoadingState = LoadingState(firstPage, false, emptyList())
//        val expectedDefaultState = DefaultState(firstPage+1, true, response)
//        argumentCaptor.run {
//            verify(observerState, times(3)).onChanged(capture())
//            val (initialState, loadingState, defaultState) = allValues
//            assertEquals(loadingState, expectedLoadingState)
//            assertEquals(defaultState, expectedDefaultState)
//        }
//    }
//
//    @Test
//    fun testCryptoList_updateCryptoList_LoadPagination() {
//        val response = limitCryptoListSizeArrayEmptyCryptoViewModel()
//        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
//                .thenReturn(Single.just(response))
//
//        viewmodel.stateLiveData.observeForever(observerState)
//        viewmodel.updateCryptoList()
//        viewmodel.updateCryptoList()
//
//        verify(cryptoListUseCases, times(2)).getCryptoListBy(ArgumentMatchers.anyInt())
//
//        val expectedFinalResponse = mutableListOf<CryptoViewModel>()
//        expectedFinalResponse.addAll(response)
//        expectedFinalResponse.addAll(response)
//
//        val argumentCaptor = ArgumentCaptor.forClass(CryptoListState::class.java)
//        val expectedPaginatingState = PaginatingState(1, false, response)
//        val expectedFinalState = DefaultState(2, false, expectedFinalResponse)
//        argumentCaptor.run {
//            verify(observerState, times(5)).onChanged(capture())
//            val (initialState, loadingState, defaultState, paginatingState, finalState) = allValues
//            assertEquals(expectedPaginatingState, paginatingState)
//            assertEquals(expectedFinalState, finalState)
//        }
//    }
//
//    @Test
//    fun testCryptoList_updateCryptoList_Error() {
//        val errorMessage = "Error response"
//        val response = Throwable(errorMessage)
//        whenever(cryptoListUseCases.getCryptoListBy(ArgumentMatchers.anyInt()))
//                .thenReturn(Single.error(response))
//
//        viewmodel.stateLiveData.observeForever(observerState)
//        viewmodel.updateCryptoList()
//
//        val page = 0
//        verify(cryptoListUseCases).getCryptoListBy(page)
//
//        val argumentCaptor = ArgumentCaptor.forClass(CryptoListState::class.java)
//        val expectedLoadingState = LoadingState(page, false, emptyList())
//        val expectedErrorState = ErrorState(errorMessage, page, false, emptyList())
//        argumentCaptor.run {
//            verify(observerState, times(3)).onChanged(capture())
//            val (initialState, loadingState, errorState) = allValues
//            assertEquals(loadingState, expectedLoadingState)
//            assertEquals(errorState, expectedErrorState)
//        }
//    }
}