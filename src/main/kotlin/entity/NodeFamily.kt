package fit.bikot.dsl.fve.entity

import fit.bikot.dsl.fve.enums.NodeFamilyTypeEnum
import fit.bikot.dsl.fve.enums.ProtocolTypeEnum

class NodeFamily(val type: NodeFamilyTypeEnum){
    private val nodes: MutableMap<String, Node> = mutableMapOf()

    fun getNode(name: String): Node? {
        return nodes[name];
    }

    fun getNodes(): List<Node>{
        return nodes.values.toList();
    }

    fun addNode(node: Node): NodeFamily {
        if(nodes.containsKey(node.name)){
            throw UnsupportedOperationException("Cannot add node with duplicate name.")
        }
        this.nodes[node.name] = node
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


