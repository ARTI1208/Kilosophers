package ru.spbstu.kilosophers.part2

// Produces kilosophers
interface AdvancedUniversity {
    fun produce(left: AdvancedAbstractFork, right: AdvancedAbstractFork, vararg args: Any): AdvancedAbstractKilosopher
}