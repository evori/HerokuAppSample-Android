package example.aleks.com.herokuapp.domain.repository.storage

import com.google.gson.Gson
import example.aleks.com.herokuapp.domain.model.MoviesData
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.*
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */

class LocalStorageRepository @Inject constructor(private val cacheFolder: File, private val gson: Gson)
    : ILocalStorageRepository {

    //region properties
    private val moviesFileName = "${LocalStorageRepository::class.java.simpleName}_movies.json"
    //endregion

    //region ILocalStorageRepository implementation
    override fun addMovies(movies: MoviesData) {

        try {

            saveObjectToFile(movies, MoviesData::class.java, fileName = moviesFileName)
        } catch (ex: Exception) {

            ex.printStackTrace()
        }
    }

    override fun getMovies(): Single<MoviesData> {

        return Single.create<MoviesData> {

            var movies: MoviesData? = null

            try {

                movies = restoreObjectFromFile(MoviesData::class.java, fileName = moviesFileName )

            } catch (ex: Exception) {

                ex.printStackTrace()
            }

            if (!it.isDisposed) {

                it.onSuccess(movies ?: MoviesData.empty())
            }
        }
    }

    override fun deleteMovies() {

        try {

            deleteFile(fileName = moviesFileName)
        } catch (ex: Exception) {

            ex.printStackTrace()
        }
    }
    //endregion

    //region private methods
    private fun saveObjectToFile(src: Any, typeOfSrc: Type, fileName: String) {

        val jsonString: String = gson.toJson(src, typeOfSrc)
        overwriteTextFile(fileName, jsonString)
    }

    private fun <T : Any> restoreObjectFromFile(typeOfSrc: Type, fileName: String): T {

        val jsonString = readTextFile(fileName)
        return gson.fromJson(jsonString, typeOfSrc)
    }

    private fun createNewOrOverwrite(folderName: String, fileName: String): File {

        val file = File(folderName, fileName)

        if (file.exists())
            file.delete()

        file.createNewFile()
        return file

    }

    private fun deleteFile(fileName: String) {

        val file = File(cacheFolder.absolutePath, fileName)

        if (file.exists())
            file.delete()
    }

    private fun overwriteTextFile(fileName: String, textToWrite: String) {

        var output: Writer? = null

        try {
            val file = createNewOrOverwrite(cacheFolder.absolutePath, fileName)
            output = BufferedWriter(FileWriter(file))
            output.write(textToWrite)
        } finally {

            output?.close()
        }
    }

    private fun readTextFile(fileName: String): String? {

        var inputStream: InputStream? = null
        var textContent: String? = null

        try {
            val file = File(cacheFolder.absolutePath, fileName)

            if (file.exists()) {

                inputStream = FileInputStream(file)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                textContent = String(buffer, Charsets.UTF_8)
            }
        } finally {

            inputStream?.close()
        }

        return textContent
    }
    //endregion
}