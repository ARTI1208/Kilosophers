package ru.spbstu.kilosophers.part2

// Produces forks
interface AdvancedForkBox {
    fun produce(vararg args: Any): AdvancedAbstractFork
}