import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

// bubble sort algorithm
fun bubbleSort(arr: IntArray) {
    val length = arr.size
    for (i in 0 until length - 1) {
        for (j in 0 until length - i - 1) {
            if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
            }
        }
    }
}

// insertion sort algorithm
fun insertionSort(arr: IntArray) {
    val length = arr.size
    for (i in 1 until length) {
        val key = arr[i]
        var j = i - 1
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]
            j--
        }
        arr[j + 1] = key
    }
}

// quick sort algorithm
fun quickSort(arr: IntArray, low: Int, high: Int) {
    if (low < high) {
        val pivotIndex = choosePivot( low, high)
        val partitionIndex = partition(arr, low, high, pivotIndex)
        quickSort(arr, low, partitionIndex - 1)
        quickSort(arr, partitionIndex + 1, high)
    }
}

fun choosePivot(low: Int, high: Int): Int {
    // Choose pivot as the middle element
    return low + (high - low) / 2
}

fun partition(arr: IntArray, low: Int, high: Int, pivotIndex: Int): Int {
    val pivotValue = arr[pivotIndex]
    swap(arr, pivotIndex, high) // Move pivot to the end
    var i = low
    for (j in low until high) {
        if (arr[j] < pivotValue) {
            swap(arr, i, j)
            i++
        }
    }
    swap(arr, i, high) // Move pivot to its final position
    return i
}

fun swap(arr: IntArray, i: Int, j: Int) {
    val temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}


// function to perform concurrent sorting
fun concurrentSort(arr: IntArray) {
    runBlocking {
        val bubbleSortDeferred = async { bubbleSort(arr) }
        val insertionSortDeferred = async { insertionSort(arr) }
        val quickSortDeferred = async { quickSort(arr, 0, arr.size - 1) }
        awaitAll(bubbleSortDeferred, insertionSortDeferred, quickSortDeferred)
    }
}

fun main() {
    val arr = intArrayOf(64, 34, 25, 12, 22, 11, 90)
    val arr1 = intArrayOf(50, 10, 80, 70, 25, 15)
    val arr2 = intArrayOf(9,5,2,3,6,7,1,4)
    val arr3 = intArrayOf(90,30,50,20,15,60,35)

    println("\n Original array : ${arr.contentToString()}\n")

    concurrentSort(arr)
    println("Sorted Array : ${arr.contentToString()}")
    println()

    bubbleSort(arr1)
    println("Bubble Sort : ${arr1.contentToString()}\n")


    insertionSort(arr2)
    println("Insertion Sort : ${arr2.contentToString()}\n")

    quickSort(arr3, 0, arr3.size - 1)
    println("Quick Sort : ${arr3.contentToString()}")
}
