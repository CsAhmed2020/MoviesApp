package com.example.moviesapp.data.remote

import android.util.Log
import com.example.moviesapp.application.MoviesApplication
import com.example.moviesapp.data.mapper.getCellAsString
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.domain.model.NetflixMovie
import com.example.moviesapp.domain.model.TopMoviesTVs
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.FileAsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*
import com.example.moviesapp.common.Constants
import com.example.moviesapp.common.Resource
import io.ktor.http.cio.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MoviesRemoteDataSourceImpl (
    private val baseUrl: String
    ):MoviesRemoteDataSource {

    private val client = HttpClient(Android) {
        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        engine {
            connectTimeout = 60_000
            socketTimeout = 60_000
        }


    }

    override suspend fun getTopMovies(): TopMoviesTVs =
        client.get {
            url("${baseUrl}${Constants.TOP_MOVIES}")
            parameter("apiKey",Constants.API_KEY)
        }

    override suspend fun getTopTvs(): TopMoviesTVs =
        client.get{
            url("${baseUrl}${Constants.TOP_TVs}")
            parameter("apiKey",Constants.API_KEY)
        }

    override suspend fun getLocalAgencyMovies(): HttpResponse =
        client.get {
            url(Constants.LOCAL_AGENCY_MOVIES)
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getNetflixMovies(): Flow<Resource<List<NetflixMovie>>> =
        callbackFlow {
            val url = Constants.NETFLIX_MOVIES
            val client = AsyncHttpClient()
            val netflixMovies = mutableListOf<NetflixMovie>()
            client.get(url, object : FileAsyncHttpResponseHandler(MoviesApplication.context.applicationContext) {
                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    throwable: Throwable,
                    file: File
                ) {}

                override fun onSuccess(statusCode: Int, headers: Array<Header>, file: File) {
                    Log.d("Ahmed","OnSuccess")
                    try {
                        val inputStream: InputStream = FileInputStream(file)
                        val workbook = XSSFWorkbook(inputStream)
                        val sheet: XSSFSheet = workbook.getSheetAt(0)
                        val rowsCount = sheet.physicalNumberOfRows
                        val formulaEvaluator: FormulaEvaluator =
                            workbook.creationHelper.createFormulaEvaluator()


                        for (r in 1 until rowsCount/50) {
                            val row: Row = sheet.getRow(r)
                            val cellsCount: Int = row.physicalNumberOfCells
                            val movie  : MutableList<String>   = MutableList(12){""}
                            for (c in 0 until cellsCount) {
                                if (c > 13) {
                                    break
                                } else {
                                    val value: String = getCellAsString(row, c, formulaEvaluator)
                                    movie.add(c,value)

                                }
                            }
                            netflixMovies.add(movie.toMovie())
                        }

                        trySend(Resource.Success(data = netflixMovies))

                    } catch (e: FileNotFoundException) {
                        Log.e("Ahmed", "readExcelData: FileNotFoundException. " + e.message)
                        trySend(Resource.Error(e.message.toString()))
                    } catch (e: IOException) {
                        Log.e("Ahmed", "readExcelData: Error reading input stream. " + e.message)
                        trySend(Resource.Error(e.message.toString()))
                    }
                }
            })
            awaitClose { cancel() }
        }



}