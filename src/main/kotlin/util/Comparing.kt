package fit.bikot.dsl.fve.util

import fit.bikot.dsl.fve.entity.Node
import java.time.Instant
import java.time.LocalDateTime

fun atTime(hours: Int, minutes: Int) {
    val time = LocalDateTime.of(0,0,0,hours,minutes)
    emitTimeEquals(time);
}

fun emitComparingImpl(lhs: Any, rhs: Any, lessThan: Boolean = true){
    if(lhs is Now && rhs is LocalDateTime){
        emitAfterTime(rhs);
    } else {
        if (lhs is Node) {
            // todo emit config to get node value
        } else {
            // todo emit value
        }
        if(lessThan)
        // emit less than
        else
        // emit more than
            if (rhs is Node) {
                // todo emit config to get node value
            } else {
                // todo emit value
            }
    }
}


fun emitTimeComparingimpl(rhs: LocalDateTime, beforeThan: Boolean = true){
    if(beforeThan)
        emitBeforeTime(rhs)
    else
        emitAfterTime(rhs)
}

fun comparingImpl(lhs: Any, rhs: Any, lessThan: Boolean = true){
    when(lhs){

        // Type < ( Node | Number )
        is Int, Short, Double, Float, Long, Char, Short, Byte -> {
            when(rhs){
                is Node, Int, Short, Double, Float, Long, Char, Short, Byte -> {
                    emitComparingImpl(lhs, rhs, lessThan);
                }
                else -> {
                    throw UnsupportedOperationException("Right hand side value of non matching type.")
                }
            }
        }

        // CurrentTime < GivenTime
        is Now -> {
            when (rhs) {
                is LocalDateTime -> emitTimeComparingimpl(rhs)
                is Instant -> emitTimeComparingimpl(LocalDateTime.from(rhs))
                else -> throw UnsupportedOperationException("Right hand side value of non matching type.")
            }
        }

        // Node < ( Node | Number)
        is Node -> {
            when (rhs){
                is Node, Int, Short, Double, Float, Long, Char, Short, Byte -> emitComparingImpl(lhs, rhs)
                else -> throw UnsupportedOperationException("Values of unsupported type.")
            }
        }

        else -> throw UnsupportedOperationException("Values of unsupported type.")

    }
}

infix fun Any.greaterThan(that: Any): Unit{
    comparingImpl(this, that, lessThan = false);
}


infix fun Any.lessThan(that: Any): Unit{
    comparingImpl(this, that, lessThan = true);
}


fun emitAfterTime(rhs: LocalDateTime) {
    // todo emit config to compare current time with hour and minute of day
}

fun emitBeforeTime(rhs: LocalDateTime){
    // todo emit config to compare current time with hour and minute of day
}

fun emitTimeEquals(time: LocalDateTime){
    // todo emit config to compare current time with hour and minute of day
}


object Sunset {}

object Now {}