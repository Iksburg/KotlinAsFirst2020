@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson1.task1.sqr

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    constructor(s: String) : this(
        Regex("""(-?\d+\.?\d*)""").find(s)?.value.orEmpty().toDouble(),
        Regex("""([-+]\d+\.?\d*i)""").find(s)?.value.orEmpty().substringBefore('i').toDouble()
    )

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(this.re + other.re, this.im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-this.im, -this.re)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(this.re - other.re, this.im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        Complex(this.re * other.re - this.im * other.im, this.im * other.re + this.re * other.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex =
        Complex(
            (this.re * other.re + this.im * other.im) / (sqr(other.re) + sqr(other.im)),
            (this.im * other.re - this.re * other.im) / (sqr(other.re) + sqr(other.im))
        )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Complex) return false
        return this.re == other.re && this.im == other.im
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = (this.im.toString() + {
        if (this.re < 0) {
            "-"
        } else {
            "+"
        }
    } + this.re.toString())

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
