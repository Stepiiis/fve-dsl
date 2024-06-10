package fit.bikot.dsl.fve

import fit.bikot.dsl.fve.entity.FVEConfig
import fit.bikot.dsl.fve.enums.NodeFamilyTypeEnum.*
import fit.bikot.dsl.fve.enums.ProtocolTypeEnum.*
import fit.bikot.dsl.fve.util.atTime
import fit.bikot.dsl.fve.util.greaterThan
import fit.bikot.dsl.fve.util.lessThan

fun main() {
    FVEConfig {

        family(PV_INVERTER) {

            node(MQTT) {
                endpoint = "mqtt://192.168.1.10/solar"
                onEvent("sunset") {
                    condition {
                        atSunset()
                    }
                    action {
                        send(42)
                    }
                }
            }

            node(MQTT) {
                name = "SOLAR_GENERATION"
                endpoint = "mqtt://192.168.1.11/solar_generation"
            }

            node(MQTT) {
                name = "ENERGY_USAGE"
                endpoint = "mqtt://192.168.1.11/usage"
            }

            node(MQTT) {
                name = "BATTERY_STORAGE"
                endpoint = "mqtt://192.168.1.11/battery"
                onEvent("lowBattery") {
                    condition {
                        it lessThan 20
                    }
                    action {
                        send("CONSERVE_ENERGY")
                        notification("Battery low, conserving energy")
                    }
                }
            }
        }

        family(HVAC) {
            node(MQTT) {
                name = "HVAC"
                adjustable = true
                endpoint = "mqtt://192.168.1.12/hvac"
                onEvent("solarSurplus") {
                    condition {
                        nodeValue(PV_INVERTER, "SOLAR_GENERATION") greaterThan
                                nodeValue(PV_INVERTER, "ENERGY_USAGE")
                    }
                    action {
                        send(true)
                    }
                }
                onEvent("solarDeficit") {
                    condition {
                        nodeValue(PV_INVERTER, "SOLAR_GENERATION") lessThan
                                nodeValue(PV_INVERTER, "ENERGY_USAGE")
                    }
                    action {
                        send(false)
                    }
                }
                reductionAction {
                    send("REDUCE_POWER")
                }
            }
        }

        behavior("Energy Saving Mode") {
            trigger {
                atTime(22, 0)
            }
            action {
                getAdjustableDevices().forEach { it.reducePower() }
                notification("Switched to energy saving mode")
            }
        }
    }
}