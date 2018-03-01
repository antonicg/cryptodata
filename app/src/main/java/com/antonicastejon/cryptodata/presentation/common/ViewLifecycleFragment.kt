package com.antonicastejon.cryptodata.presentation.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class ViewLifecycleFragment : Fragment() {

    var lifecycleOwner: FragmentLifecycleOwner? = null

    protected fun getFragmentLifecycleOwner() = lifecycleOwner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleOwner = FragmentLifecycleOwner(this)
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        lifecycleOwner = null
        super.onDestroyView()
    }
}

class FragmentLifecycleOwner(val provider: LifecycleOwner) : LifecycleOwner {

    val lifecycleRegystry: LifecycleRegistry = LifecycleRegistry(provider)
    override fun getLifecycle() = lifecycleRegystry
}