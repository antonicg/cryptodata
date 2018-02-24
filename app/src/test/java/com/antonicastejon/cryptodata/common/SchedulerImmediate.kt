package com.antonicastejon.cryptodata.common

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import java.util.concurrent.TimeUnit

class SchedulerImmediate: Scheduler() {
    override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
        return super.scheduleDirect(run, 0, unit)
    }

    override fun createWorker(): Worker = ExecutorScheduler.ExecutorWorker(Runnable::run)
}