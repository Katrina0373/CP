package com.mocalovak.cp.domain.model

data class PassiveEffect(val parameter:String, val bonus: Int, val description:String){
    override fun toString():String {
        return description
    }
}

fun List<PassiveEffect>?.takeString(): String {
    var result: String = ""
    this?.forEach {
        result += "$it, "
    }
    if(result.isNotEmpty()){
        result = result.dropLast(2)
    }
    return result
}
