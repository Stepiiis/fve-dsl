package fit.bikot.dsl.fve.entity

import fit.bikot.dsl.fve.enums.ProtocolTypeEnum
import fit.bikot.dsl.fve.enums.ProtocolTypeEnum.*

abstract class Node{
    private val events = mutableListOf<Event>()
    var reductionFunction: (() -> Unit)? = null
    lateinit var endpoint: String
    lateinit var name: String
    var adjustable: Boolean = false

    fun onEvent(eventType: String, action: Event.() -> Unit) {
        val event = Event(eventType, this).apply(action)
        events.add(event)
    }

    abstract fun send(value: Any): Boolean
    abstract fun get(): Any

    fun reductionAction(reductionFunction: () -> Unit) {
        this.reductionFunction = reductionFunction
    }

    fun reducePower(){
        reductionFunction?.invoke()
    }
}

fun createNode(protocol: ProtocolTypeEnum): Node{
    return when(protocol){
        MQTT -> MQTTNode()
        Matter -> TODO()
        ZIGBEE -> TODO()
        LORA -> TODO()
        OPC_UA -> TODO()
        MODBUS -> TODO()
    }
}

class MQTTNode: Node() {
    override fun send(value: Any): Boolean {
        TODO("Emit config to send current value to MQTT endpoint.")
    }
    override fun get(): Any{
        TODO("Emit config to read current value from MQTT endpoint")
    }
}
