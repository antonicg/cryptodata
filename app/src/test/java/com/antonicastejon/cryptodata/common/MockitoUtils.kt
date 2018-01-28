package com.antonicastejon.cryptodata.common

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

/**
 * Created by Antoni Castejón
 * 26/01/2018.
 */
inline fun <reified T> mock() = Mockito.mock(T::class.java)
inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> = Mockito.`when`(methodCall)