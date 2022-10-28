
import org.junit.Assert.*
import org.junit.Test

class MyLinkedListTest {

    @Test
    fun `MyLinkedList append`() {
        val myList = MyLinkedList(1)
        myList.append(2)
        myList.append(3)
        myList.append(4)
        myList.append(5)
        assert(myList.value() == "12345")
    }

    @Test
    fun `MyLinkedList insert`() {
        val myList = MyLinkedList(1)
        myList.append(2)
        myList.append(3)
        myList.append(4)
        myList.append(5)
        myList.insert(0)
        assert(myList.value() == "012345")
        myList.insert(9)
        assert(myList.value() == "9012345")
    }

    @Test
    fun `MyLinkedList delete`() {
        val myList = MyLinkedList(1)
        myList.append(2)
        myList.append(3)
        myList.append(4)
        myList.append(5)

        myList.del(1)
        assert(myList.value() == "2345")

        myList.del(2)
        var result = myList.value()
        assert(result == "245")

        myList.del(3)
        result = myList.value()
        assert(result == "24")

        try {
            myList.del(3)
            result = myList.value()
            assert(result == "24")
        } catch (e: IndexOutOfBoundsException) {
            e.message?.contains("fuera de los")?.let { assert(it) }
        }

        try {
            myList.del(0)
            result = myList.value()
            assert(result == "24")
        } catch (e: IndexOutOfBoundsException) {
            assert(true)
        }
    }
}
