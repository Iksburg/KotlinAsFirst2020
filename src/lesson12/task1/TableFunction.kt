@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import kotlin.math.abs

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {
    private val table = mutableMapOf<Double, Double>()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = table.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        if (table.isEmpty()) {
            table[x] = y
        } else {
            if (x in table) {
                table[x] = y
                return false
            }
            table[x] = y
        }
        return true
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        if (x in table) {
            table.remove(x)
            return true
        }
        return false
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = table.toList()


    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        if (table.isEmpty()) {
            throw IllegalStateException()
        } else {
            var minDifValue: Double = Double.MAX_VALUE
            var result: Pair<Double, Double>? = null
            for ((first, second) in table) {
                if (abs(x - first) < minDifValue) {
                    minDifValue = abs(x - first)
                    result = Pair(first, second)
                }
            }
            return result
        }
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        when (table.size) {
            0 -> throw IllegalStateException()
            1 -> return table.values.first()
            else -> {
                var quantityNumLess = 0
                var quantityNumMore = 0
                var minFirstKey = Double.MIN_VALUE
                var maxFirstKey = Double.MAX_VALUE
                var minSecondKey = Double.MIN_VALUE
                var maxSecondKey = Double.MAX_VALUE
                for ((key, value) in table) {
                    if (x == key) return value
                    if (x > key) {
                        quantityNumLess++
                        if (key > minFirstKey) {
                            minSecondKey = minFirstKey
                            minFirstKey = key
                        } else if (key > minSecondKey) {
                            minSecondKey = key
                        }
                    }
                    if (x < key) {
                        quantityNumMore++
                        if (key < maxFirstKey) {
                            maxSecondKey = maxFirstKey
                            maxFirstKey = key
                        } else if (key < maxSecondKey) {
                            maxSecondKey = key
                        }
                    }
                }
                val minFirstValue = table[minFirstKey]
                val minSecondValue = table[minSecondKey]
                val maxFirstValue = table[maxFirstKey]
                val maxSecondValue = table[maxSecondKey]
                return when {
                    quantityNumLess > 0 && quantityNumMore > 0 && maxFirstValue != null && minFirstValue != null ->
                        (x - minFirstKey) * (maxFirstValue - minFirstValue) /
                                (maxFirstKey - minFirstKey) + minFirstValue
                    quantityNumLess > 1 && maxFirstValue != null && minSecondValue != null ->
                        (x - minSecondKey) * (maxFirstValue - minSecondValue) /
                                (minFirstKey - minSecondKey) + minSecondValue
                    else -> {
                        if (maxSecondValue != null && maxFirstValue != null) {
                            (x - maxFirstKey) * (maxSecondValue - maxFirstValue) /
                                    (maxSecondKey - maxFirstKey) + maxFirstValue
                        } else {
                            throw IllegalArgumentException()
                        }
                    }
                }
            }
        }
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TableFunction) return false
        return table == other.table
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + size
        return result
    }
}