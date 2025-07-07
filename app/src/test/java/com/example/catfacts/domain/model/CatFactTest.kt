import com.example.catfacts.domain.model.CatFact
import junit.framework.TestCase.assertEquals
import org.junit.Test


class CatFactTest {
    @Test
    fun `test CatFact data class works`() {
        val fact = CatFact("Cats sleep 70% of their life", 30)
        assertEquals("Cats sleep 70% of their life", fact.fact)
        assertEquals(30, fact.length)
    }
}
