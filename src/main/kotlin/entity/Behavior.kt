package fit.bikot.dsl.fve.entity

interface IBehavior {
    var trigger: () -> Unit
    var action: () -> Unit
    fun trigger(trigger: () -> Unit)
    fun action(action: () -> Unit)
}

class Behavior(val name: String) : IBehavior {
    override lateinit var trigger: () -> Unit
    override lateinit var action: () -> Unit
    override fun action(action: () -> Unit) {
        this.action = action
    }

    override fun trigger(trigger: () -> Unit) {
        this.trigger = trigger
    }
}