@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt
import kotlin.math.pow

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sum = 0.0
    for (element in v) {
        sum += element.pow(2)
    }
    return sqrt(sum)
}

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    if (list.isEmpty()) {
        0.0
    } else {
        list.sum() / list.size
    }

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> = TODO()

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int = TODO()

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var result = 0.0
    val number = x.toDouble()
    for ((index, element) in p.withIndex()) {
        result += number.pow(index) * element
    }
    return result.toInt()
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var divider = 2
    var number = n
    val multiplierList = mutableListOf<Int>()
    do {
        while (number % divider == 0) {
            multiplierList.add(divider)
            number /= divider
        }
        divider++
    } while (number > 1)
    return multiplierList
}

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String = TODO()

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var degree = digits.size - 1
    var result = 0.0
    val newBase = base.toDouble()
    for (element in digits) {
        result += element * newBase.pow(degree)
        degree--
    }
    return result.toInt()
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun deleteFirstDigit(n: Int): Int {
    var number = n
    var divider = 1
    while (number / divider > 9) divider *= 10
    number %= divider
    return number
}

fun roman(n: Int): String {
    val unit = listOf("I", "V")
    val ten = listOf("X", "L")
    val hundred = listOf("C", "D", "M")
    val romanNumber = StringBuilder()
    var number = n

    do {
        when {
            number > 999 -> {
                for (i in 1..number / 1000) {
                    romanNumber.append(hundred[2])
                }
            }
            number > 899 -> {
                romanNumber.append(hundred[0] + hundred[2])
            }
            number > 499 -> {
                romanNumber.append(hundred[1])
                if (number > 599) {
                    for (i in 1..number / 100 - 5) {
                        romanNumber.append(hundred[0])
                    }
                }
            }
            number > 399 -> {
                romanNumber.append(hundred[0] + hundred[1])
            }
            number > 99 -> {
                for (i in 1..number / 100) {
                    romanNumber.append(hundred[0])
                }
            }
            number > 89 -> {
                romanNumber.append(ten[0] + hundred[0])
            }
            number > 49 -> {
                romanNumber.append(ten[1])
                if (number > 59) {
                    for (i in 1..number / 10 - 5) {
                        romanNumber.append(ten[0])
                    }
                }
            }
            number > 39 -> {
                romanNumber.append(ten[0] + ten[1])
            }
            number > 9 -> {
                for (i in 1..number / 10) {
                    romanNumber.append(ten[0])
                }
            }
            number == 9 -> {
                romanNumber.append(unit[0] + ten[0])
            }
            number > 4 -> {
                romanNumber.append(unit[1])
                if (number > 5) {
                    for (i in 1..number - 5) {
                        romanNumber.append(unit[0])
                    }
                }
            }
            number == 4 -> {
                romanNumber.append(unit[0] + unit[1])
            }
            else -> {
                for (i in 1..number) {
                    romanNumber.append(unit[0])
                }
            }
        }
        number = deleteFirstDigit(number)
    } while (number >= 1)

    return romanNumber.toString()
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun notEmpty(n: StringBuilder): String {
    return if (n.isNotEmpty()) {
        " "
    } else {
        ""
    }
}

fun russian(n: Int): String {
    val unit = listOf("один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")

    val firstTen = listOf(
        "десять",
        "одиннадцать",
        "двенадцать",
        "тринадцать",
        "четырнадцать",
        "пятнадцать",
        "шестнадцать",
        "семнадцать",
        "восемнадцать",
        "девятнадцать"
    )

    val ten = listOf(
        "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
    )

    val hundred = listOf(
        "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"
    )

    val thousand = listOf(
        "одна тысяча",
        "две тысячи",
        "три тысячи",
        "четыре тысячи",
        "пять тысяч",
        "шесть тысяч",
        "семь тысяч",
        "восемь тысяч",
        "девять тысяч"
    )

    val numberInWords = StringBuilder()
    var number = n

    if (number == 0) {
        numberInWords.append("нуль")
    } else {
        do {
            when {
                number > 99999 -> {
                    numberInWords.append(
                        if (number / 1000 % 100 == 0) {
                            hundred[number / 100000 - 1] + " тысяч"
                        } else {
                            hundred[number / 100000 - 1]
                        }
                    )
                }
                number > 19999 -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(
                        if (number / 1000 % 10 == 0) {
                            ten[number / 10000 - 2] + " тысяч"
                        } else {
                            ten[number / 10000 - 2]
                        }
                    )
                }
                number > 9999 -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(firstTen[number / 1000 - 10] + " тысяч")
                    if (number / 1000 != 10) {
                        number = deleteFirstDigit(number)
                    }
                }
                number > 999 -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(thousand[number / 1000 - 1])
                }
                number > 99 -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(hundred[number / 100 - 1])
                }
                number > 19 -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(ten[number / 10 - 2])
                }
                number > 9 -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(firstTen[number % 10])
                    number = deleteFirstDigit(number)
                }
                else -> {
                    numberInWords.append(notEmpty(numberInWords))
                    numberInWords.append(unit[number - 1])
                }
            }
            number = deleteFirstDigit(number)
        } while (number >= 1)
    }
    return numberInWords.toString()
}