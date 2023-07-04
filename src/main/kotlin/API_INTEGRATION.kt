import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray


// Data model for User
data class User(val id: Int, val name: String, val email: String)

// Data model for Quotes
data class Quote(val content: String, val author: String)

// Data model for Dog Image
data class DogImage(val imageUrl: String)

// Fetches users from JSON Placeholder API
suspend fun fetchUsers(): List<User> {
    val url = "https://jsonplaceholder.typicode.com/users"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()
//withcontext : Calls the specified suspending block with a given coroutine context, suspends until it completes, and returns the result.

    val response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    val users = mutableListOf<User>()
    if (response.isSuccessful) {
        val json = response.body?.string()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val userObject = jsonArray.getJSONObject(i)
            val id = userObject.getInt("id")
            val name = userObject.getString("name")
            val email = userObject.getString("email")
            users.add(User(id, name, email))
        }
    }

    return users
}


// Fetches a random quote from the Quote API
suspend fun fetchRandomQuote(): Quote? {
    val url = "https://api.quotable.io/random"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    val response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    var quote: Quote? = null
    if (response.isSuccessful) {
        val json = response.body?.string()
        val jsonObject = JSONObject(json)
        val text = jsonObject.getString("content")
        val author = jsonObject.getString("author")
        quote = Quote(text, author)
    }

    return quote
}

// Fetches a random dog image from the Random Dog Image API
suspend fun fetchRandomDogImage(): DogImage? {
    val url = "https://random.dog/woof.json"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    val response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    var dogImage: DogImage? = null
    if (response.isSuccessful) {
        val json = response.body?.string()
        val jsonObject = JSONObject(json)
        val imageUrl = jsonObject.getString("url")
        dogImage = DogImage(imageUrl)
    }

    return dogImage
}

// this is the main function which is used to fetch the data concurrently from multiple apis
fun main() {
    runBlocking {

        val usersDeferred = async { fetchUsers() }
        val quotesDeferred = async { fetchRandomQuote() }
        val dogImageDeferred = async { fetchRandomDogImage() }
        val users = usersDeferred.await()
        val quote = quotesDeferred.await()
        val dogImage = dogImageDeferred.await()

        println("Users:")
        users.forEach { user -> println("${user.id}. ${user.name} --- ${user.email}") }



        println("\nQuote:")
        quote?.let { q -> println("${q.content} - ${q.author}") }

        println("\nDog Image:")
        dogImage?.let { image -> println(image.imageUrl) }
    }
}