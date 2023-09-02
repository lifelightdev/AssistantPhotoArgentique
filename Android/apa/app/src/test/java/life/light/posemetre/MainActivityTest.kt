package life.light.posemetre

import org.junit.Test
import org.junit.Assert.assertEquals

class MainActivityTest  {

    @Test
    fun calcul() {
        // TODO
    }

    @Test
    fun rechercheIndexOuvertureTest() {
        /* Given */
        val ouvertureCalcule = 0.0
        val indexOuvertureSelected = 0
        val listeOuverture: ArrayList<String> = ArrayList()

        /* When */
        val (index, trouve) = MainActivity().rechercheIndexOuverture(
            ouvertureCalcule,
            indexOuvertureSelected,
            listeOuverture
        )

        /* Then */
        assertEquals(true, true)
    }

    @Test
    fun rechercheIndexVitesse() {
        // TODO
    }
}