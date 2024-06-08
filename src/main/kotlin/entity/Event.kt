package fit.bikot.dsl.fve.entity


open class Event(val type: String, val node: Node) {
    lateinit var action: () -> Unit
    var condition: ((Any) -> Unit)? = null

    fun action(action: () -> Unit) {
        this.action = action
    }

    fun condition(condition: (Any) -> Unit) {
        this.condition = condition
    }

    fun atSunset() {
        TODO("Emit config to compare current time with time of sunset at current location")
    }

    fun atSundawn() {
        TODO("Emit config to compare current time with time of sundawn at current location")
    }

}