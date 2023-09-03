package life.light.posemetre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class MainActivityTest  {

    // Exécute chaque tâche de manière synchrone à l'aide des composants d'architecture.
    // Indispensable pour fonctionner
    @get:Rule
    val rule = InstantTaskExecutorRule()
    val listeOuverture: ArrayList<String> = ArrayList()
    val listeVitesse: ArrayList<String> = ArrayList()

    @Before
    fun setup() {
        listeOuverture.add("2.8")
        listeOuverture.add("4")
        listeOuverture.add("4.5")
        listeOuverture.add("5.6")
        listeOuverture.add("8")
        listeOuverture.add("11")
        listeOuverture.add("16")
        listeOuverture.add("22")

        listeVitesse.add("1/500")
        listeVitesse.add("1/250")
        listeVitesse.add("1/125")
        listeVitesse.add("1/60")
        listeVitesse.add("1/30")
        listeVitesse.add("1/15")
        listeVitesse.add("1/8")
        listeVitesse.add("1/4")
        listeVitesse.add("1/2")
        listeVitesse.add("1")
        listeVitesse.add("B")
    }

    @Test
    fun test_rechercheOuvertureInferieurMinimum() {
        /* Given */
        val ouvertureCalcule = 2.0
        val indexOuvertureSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexOuverture(
            ouvertureCalcule,
            indexOuvertureSelected,
            listeOuverture
        )
        /* Then */
        assertEquals(index,0)
        assertFalse(trouve)
    }

    @Test
    fun test_rechercheOuvertureSuperieurMaximum() {
        /* Given */
        val ouvertureCalcule = 25.0
        val indexOuvertureSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexOuverture(
            ouvertureCalcule,
            indexOuvertureSelected,
            listeOuverture
        )
        /* Then */
        assertEquals(index,7)
        assertFalse(trouve)
    }

    @Test
    fun test_rechercheOuvertureProcheSuperieur() {
        /* Given */
        val ouvertureCalcule = 20.0
        val indexOuvertureSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexOuverture(
            ouvertureCalcule,
            indexOuvertureSelected,
            listeOuverture
        )
        /* Then */
        assertEquals(index,7)
        assertTrue(trouve)
    }

    @Test
    fun test_rechercheOuvertureProcheInferieur() {
        /* Given */
        val ouvertureCalcule = 18.0
        val indexOuvertureSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexOuverture(
            ouvertureCalcule,
            indexOuvertureSelected,
            listeOuverture
        )
        /* Then */
        assertEquals(index,6)
        assertTrue(trouve)
    }


    @Test
    fun test_rechercheVitesseInferieurMinimum() {
        /* Given */
        val vitesseCalcule = 0.001 // 1/1000 seconde
        val indexVitesseSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexVitesse(
            vitesseCalcule,
            indexVitesseSelected,
            listeVitesse
        )
        /* Then */
        assertEquals(index,0)
        assertFalse(trouve)
    }

    @Test
    fun test_rechercheVitesseSuperieurMaximum() {
        /* Given */
        val vitesseCalcule = 2.0 //2 secondes
        val indexVitesseSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexVitesse(
            vitesseCalcule,
            indexVitesseSelected,
            listeVitesse
        )
        /* Then */
        assertEquals(index,10)
        assertFalse(trouve)
    }

    @Test
    fun test_rechercheVitesseProcheSuperieur() {
        /* Given */
        val vitesseCalcule = 0.01 // 1/100 seconde
        val indexVitesseSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexVitesse(
            vitesseCalcule,
            indexVitesseSelected,
            listeVitesse
        )
        /* Then */
        assertEquals(index,2)
        assertTrue(trouve)
    }

    @Test
    fun test_rechercheVitesseProcheInferieur() {
        /* Given */
        val vitesseCalcule = 0.0025 // 1/400
        val indexVitesseSelected = 0
        /* When */
        val (index, trouve) = MainActivity().rechercheIndexVitesse(
            vitesseCalcule,
            indexVitesseSelected,
            listeVitesse
        )
        /* Then */
        assertEquals(index,0)
        assertTrue(trouve)
    }
}