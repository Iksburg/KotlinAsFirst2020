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
    private var table = mutableListOf<Pair<Double, Double>>()

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
            for (i in 0 until table.size) {
                if (x == table[i].first) {
                    val newPair = Pair(x, y)
                    table[i] = newPair
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
        for (i in 0 until table.size) {
            if (x == table[i].first) {
                table.removeAt(i)
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
        for (i in 0 until table.size) {
            result.add(Pair(table[i].first, table[i].second))
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
            for (i in 0 until table.size) {
                if (abs(x - table[i].first) < minDifValue) {
                    minDifValue = abs(x - table[i].first)
                    result = Pair(table[i].first, table[i].second)
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
            1 -> return table[0].second
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
                for (i in 0 until table.size) {
                    if (x == table[i].first) return table[i].second
                    if (x > table[i].first) {
                        quantityNumLess++
                        if (table[i].first > minFirstValue) {
                            minSecondValue = minFirstValue
                            minFirstValue = table[i].first
                            secondMinIndex = firstMinIndex
                            firstMinIndex = i
                        } else if (table[i].first > minSecondValue) {
                            minSecondValue = table[i].first
                            secondMinIndex = i
                        }
                    }
                    if (x < table[i].first) {
                        quantityNumMore++
                        if (table[i].first < maxFirstValue) {
                            maxSecondValue = maxFirstValue
                            maxFirstValue = table[i].first
                            secondMaxIndex = firstMaxIndex
                            firstMaxIndex = i
                        } else if (table[i].first < maxSecondValue) {
                            maxSecondValue = table[i].first
                            secondMaxIndex = i
                        }
                    }
                }
                return when {
                    quantityNumLess > 0 && quantityNumMore > 0 ->
                        (x - minFirstValue) * (table[firstMaxIndex].second - table[firstMinIndex].second) /
                                (maxFirstValue - minFirstValue) + table[firstMinIndex].second
                    quantityNumLess > 1 ->
                        (x - minSecondValue) * (table[firstMinIndex].second - table[secondMinIndex].second) /
                                (minFirstValue - minSecondValue) + table[secondMinIndex].second
                    else ->
                        (x - maxFirstValue) * (table[secondMaxIndex].second - table[firstMaxIndex].second) /
                                (maxSecondValue - maxFirstValue) + table[firstMaxIndex].second
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
        for (i in 0 until firstTable.size) {
            for (j in 0 until firstTable.size) {
                if (firstTable[i].first == secondTable[j].first && firstTable[i].second == secondTable[j].second) {
                    countOfEquals++
                }
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