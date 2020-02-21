package ru.spbstu.kilosophers.part2

enum class AdvancedActionKind {
    THINK,
    TAKE_LEFT,
    TAKE_RIGHT,
    TAKE_SERVIETTE,
    DROP_LEFT,
    DROP_RIGHT,
    DROP_SERVIETTE,
    EAT;

    operator fun invoke(duration: Int) = AdvancedAction(this, duration)
}

data class AdvancedAction(val kind: AdvancedActionKind, val duration: Int)