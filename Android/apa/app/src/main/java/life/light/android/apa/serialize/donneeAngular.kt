class AndroidVue {
    var id: Long = 0
    var nomAppareilPhoto: String? = null
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var sensibilite: String? = null
    lateinit var vitesses: List<String>
    lateinit var ouvertures: List<String>

    override fun toString(): String {
        return "AndroidVue(id=$id, " +
                "nomAppareilPhoto=$nomAppareilPhoto, " +
                "latitude=$latitude," +
                "longitude=$longitude," +
                "sensibilite=$sensibilite," +
                "vitesses=$vitesses," +
                "ouvertures=$ouvertures)"
    }
}


