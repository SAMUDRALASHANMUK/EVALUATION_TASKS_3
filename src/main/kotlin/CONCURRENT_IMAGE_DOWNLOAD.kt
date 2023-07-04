import kotlinx.coroutines.*
import java.io.File
import java.net.URL

fun main() {

    val imageUrls = listOf(
        "https://random.dog/1f1447c8-0eda-4a0e-8e02-d8194243737c.png",
        "https://random.dog/3518b9b0-64b1-4b7d-b5b4-259a4b81d8ec.jpg",
        "https://random.dog/b85abb5b-5b9e-47d2-9938-71129cdfdb50.jpg",
        "https://random.dog/9e90f135-67ec-47ef-9d99-61b9223f2e86.jpg",
        "https://random.dog/27ee3890-8eca-4038-b77e-b6630ebd3b74.jpg",
        "https://random.dog/41bf3852-811c-470f-8e3d-8b023e5e1c0b.jpg",
        "https://random.dog/2294c0a8-0595-4fca-869e-803e13d2ffe1.jpg",
        "https://random.dog/441dc47a-b99b-4a74-bfe9-5159ddd1b9bf.jpg",
        "https://random.dog/80a8c758-1ee8-4460-96c6-cb31716a269c.jpg",
        "https://random.dog/b5546559-7090-41cd-aef6-e830c2206f8d.jpg",
        "https://random.dog/42e97d6a-c825-4191-9434-32cea191fc21.jpeg",
        "https://random.dog/6428de50-b94b-4b1a-9cab-63495e637daa.jpg",
        "https://random.dog/bc21dfb7-db3b-4d66-93d8-7bb4809ec1b8.jpg",
        "https://random.dog/6d07c332-b2b3-418e-a3ab-bb189e4bb899.jpg",
        "https://random.dog/8fbff52b-416f-45ba-a273-ce503cdbbef3.jpg",
        "https://random.dog/5c2796a3-e1d6-4fc2-92e9-7333fc58f341.jpg",
        "https://random.dog/713ae190-d34f-4355-a9c3-abbdc642a105.jpg",
        "https://random.dog/a338fdf7-70aa-4a26-a2c2-357cdede7991.jpg",
        "https://random.dog/64e10608-3438-4a19-b362-136f0d8719de.jpg",
        "https://random.dog/b645104b-f4d4-4b0a-abbd-02785145f5a3.png",
        "https://random.dog/zqdg7gkwd4s11.png"
    )

    val outputDirectory = File("C:\\Users\\shanmuk.s\\Downloads\\Images_kotlin_program")

    /* With parameter: Dispatchers.Default [similar to GlobalScope.launch { } ]
          - Gets its own context at Global level. Executes in a separate background thread.
          - After delay() or suspending function execution,
              it continues to run either in the same thread or some other thread.  */

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    val job = coroutineScope.launch {
        imageUrls.forEachIndexed { index, imageUrl ->
            launch {
                try {
                    val fileName = imageUrl.substringAfterLast("/")
                    val outputFile = File(outputDirectory, fileName)
                    URL(imageUrl).openStream().use { input ->
                        outputFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    println("$index : image downloaded successfully..")
                } catch (e: Exception) {
                    println("unable to download this image and the message is ${e.message}")
                }
            }
        }
    }
    runBlocking {
        job.join()
    }
}
