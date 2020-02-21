package ru.spbstu.kilosophers.part2.concurrent

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.spbstu.kilosophers.part2.AdvancedAbstractFork
import ru.spbstu.kilosophers.part2.AdvancedAbstractKilosopher
import java.util.concurrent.Executors

private class AdvancedConcurrentFork : AdvancedAbstractFork() {
    private val index = forkNumber++

    private val forkContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher() + CoroutineName("Fork $index")

    override var owner: AdvancedAbstractKilosopher? = null

    override suspend fun tryTake(who: AdvancedAbstractKilosopher): Boolean {
        if (owner != null) return false
        return withContext(forkContext) {
            if (owner == null) {
                owner = who
                true
            } else {
                false
            }
        }
    }

    override suspend fun tryDrop(who: AdvancedAbstractKilosopher): Boolean {
        if (owner != who) return false
        return withContext(forkContext) {
            if (owner != who) {
                false
            } else {
                owner = null
                true
            }
        }
    }

    companion object {
        var forkNumber = 0
    }
}

fun createConcurrentFork(): AdvancedAbstractFork = AdvancedConcurrentFork()