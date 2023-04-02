package com.example.util

import java.io.File
import java.lang.Integer.max

class Report(
    private val fileName: String,
) {
    private val targetDir = "reports"
    private var data: String = ""

    @Synchronized
    fun text(text: String) {
        "$text\n".also { data += it + "\n" }.also { println(it) }
    }

    @Synchronized
    fun line(text: String) {
        text.also { data += it + "\n" }.also { println(it) }
    }

    fun h1(text: String) {
        text("# $text")
    }

    fun h2(text: String) {
        text("## $text")
    }

    fun h3(text: String) {
        text("### $text")
    }

    fun h4(text: String) {
        text("#### $text")
    }

    fun code(text: String) {
        text("`$text`")
    }

    fun line() {
        text("---")
    }

    fun json(json: String) {
        val block = "```json\n${json}\n```"
        text(block)
    }

    @Synchronized
    fun writeToFile() {
        File(targetDir).mkdirs()
        val file = File(targetDir, fileName)
        file.writeText(data)
        println("Wrote report to ${file.absolutePath}")
    }

    @Synchronized
    fun clear() {
        data = ""
    }

    fun htmlTable(legend: String, data: LinkedHashMap<String, LinkedHashMap<String, Benchmark.Result>>) {
        line(htmlTableString(legend, data))
    }

    private fun htmlTableString(legend: String, data: LinkedHashMap<String, LinkedHashMap<String, Benchmark.Result>>): String {
        val sb = StringBuilder()
        sb.append("<table>\n")
        // Get a list of unique inner keys (column headers)
        val columnHeaders = data.values.flatMap { it.keys }.toSet()
        // Generate the first row with header column
        sb.append("  <tr>\n")
        sb.append("    <th><i>$legend</i></th>\n") // Legend at the beginning of the header row
        columnHeaders.forEach { columnHeader ->
            val header = columnHeader.split(" ").joinToString("<br/>") // Minimize width
            sb.append("    <th>$header</th>\n")
        }
        sb.append("  </tr>\n")
        // Generate the rest of the rows
        data.keys.forEach { rowKey ->
            sb.append("  <tr>\n")
            sb.append("    <th>$rowKey</th>\n") // Header cell in the first column
            columnHeaders.forEach { columnHeader ->
                val ops = data[rowKey]?.get(columnHeader)?.opsPerSecond()
                val errors = data[rowKey]?.get(columnHeader)?.errorPercentage()?.let { "<i>F$it%</i>" } ?: ""
                sb.append("    <td>$ops $errors</td>\n")
            }
            sb.append("  </tr>\n")
        }
        sb.append("</table>")
        return sb.toString()
    }

    fun table(titleOne: String, titleTwo: String, data: List<Pair<String, String>>) {
        val maxLengthQuery = max(titleOne.length, data.maxByOrNull { it.first.length }?.first?.length ?: 0)
        val maxLengthResult = max(titleTwo.length, data.maxByOrNull { it.second.length }?.second?.length ?: 0)

        line("| $titleOne${" ".repeat(maxLengthQuery - titleOne.length)} | $titleTwo${" ".repeat(maxLengthResult - titleTwo.length)} |")
        line("|${"-".repeat(maxLengthQuery + 2)}|${"-".repeat(maxLengthResult + 2)}|")

        data.forEach { (query, result) ->
            val paddedQuery = query.padEnd(maxLengthQuery + 1)
            val paddedResult = result.padEnd(maxLengthResult + 1)
            line("| $paddedQuery| $paddedResult|")
        }
        line("")
    }
}
