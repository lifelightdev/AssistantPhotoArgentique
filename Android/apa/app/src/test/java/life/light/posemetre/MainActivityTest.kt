package life.light.posemetre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertTrue

class MainActivityTest  {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun calcul() {
        // TODO
    }

    @Test
    fun rechercheIndexOuvertureTest() {

        /* Given */
        val ouvertureCalcule = 22.0
        val indexOuvertureSelected = 0
        val listeOuverture: ArrayList<String> = ArrayList()
        listeOuverture.add("4.5")
        listeOuverture.add("11")
        listeOuverture.add("22")

        /* When */
        val (index, trouve) = MainActivity().rechercheIndexOuverture(
            ouvertureCalcule,
            indexOuvertureSelected,
            listeOuverture
        )

        /* Then */
        assertEquals(index,2)
        assertTrue(trouve)

    }

    @Test
    fun rechercheIndexVitesse() {
        // TODO
    }
}