package ru.spbstu.kilosophers.part2.concurrent

import ru.spbstu.kilosophers.part2.AdvancedForkBox
import ru.spbstu.kilosophers.part2.AdvancedAbstractFork

object AdvancedConcurrentForkBox : AdvancedForkBox {
    override fun produce(vararg args: Any): AdvancedAbstractFork = createConcurrentFork()
}