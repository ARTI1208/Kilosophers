package ru.spbstu.kilosophers.part2.atomic

import ru.spbstu.kilosophers.part2.AdvancedAbstractFork
import ru.spbstu.kilosophers.part2.AdvancedAbstractKilosopher
import java.util.concurrent.atomic.AtomicReference

class AdvancedAtomicFork : AdvancedAbstractFork() {

    private val ownerReference = AtomicReference<AdvancedAbstractKilosopher>()

    override val owner: AdvancedAbstractKilosopher?
        get() = ownerReference.get()

    override suspend fun tryTake(who: AdvancedAbstractKilosopher): Boolean = ownerReference.compareAndSet(null, who)

    override suspend fun tryDrop(who: AdvancedAbstractKilosopher): Boolean = ownerReference.compareAndSet(who, null)
}