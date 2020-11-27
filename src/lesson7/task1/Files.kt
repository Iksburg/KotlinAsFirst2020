@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import kotlin.math.pow
import kotlin.text.RegexOption.IGNORE_CASE
import kotlin.text.RegexOption.LITERAL

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        if (line.firstOrNull() == '_') continue
        writer.write(line)
        writer.newLine()
    }
    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val text = File(inputName).readText()
    val result = mutableMapOf<String, Int>()
    for (element in substrings) {
        val regex = Regex(element, setOf(LITERAL, IGNORE_CASE))
        var nextMatch = regex.find(text)
        var count = 0
        while (nextMatch != null) {
            count++
            nextMatch = regex.find(text, nextMatch.range.first + 1)
        }
        result[element] = count
    }
    return result
}

/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val mapOfCorrection = mapOf(
        'ы' to 'и',
        'Ы' to 'И',
        'я' to 'а',
        'Я' to 'А',
        'ю' to 'у',
        'Ю' to 'У',
    )
    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            if (line.isNotEmpty()) it.write(line[0].toString())
            for (i in 0..line.length - 2) {
                if (listOf('ж', 'ч', 'ш', 'щ').contains(line[i].toLowerCase())
                    && mapOfCorrection.contains(line[i + 1])
                ) {
                    it.write(mapOfCorrection[line[i + 1]].toString())
                } else {
                    it.write(line[i + 1].toString())
                }
            }
            it.newLine()
        }
    }
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var maxLineLength = 0
    val listOfLine = mutableListOf<String>()
    val listOfCenters = mutableListOf<Int>()
    for (line in File(inputName).readLines()) {
        val lineLength = line.trim().length
        listOfLine.add(line)
        listOfCenters.add(lineLength / 2)
        if (lineLength > maxLineLength) maxLineLength = lineLength
    }
    val maxLineCenter = maxLineLength / 2
    File(outputName).bufferedWriter().use {
        for (i in 0 until listOfLine.size) {
            val stringWithoutSpaces = listOfLine[i].trim()
            val lineLength = stringWithoutSpaces.length
            val lineCenter = listOfCenters[i]
            val alignedLine = when {
                lineCenter >= maxLineCenter -> stringWithoutSpaces
                else -> if (maxLineLength % 2 == 0 && lineLength % 2 != 0) {
                    stringWithoutSpaces.padStart(lineLength - 1 + maxLineCenter - lineCenter)
                } else {
                    stringWithoutSpaces.padStart(lineLength + maxLineCenter - lineCenter)
                }
            }
            it.write(alignedLine)
            it.newLine()
        }
    }
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var countS = 0
        var countB = 0
        var countI = 0
        it.write("<html><body><p>")
        for (line in File(inputName).readLines()) {
            if (line.isNotEmpty()) {
                var text = line[0].toString() + line[1].toString()
                var count = 0
                var firstChar = 0
                if (line.length < 3) {
                    it.write(line)
                } else {
                    for (i in 0..line.length - 3) {
                        text += line[i + 2]
                        if (text[i + count] == '~' && text[i + 1 + count] == '~') {
                            if (countS == 0) {
                                text = text.replace("~~", "<s>")
                                countS += 1
                                count += 1
                            } else {
                                text = text.replace("~~", "</s>")
                                countS -= 1
                                count += 2
                            }
                        }
                        if (text[i + count] == '*' && text[i + 1 + count] == '*' && text[i + 2 + count] == '*') {
                            if (countB == 0 && countI == 0) {
                                text = text.replace("***", "<b><i>")
                                countB += 1
                                countI += 1
                                count += 3
                                firstChar = 2
                            } else if (countB == 1 && countI == 0) {
                                text = text.replace("***", "</b><i>")
                                countB -= 1
                                countI += 1
                                count += 4
                                firstChar = 2
                            } else if (countB == 0 && countI == 1) {
                                text = text.replace("***", "</i><b>")
                                countB += 1
                                countI -= 1
                                count += 4
                                firstChar = 1
                            } else if (firstChar == 1) {
                                text = text.replace("***", "</i></b>")
                                countB -= 1
                                countI -= 1
                                count += 5
                                firstChar = 0
                            } else if (firstChar == 2) {
                                text = text.replace("***", "</b></i>")
                                countB -= 1
                                countI -= 1
                                count += 5
                                firstChar = 0
                            }
                        } else if (text[i + count] == '*' && text[i + 1 + count] == '*') {
                            if (countB == 0) {
                                text = text.replace("**", "<b>")
                                countB += 1
                                count += 1
                                if (firstChar != 2) firstChar = 1
                            } else {
                                text = text.replace("**", "</b>")
                                countB -= 1
                                count += 2
                                if (firstChar == 1) firstChar = 0
                            }
                        } else if (text[i + count] == '*') {
                            if (countI == 0) {
                                text = text.replace("*", "<i>")
                                countI += 1
                                count += 2
                                if (firstChar != 1) firstChar = 2
                            } else {
                                text = text.replace("*", "</i>")
                                countI -= 1
                                count += 3
                                if (firstChar == 2) firstChar = 0
                            }
                        }
                    }
                }
                it.write(text)
            } else {
                it.write("</p><p>")
                continue
            }
        }
        it.write("</p></body></html>")
    }
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = (lhv * rhv).toString()
    val lengthResult = result.length
    var secondMultiplier = rhv
    var currentLength = lengthResult
    val line = "-".repeat(lengthResult + 1)
    File(outputName).bufferedWriter().use {
        it.write(lhv.toString().padStart(lengthResult + 1))
        it.newLine()
        it.write("*" + rhv.toString().padStart(lengthResult))
        it.newLine()
        it.write(line)
        it.newLine()
        while (secondMultiplier != 0) {
            val firstDigit = secondMultiplier % 10
            val number = (lhv * firstDigit).toString()
            if (currentLength == lengthResult) {
                it.write(number.padStart(currentLength + 1))
            } else {
                it.write("+" + number.padStart(currentLength))
            }
            it.newLine()
            currentLength -= 1
            secondMultiplier /= 10
        }
        it.write(line)
        it.newLine()
        it.write(result.padStart(lengthResult + 1))
    }
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = (lhv / rhv)
    var dividend = lhv.toString()
    var resultDigitCounter = digitNumber(result)
    File(outputName).bufferedWriter().use {
        var subtrahend = (result / 10.0.pow(resultDigitCounter - 1).toInt() * rhv)
        var subtrahendLength = digitNumber(subtrahend) + 1
        var remainder = when {
            lhv < rhv -> lhv.toString()
            lhv.toString().take(digitNumber(subtrahend)).toInt() >= subtrahend -> lhv.toString()
                .take(digitNumber(subtrahend))
            else -> lhv.toString().take(digitNumber(subtrahend) + 1)
        }
        val length: Int =
            if (remainder.length == digitNumber(subtrahend) + 1 || subtrahend == 0 && digitNumber(lhv) >= 2) {
                it.write("$lhv | $rhv")
                digitNumber(lhv)
            } else {
                it.write(" $lhv | $rhv")
                " $lhv | ".length
            }
        it.newLine()
        if (remainder.length == digitNumber(subtrahend) + 1 || subtrahend == 0 && digitNumber(lhv) >= 2) {
            it.write("-$subtrahend".padStart(length) + result.toString().padStart(4))
        } else {
            it.write("-$subtrahend".padEnd(length) + result)
        }
        it.newLine()
        var line = if (digitNumber(subtrahend) == remainder.length) {
            "-".repeat(remainder.length + 1)
        } else {
            "-".repeat(remainder.length)
        }
        it.write(line.padStart(subtrahendLength))
        it.newLine()
        resultDigitCounter -= 1
        dividend = dividend.drop(digitNumber(subtrahend))
        val previousRemainder = remainder
        if (resultDigitCounter != 0 && lhv >= rhv) {
            remainder = (remainder.toInt() - subtrahend).toString() + dividend[0]
            it.write(remainder.padStart(subtrahendLength + 1))
            it.newLine()
        } else {
            remainder = (remainder.toInt() - subtrahend).toString()
            it.write(remainder.padStart(subtrahendLength))
        }
        subtrahendLength += remainder.length - digitNumber((previousRemainder.toInt() - subtrahend))
        subtrahend = remainder.toInt() - remainder.toInt() % rhv
        if (resultDigitCounter != 0) {
            while (resultDigitCounter != 0) {
                it.write("-$subtrahend".padStart(subtrahendLength))
                line = if (digitNumber(subtrahend) == remainder.length) {
                    "-".repeat(remainder.length + 1)
                } else {
                    "-".repeat(remainder.length)
                }
                it.newLine()
                it.write(line.padStart(subtrahendLength))
                it.newLine()
                resultDigitCounter -= 1
                dividend = dividend.drop(1)
                if (resultDigitCounter != 0) {
                    remainder = (remainder.toInt() - subtrahend).toString() + dividend[0]
                    it.write(remainder.padStart(subtrahendLength + 1))
                    it.newLine()
                } else {
                    remainder = (remainder.toInt() - subtrahend).toString()
                    it.write(remainder.padStart(subtrahendLength))
                }
                subtrahendLength += 1
                subtrahend = remainder.toInt() - remainder.toInt() % rhv
            }
        }
    }
}