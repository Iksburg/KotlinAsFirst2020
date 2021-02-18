@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

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
    private var table = mutableListOf<Couple>()

    private class Couple(
        val x: Double,
        var y: Double
    )

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
            table.add(Couple(x, y))
        } else {
            for (i in 0 until table.size) {
                if (x == table[i].x) {
                    table[i].y = y
                    return false
                }
            }
            table.add(Couple(x, y))
        }
        return true
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        for (i in 0 until table.size) {
            if (x == table[i].x) {
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
            result.add(Pair(table[i].x, table[i].y))
        }
        return result
    }

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? = TODO()

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double = TODO()

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
                if (firstTable[i].x == secondTable[j].x && firstTable[i].y == secondTable[j].y) {
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