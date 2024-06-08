package fit.bikot.dsl.fve.entity

import fit.bikot.dsl.fve.enums.NodeFamilyTypeEnum


sealed interface SmartHomeConfigIfc {
    fun addFamily(family: NodeFamily): SmartHomeConfigIfc
    fun family(type: NodeFamilyTypeEnum, initializer: NodeFamily.() -> Unit)
    fun behavior(name: String, initializer: IBehavior.() -> Unit): SmartHomeConfigIfc
    fun getDevices(): List<Node>
    fun getAdjustableDevices(): List<Node>
}

fun FVEConfig(initializer: FVEConfig.() -> Unit): FVEConfig {
    val config = FVEConfig
    config.initializer()
    return config
}

data object FVEConfig : SmartHomeConfigIfc {
    private val families: MutableMap<NodeFamilyTypeEnum, NodeFamily> = mutableMapOf()
    private val behaviours: MutableList<IBehavior> = arrayListOf()

    override fun addFamily(family: NodeFamily): SmartHomeConfigIfc {
        if(families.containsKey(family.type))
            throw UnsupportedOperationException("Duplicate family type defined. type = " + family.type)
        this.families[family.type] = family
        return this
    }

    override fun family(type: NodeFamilyTypeEnum, initializer: NodeFamily.() -> Unit){
        val nodeFamily = NodeFamily(type);
        nodeFamily.apply{
            initializer()
        }
        addFamily(nodeFamily)
    }

    override fun behavior(name: String, initializer: IBehavior.() -> Unit): SmartHomeConfigIfc {
        val behaviour = Behavior(name)
        behaviour.apply{
            initializer()
        }
        behaviours.add(behaviour)
        return this
    }

    override fun getDevices(): List<Node> {
        return families.map{
           return it.value.getNodes()
        }
    }

    override fun getAdjustableDevices(): List<Node> {
        return getDevices().filter{it.adjustable}
    }

    fun nodeValue(family: NodeFamilyTypeEnum, nodeName: String){
        families[family]?.getNode(nodeName);
    }

    fun notification(s: String) {
        // create notification with given
    }
}

