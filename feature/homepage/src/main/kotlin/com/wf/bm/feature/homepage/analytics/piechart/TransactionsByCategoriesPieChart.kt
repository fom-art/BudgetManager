package com.wf.bm.feature.homepage.analytics.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wf.bm.core.model.Category
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.utils.toStringWithTwoDecimalsAndNoTrailingZeros
import java.time.LocalDateTime
import kotlin.math.abs

@Composable
fun TransactionsByCategoriesPieChart(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
    currency: Currency,
) {
    val categoryExpenseMap = transactions
        .flatMap { transaction ->
            transaction.categories.map {
                it.title to transaction.currency.exchangeCurrency(transaction.amount, currency)
            }
        }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, amounts) -> amounts.sum() }

    val totalExpenses = categoryExpenseMap.values.sum()
    val colors =
        generateCompatibleColors(categoryExpenseMap.size, MaterialTheme.colorScheme.primary) // Generate distinct colors for each category

    val pieChartData =
        categoryExpenseMap.toList().mapIndexed { index, (category, amount) -> // Convert map to list
            PieChartData(
                label = category,
                value = amount,
                percentage = amount / totalExpenses,
                color = colors[index]
            )
        }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pie Chart
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            DrawPieChart(pieChartData = pieChartData)
        }

        // Legends
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pieChartData.forEach { data ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(data.color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${data.label}: ${data.value.toStringWithTwoDecimalsAndNoTrailingZeros()} ${currency.sign}" +
                                " (${(data.percentage * 100).toInt()}%)",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

// Pie Chart Data Class
data class PieChartData(
    val label: String,
    val value: Double,
    val percentage: Double,
    val color: Color
)

// Pie Chart Drawing
@Composable
fun DrawPieChart(pieChartData: List<PieChartData>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        var startAngle = 0f
        val sweepAngles = pieChartData.map { it.percentage * 360f }

        pieChartData.zip(sweepAngles).forEach { (data, sweepAngle) ->
            drawArc(
                color = data.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle.toFloat(),
                useCenter = true
            )
            startAngle += sweepAngle.toFloat()
        }
    }
}

fun generateCompatibleColors(count: Int, primaryColor: Color): List<Color> {
    val primaryHSL = colorToHSL(primaryColor)
    val baseHue = primaryHSL.first
    val saturation = primaryHSL.second.coerceIn(0.6f, 0.8f) // Maintain vibrant saturation
    val lightness = primaryHSL.third.coerceIn(0.6f, 0.7f) // Slightly lighter, balanced lightness

    return List(count) { index ->
        // Evenly spread hues around the primary hue
        val hueOffset = (360 / count) * index
        val hue = (baseHue + hueOffset) % 360
        val generatedColor = hslToColor(
            hue = hue.toFloat(),
            saturation = saturation,
            lightness = lightness
        )

        // Blend minimally with the primary color for subtle harmony
        lerp(generatedColor, primaryColor, 0.2f) // Minimal blending for distinct but harmonious colors
    }
}

fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
    val c = (1 - abs(2 * lightness - 1)) * saturation
    val x = c * (1 - abs((hue / 60) % 2 - 1))
    val m = lightness - c / 2

    val (r, g, b) = when {
        hue < 60 -> Triple(c, x, 0f)
        hue < 120 -> Triple(x, c, 0f)
        hue < 180 -> Triple(0f, c, x)
        hue < 240 -> Triple(0f, x, c)
        hue < 300 -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = (r + m),
        green = (g + m),
        blue = (b + m)
    )
}

fun colorToHSL(color: Color): Triple<Float, Float, Float> {
    val r = color.red
    val g = color.green
    val b = color.blue

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    val lightness = (max + min) / 2

    val saturation = if (delta == 0f) {
        0f
    } else {
        delta / (1 - abs(2 * lightness - 1))
    }

    val hue = when (max) {
        r -> 60 * ((g - b) / delta % 6)
        g -> 60 * ((b - r) / delta + 2)
        b -> 60 * ((r - g) / delta + 4)
        else -> 0f
    }.let { if (it < 0) it + 360 else it }

    return Triple(hue, saturation, lightness)
}



@Preview(showBackground = true)
@Composable
fun TransactionsByCategoriesPieChartPreview() {
    val sampleTransactions = listOf(
        Transaction(
            title = "Groceries",
            isPositive = false,
            amount = 150.0,
            currency = Currency.USD,
            date = LocalDateTime.now(),
            categories = listOf(Category("Food")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Rent",
            isPositive = false,
            amount = 800.0,
            currency = Currency.EUR,
            date = LocalDateTime.now(),
            categories = listOf(Category("Housing")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Utilities",
            isPositive = false,
            amount = 200.0,
            currency = Currency.UAH,
            date = LocalDateTime.now(),
            categories = listOf(Category("Utilities")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Dining Out",
            isPositive = false,
            amount = 100.0,
            currency = Currency.CZK,
            date = LocalDateTime.now(),
            categories = listOf(Category("Dining")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Entertainment",
            isPositive = false,
            amount = 300.0,
            currency = Currency.USD,
            date = LocalDateTime.now(),
            categories = listOf(Category("Leisure")),
            repeatableTransaction = null
        )
    )

    TransactionsByCategoriesPieChart(
        transactions = sampleTransactions,
        modifier = Modifier.padding(16.dp),
        currency = Currency.USD // Convert all amounts to USD
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyTransactionsByCategoriesPieChartPreview() {
     TransactionsByCategoriesPieChart(
        transactions = emptyList(),
        modifier = Modifier.padding(16.dp),
        currency = Currency.USD // Convert all amounts to USD
    )
}