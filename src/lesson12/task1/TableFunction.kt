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
    private var table = mutableSetOf<Pair<Double, Double>>()

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
        if (table.size == 0) {
            table.add(Pair(x, y))
        } else {
            for (pair in table) {
                if (x == pair.first) {
                    val newPair = Pair(x, y)
                    table.remove(pair)
                    table.add(newPair)
                    return false
                }
            }

            table.add(Pair(x, y))
        }
        return true
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        for (pair in table) {
            if (x == pair.first) {
                table.remove(pair)
                return true
            }
        }
        return false
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> {
        val result = mutableListOf<Pair<Double, Double>>()
        for (pair in table) {
            result.add(pair)
        }
        return result
    }

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        if (table.size == 0) {
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
            1 -> return table.first().second
            else -> {
                var quantityNumLess = 0
                var quantityNumMore = 0
                var firstMinIndex = Integer.MAX_VALUE
                var secondMinIndex = Integer.MAX_VALUE
                var firstMaxIndex = Integer.MIN_VALUE
                var secondMaxIndex = Integer.MIN_VALUE
                var minFirstValue = Double.MIN_VALUE
                var maxFirstValue = Double.MAX_VALUE
                var minSecondValue = Double.MIN_VALUE
                var maxSecondValue = Double.MAX_VALUE
                for (pair in table) {
                    if (x == pair.first) return pair.second
                    if (x > pair.first) {
                        quantityNumLess++
                        if (pair.first > minFirstValue) {
                            minSecondValue = minFirstValue
                            minFirstValue = pair.first
                            secondMinIndex = firstMinIndex
                            firstMinIndex = table.indexOf(pair)
                        } else if (pair.first > minSecondValue) {
                            minSecondValue = pair.first
                            secondMinIndex = table.indexOf(pair)
                        }
                    }
                    if (x < pair.first) {
                        quantityNumMore++
                        if (pair.first < maxFirstValue) {
                            maxSecondValue = maxFirstValue
                            maxFirstValue = pair.first
                            secondMaxIndex = firstMaxIndex
                            firstMaxIndex = table.indexOf(pair)
                        } else if (pair.first < maxSecondValue) {
                            maxSecondValue = pair.first
                            secondMaxIndex = table.indexOf(pair)
                        }
                    }
                }
                return when {
                    quantityNumLess > 0 && quantityNumMore > 0 ->
                        (x - minFirstValue) * (table.elementAt(firstMaxIndex).second -
                                table.elementAt(firstMinIndex).second) /
                                (maxFirstValue - minFirstValue) + table.elementAt(firstMinIndex).second
                    quantityNumLess > 1 ->
                        (x - minSecondValue) * (table.elementAt(firstMinIndex).second -
                                table.elementAt(secondMinIndex).second) /
                                (minFirstValue - minSecondValue) + table.elementAt(secondMinIndex).second
                    else ->
                        (x - maxFirstValue) * (table.elementAt(secondMaxIndex).second -
                                table.elementAt(firstMaxIndex).second) /
                                (maxSecondValue - maxFirstValue) + table.elementAt(firstMaxIndex).second
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
        val firstTable = table
        val secondTable = other.table
        var countOfEquals = 0
        if (firstTable.size != secondTable.size) return false
        for (pair in firstTable) {
            if (pair in secondTable) {
                countOfEquals++
            }
        }
        return countOfEquals == firstTable.size
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + size
        return result
    }

}