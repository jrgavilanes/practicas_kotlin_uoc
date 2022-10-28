class MyLinkedList(value: Any) {
    class Node(var value: Any) {
        var next: Node? = null
    }

    private var head: Node? = Node(value)
    private var tail: Node? = Node(value)
    private var len = 1

    fun len(): Int {
        return len
    }

    fun append(value: Any) {
        val node = Node(value)
        if (len == 1) head?.next = node
        tail?.next = node
        tail = node
        len++
    }

    fun del(position: Int) {
        if (position == 1) {
            head = head?.next
            len--
            return
        }

        if (position > len || position < 1) {
            throw IndexOutOfBoundsException("Borrado fuera de los límites")
        }

        var pos = 1
        var cursor = head
        var prev = head
        while (true) {
            if (pos == position) {
                prev?.next = cursor?.next
                len--
                break
            }
            prev = cursor
            cursor = cursor?.next
            pos++
        }
    }

    fun insert(value: Any) {
        val node = Node(value)
        node.next = head
        head = node
        len++
    }

    fun value(): String {
        var result = ""
        var cursor = head
        while (cursor != null) {
            result = "$result${cursor.value}"
//            print(cursor.value)
            cursor = cursor.next
        }
        return result
    }
}

fun main() {
    val myList = MyLinkedList(8)
    myList.append(0)
    myList.append(1)
    myList.append(2)
//    myList.insert(9)
//    myList.insert(7)

    println()
//    print("longitud ${myList.len()}")
    myList.del(3)
    println(myList.value())
}
