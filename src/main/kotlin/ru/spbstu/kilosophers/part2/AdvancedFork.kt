package ru.spbstu.kilosophers.part2

interface AdvancedFork {
    val owner: AdvancedAbstractKilosopher?

    val left: AdvancedAbstractKilosopher

    val right: AdvancedAbstractKilosopher

    suspend fun tryTake(who: AdvancedAbstractKilosopher): Boolean

    suspend fun tryDrop(who: AdvancedAbstractKilosopher): Boolean

    fun isFree(): Boolean = owner == null
}