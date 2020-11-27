package lesson7.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.io.File

class Tests {

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    @Tag("Example")
    fun alignFile() {
        alignFile("input/align_in1.txt", 50, "temp.txt")
        assertFileContent(
            "temp.txt",
            """Для написания разных видов программ сейчас
применяются разные языки программирования.
Например, в сфере мобильных программ сейчас правят
бал языки Swift (мобильные устройства под
управлением iOS) и Java (устройства под
управлением Android). Системные программы, как
правило, пишутся на языках C или {cpp}. Эти же
языки долгое время использовались и для создания
встраиваемых программ, но в последние годы в этой
области набирает популярность язык Java. Для
написания web-клиентов часто используется
JavaScript, а в простых случаях -- язык разметки
страниц HTML. Web-серверы используют опять-таки
Java (в сложных случаях), а также Python и PHP (в
более простых). Наконец, простые desktop-программы
сейчас могут быть написаны на самых разных языках,
и выбор во многом зависит от сложности программы,
области её использования, предполагаемой
операционной системы. В первую очередь следует
назвать языки Java, {cpp}, C#, Python, Visual
Basic, Ruby, Swift.

Самым универсальным и одновременно самым
распространённым языком программирования на данный
момент следует считать язык Java. Java в широком
смысле -- не только язык, но и платформа для
выполнения программ под самыми разными
операционными системами и на разной аппаратуре.
Такая универсальность обеспечивается наличием
виртуальной машины Java -- системной программы,
интерпретирующей Java байт-код в машинные коды
конкретного компьютера или системы. Java также
включает богатейший набор библиотек для
разработки."""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("8")
    fun deleteMarked() {
        deleteMarked("input/delete_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """Задачи _надо_ решать правильно,

и не надо при этом никуда торопиться___
            """.trimIndent()
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("14")
    fun countSubstrings() {
        assertEquals(
            mapOf("--" to 4, "ее" to 2, "животное" to 2, "." to 2),
            countSubstrings("input/substrings_in2.txt", listOf("--", "ее", "животное", "."))
        )
        assertEquals(
            mapOf("РАЗНЫЕ" to 2, "ные" to 2, "Неряшливость" to 1, "е" to 49, "эволюция" to 0),
            countSubstrings("input/substrings_in1.txt", listOf("РАЗНЫЕ", "ные", "Неряшливость", "е", "эволюция"))
        )
        assertEquals(
            mapOf("Карминовый" to 2, "Некрасивый" to 2, "белоглазый" to 1),
            countSubstrings("input/substrings_in1.txt", listOf("Карминовый", "Некрасивый", "белоглазый"))
        )
    }

    @Test
    @Tag("12")
    fun sibilants() {
        sibilants("input/sibilants_in2.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """ааажажжжж"""
        )
        sibilants("input/sibilants_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """/**
 * Простая
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жУри, броШУра, параШут) в рамках данного задания обрабатывать не нужно
 *
 * жИ шИ ЖИ Ши ЖА шА Жа ша жу шу жу щу ча шу щу ща жа жи жи жу чу ча
 */"""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("15")
    fun centerFile() {
        centerFile("input/center_in6.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """                               ПБЫА
                               ПБЫА
                ПБЫА, ААБ, ААБ -- пбыа / ааб  Пбыа
ПБЫА --- ааб -- ааБ / пбыа -- пбыа ааб -- ААБ, ааБ: пбыа ААБ, Пбыа"""
        )
        centerFile("input/center_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """              Съешь же ещё этих мягких французских булок, да выпей чаю.
Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.
                                        Тест
                                          """ +  // Avoiding trailing whitespaces problem
                    """
                                     Hello World
           Во входном файле с именем inputName содержится некоторый текст.
        Вывести его в выходной файл с именем outputName, выровняв по центру."""
        )
        centerFile("input/center_in5.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """ БА
Ба -"""
        )
        centerFile("input/center_in4.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """оцвгаббавб
   Ао:"""
        )
        centerFile("input/center_in3.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """АБ -"""
        )
        centerFile("input/center_in2.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """аааааааба"""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("20")
    fun alignFileByWidth() {
        alignFileByWidth("input/width_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """Простая

Во       входном       файле       с       именем       inputName       содержится       некоторый      текст.
Вывести   его  в  выходной  файл  с  именем  outputName,  выровняв  по  левому  и  правому  краю  относительно
самой                                              длинной                                             строки.
Выравнивание   производить,   вставляя  дополнительные  пробелы  между  словами:  равномерно  по  всей  строке

Слова     внутри     строки     отделяются     друг     от     друга     одним     или     более     пробелом.

Следующие                   правила                   должны                  быть                  выполнены:
1)     Каждая     строка     входного    и    выходного    файла    не    должна    заканчиваться    пробелом.
2) Пустые строки или строки из пробелов во входном файле должны превратиться в пустые строки в выходном файле.
3)   Число   строк   в   выходном  файле  должно  быть  равно  числу  строк  во  входном  (в  т.  ч.  пустых).

Равномерность              определяется              следующими             формальными             правилами:
1)  Число  пробелов  между  каждыми  двумя  парами  соседних  слов  не  должно  отличаться  более,  чем  на 1.
2)  Число  пробелов  между  более  левой  парой  соседних  слов  должно  быть  больше или равно числу пробелов
между                более               правой               парой               соседних               слов."""
        )
        File("temp.txt").delete()

    }

    @Test
    @Tag("14")
    fun top20Words() {
        assertEquals(mapOf<String, Int>(), top20Words("input/empty.txt"))
        assertEquals(mapOf(
            "привет" to 4,
            "все" to 3,
            "и" to 3,
            "прямо" to 3,
            "всё" to 2,
            "let" to 2,
            "us" to 2,
            "write" to 2,
            "some" to 2,
            "digits" to 2
        ), top20Words("input/top20.txt").filter { it.value > 1 })
        assertEquals(
            mapOf(
                "и" to 1106,
                "в" to 674,
                "не" to 411,
                "он" to 306,
                "на" to 290,
                "я" to 261,
                "с" to 261,
                "как" to 211,
                "но" to 210,
                "что" to 187,
                "все" to 131,
                "к" to 130,
                "она" to 126,
                "его" to 109,
                "за" to 105,
                "то" to 104,
                "а" to 98,
                "ее" to 95,
                "мне" to 95,
                "уж" to 95,
                "ей" to 95
            ), top20Words("input/onegin.txt")
        )
    }

    @Test
    @Tag("14")
    fun transliterate() {
        transliterate(
            "input/trans_in1.txt",
            mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!"),
            "temp.txt"
        )
        assertFileContent("temp.txt", "Zzdrавствуy,\nmyyr!!!")
        File("temp.txt").delete()

        transliterate(
            "input/trans_in1.txt",
            mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!"),
            "temp.txt"
        )
        assertFileContent("temp.txt", "Zzdrавствуy,\nmyyr!!!")
        File("temp.txt").delete()
    }

    @Test
    @Tag("12")
    fun chooseLongestChaoticWord() {
        chooseLongestChaoticWord("input/chaotic_in1.txt", "temp.txt")
        assertFileContent("temp.txt", "Карминовый, Некрасивый")
        File("temp.txt").delete()
    }


    private fun checkHtmlSimpleExample() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """
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
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    private fun checkHtmlSimpleExample2() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """<html><body><p>T<i><b>D</b>.^<b>[`%nO<s></s>U<s>Ef</s></b>@M<s>b1@n<b>&S+,V</b>2Kb</s>\"h<s>p<b>ek</b>vbxFkFy7q!<b>S9)\"v</b>b</s>9</i>DS)9z5.<i>fto{]F.</i>K90;n\"\\<i>oFL;<s>t</s>6</i>#e<s>!vd]MewVW!R<i>!7Il</i>JgO<i>O<b></b>vTbF8oE-.u+6s</i>ilz?1/YCt(N<i>MW%</i>Sbd\"{vkhSLUJC,F<i>Gr<b>hPHI8Y\"(Z&rm:T6cGKC</b>^Ad</i>kw[O?9[h87h-fA<i>HDs</i>.p#thOidBmZ=YI-!Dq1;7A</s>KOkK1NC=\\[^);<b><s><i>eK</i>V</s><s>K<i>:\\</i>`<i>+|0\"Q</i>i</s>Bz</b><i>l<b>I</b>WH<s>oQ<b>BB)</b><b></b>LD<b>f</b>mAm</s>R|<b>D</b>N</i>8sKk<i>hA!%FSG<b>L=</b>m</i>!<i></i><b>=K'<s>c<i>OS</i>F@<i>l#l)AK</i><i></i>[</s>|AM<s>b6#b</s>{<s>:</s>9:#|B<i>j<s>ldE</s>n{uIh0b<s>mIj^PX</s><s>\\9</s></i></b>=gJ|<b>A<i>&<s>A</s>)!<s>}</s>(S\"y;%CF}A<s>'</s>NV<s>qrL</s>Z<s>C|u}</s></i>22</b><b>u3<i>GF<s>t'6Af</s>?<s></s>!v</i>2w</b>o\"M<b>VW</b>f<s>u\"_j</s>:M<i>3:<b>LD@+<s></s>J<s></s>/Y;</b>b(Z<b>Pkw</b>Ls'<b>p^.u<s>{</s>y</b>x</i>h./waia<s>=6</s>(w<b>k2<s>)7q&|#E<i>|}b</i>%</s>Xb}JQ<s>M<i></i>OyM</s>z,M</b>,</p><p>GN9za823A!Q<i>@`2]y]X\"7ar<s>W</s>c</i>4#65{?oNE<s>GE;V<b>a</b>#q/pX3</s>I\\S5+</p></body></html>"""
                .trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    private fun checkHtmlSimpleExample3() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """<html><body><p>3U{xAKRdH^:;Ell<s>g2</s>c<b>:</b>2yXnULQHt<b>H`y^k<i>-A<s>dju</s>X</i>#3;pc'4/<i>Q</i>s<s>S1\"Q,M<i></i>@aX<i>.u</i>A<i>=!</i>6U[f</s>&V<i>N<s>J5</s>D</i>Ij<s>oe<i>OIn(tWW</i>]</s>_xDf<s>eh</s>Ar<s>iV;&SY</s>4<s>:<i>?'m</i>%@}</s>Zh2</b>7<b>6+</b>:SBSW%<b>J<s>\\<i>o</i>a</s>3bS<s>7jl9</s>t<i>:z.Kv6</i>A&qa3<s>|r-+kcYe9</s>==PwWYL</b><s>\\</s><s>{<i>h+7m<b>i</b>YF.O</i>?<i>M]d<b></b>QE</i>M<b>+tA,</b><b>tm,QO</b>{S2\"5sk.2<i>l</i><i>S<b>d,</b></i>Mi_</s>[.</p><p><b>5!<s>Pw'6(icmdt\".:IN<i>/3[_XmG|LX!)(</i>:xU@^G`<i>#</i>=</s>PGCD/TM&gAAP!KY<s>m_(B4</s>zL(!S.</b>dOW.hP<i>u</i>N^<i>8)R-N</i>DA<b>+p3ku</b>s]:R<s>&v<i>X</i>jKcF=NllW<i><b>{N5v|C?RKNq</b>^cT@xP6|NnQycA;sTP</i>\"i<b>)#<i>&6</i>Y</b>'B`<i>S&R{''}.,n'1t?u</i>d4<b>C?7FZ&Ww,%2@<i>W=n225</i>y?<i>6p!</i>O</b>},p</s>F<i><b>?<s>J.\\[</s>=<s></s>[<s>!.</s>8B7<s>Uk</s>o</b>8J-[hkk+!</i>z<i>|v;9<b>iyWe88-Y1uS/fu</b>|`\"[</i>xM.\"Q<s>G^n<i>H+ghr=3U'7#i<b>e</b></i>Ftzf7r<i>=</i>Fr</s>;mMF%3d/%Z<b>t(@</b>[(5HU4=(<s>s,96</s>Yza<s>{u1[J0pk3J<b>um</b><i>y</i>}D?<b>9S<i></i>J<i>E%()/</i></b></s>ap</p><p>JG'Dodu}B<i>5.-_UTc<s>N9\\4rI{lh</s>j</i>lE<s>f+I2</s>GUriVD1bs3<b>n)</b>plM8cbv1(qP|kL<s>{9(d1t<b>F1P=+r/<i>SQzD8=t</i>J9Dt&M:L0UX,f2m(9ClrRGL(84&<i>?p5V4&3x[J\\k,</i>U%/\\)/SCRyV0,;\\x</b>@0f9KJz,DlAkl\\W&1-Rz</s>Q;gAv_Toh+)JxxsT\\<s>8mSHTOLn3b)k{s[=Z_U=YyLwPvd3uz`</s>f?)1Som(QnrA<s>2Y<b>5#!</b>Uo<b>qi&'</b>0bESVy/KJ9&@i7ti{Gj</s>84v4Zbbk`q6</p></body></html>"""
                .trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    @Test
    @Tag("22")
    fun markdownToHtmlSimple() {
        markdownToHtmlSimple("input/markdown_simple3.md", "temp.html")
        checkHtmlSimpleExample3()
        markdownToHtmlSimple("input/markdown_simple2.md", "temp.html")
        checkHtmlSimpleExample2()
        markdownToHtmlSimple("input/markdown_simple.md", "temp.html")
        checkHtmlSimpleExample()
    }

    private fun checkHtmlListsExample() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """
                    <html>
                      <body>
                        <p>
                          <ul>
                            <li>Утка по-пекински
                              <ul>
                                <li>Утка</li>
                                <li>Соус</li>
                              </ul>
                            </li>
                            <li>Салат Оливье
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
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    @Test
    @Tag("23")
    fun markdownToHtmlLists() {
        markdownToHtmlLists("input/markdown_lists.md", "temp.html")
        checkHtmlListsExample()
    }

    @Test
    @Tag("30")
    fun markdownToHtml() {
        markdownToHtml("input/markdown_simple.md", "temp.html")
        checkHtmlSimpleExample()

        markdownToHtml("input/markdown_lists.md", "temp.html")
        checkHtmlListsExample()
    }

    @Test
    @Tag("12")
    fun printMultiplicationProcess() {
        fun test(lhv: Int, rhv: Int, res: String) {
            printMultiplicationProcess(lhv, rhv, "temp.txt")
            assertFileContent("temp.txt", res.trimIndent())
            File("temp.txt").delete()
        }

        test(
            19935,
            111,
            """
                19935
             *    111
             --------
                19935
             + 19935
             +19935
             --------
              2212785
             """
        )

        test(
            12345,
            76,
            """
               12345
             *    76
             -------
               74070
             +86415
             -------
              938220
             """
        )

        test(
            12345,
            6,
            """
              12345
             *    6
             ------
              74070
             ------
              74070
             """
        )

    }

    @Test
    @Tag("25")
    fun printDivisionProcess() {

        fun test(lhv: Int, rhv: Int, res: String) {
            printDivisionProcess(lhv, rhv, "temp.txt")
            assertFileContent("temp.txt", res.trimIndent())
            File("temp.txt").delete()
        }
        test(
            812557,
            3903,
            """
              812557 | 3903
             -7806     208
             -----
               3195
                 -0
               ----
               31957
              -31224
              ------
                 733
             """
        )

        test(
            178614,
            92352,
            """
              178614 | 92352
              -92352   1
              ------
               86262
             """
        )

        test(
            2,
            20,
            """
              2 | 20
             -0   0
             --
              2
             """
        )

        test(
            3813,
            5510,
            """
              3813 | 5510
                -0   0
              ----
              3813
             """
        )

        test(
            260634,
            2,
            """
              260634 | 2
             -2        130317
             --
              06
              -6
              --
               00
               -0
               --
                06
                -6
                --
                 03
                 -2
                 --
                  14
                 -14
                 ---
                   0
             """
        )

        test(
            812557,
            3903,
            """
              812557 | 3903
             -7806     208
             -----
               3195
                 -0
               ----
               31957
              -31224
              ------
                 733
             """
        )

        test(
            373715,
            2,
            """
              373715 | 2
             -2        186857
             --
              17
             -16
             ---
               13
              -12
              ---
                17
               -16
               ---
                 11
                -10
                ---
                  15
                 -14
                 ---
                   1
             """
        )

        test(
            19935,
            22,
            """
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
             """
        )

        test(
            94,
            2,
            """
              94 | 2
             -8    47
             --
              14
             -14
             ---
               0
             """
        )

        test(
            903,
            1,
            """
              903 | 1
             -9     903
             --
              00
              -0
              --
               03
               -3
               --
                0
             """
        )

        test(
            99999,
            1,
            """
              99999 | 1
             -9       99999
             --
              09
              -9
              --
               09
               -9
               --
                09
                -9
                --
                 09
                 -9
                 --
                  0
             """
        )

        File("temp.txt").delete()
    }
}
