package ru.spbstu.kilosophers.part2.atomic

import ru.spbstu.kilosophers.part2.AdvancedForkBox
import ru.spbstu.kilosophers.part2.AdvancedAbstractFork

object AdvancedAtomicForkBox : AdvancedForkBox {
    override fun produce(vararg args: Any): AdvancedAbstractFork = AdvancedAtomicFork()
}