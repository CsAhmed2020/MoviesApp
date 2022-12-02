package com.example.moviesapp.data.mapper

import android.util.Log
import com.example.moviesapp.domain.model.NetflixMovie
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row


fun MutableList<String>.toMovie() : NetflixMovie{
        return NetflixMovie(
            title = elementAt(0),
            type = elementAt(1),
            description = elementAt(2),
            releaseYear = elementAt(3),
            ageCertification = elementAt(4),
            runtime = elementAt(5),
            genres = elementAt(6),
            productionCountries = elementAt(7),
            seasons = elementAt(8),
            imdbScore = elementAt(9),
            imdbVotes = elementAt(10),
            tmdbPopularity = elementAt(11),
            tmdbScore = elementAt(12)
        )
    }

fun getCellAsString(row: Row, c: Int, formulaEvaluator: FormulaEvaluator): String {
    var value = ""
    if (formulaEvaluator.evaluate(row.getCell(c)) != null) {
        try {
            val cell = row.getCell(c)
            val cellValue = formulaEvaluator.evaluate(cell)
            when (cellValue.cellType) {
                Cell.CELL_TYPE_BOOLEAN -> value = "" + cellValue.booleanValue
                Cell.CELL_TYPE_NUMERIC -> {
                    val numericValue = cellValue.numberValue
                    value = "" + numericValue
                }
                Cell.CELL_TYPE_STRING -> value = "" + cellValue.stringValue
                else -> { value = "" }
            }
        } catch (e: NullPointerException) {
            Log.e("Ahmed", "getCellAsString: NullPointerException: " + e.message)
        }
    }
    return value
}
