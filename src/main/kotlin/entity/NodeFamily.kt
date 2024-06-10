package fit.bikot.dsl.fve.entity

import fit.bikot.dsl.fve.enums.NodeFamilyTypeEnum
import fit.bikot.dsl.fve.enums.ProtocolTypeEnum

class NodeFamily(val type: NodeFamilyTypeEnum){
    private val nodeDict: MutableMap<String, Node> = mutableMapOf()

    fun getNode(name: String): Node? {
        return nodeDict[name];
    }

    fun getNodes(): List<Node>{
        return nodeDict.values.toList();
    }

    private fun addNode(node: Node): NodeFamily {
        if(nodeDict.containsKey(node.name)){
            throw UnsupportedOperationException("Cannot add node with duplicate name.")
        }
        this.nodeDict[node.name] = node
        return this
    }

    fun node(protocol: ProtocolTypeEnum, initializer: Node.() -> Unit) {
        val node = createNode(protocol);
        node.apply {
            initializer()
        }
        addNode(node)
    }
}


