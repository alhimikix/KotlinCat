package xyz.myfur.animation.modules.animation

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.beust.klaxon.Klaxon
import java.io.File
import java.io.InputStream
import kotlin.concurrent.thread

object AnimationLoader {

    var readFromJar = true

    var animations: List<Animation> = ArrayList()
        private set

    fun loadAnimations() {
        animations = ArrayList()
        val exists = File("./KotlinCat/animations.json")
        val stream =
                if (exists.exists()) {
                    readFromJar = false
                    exists.inputStream()
                } else
                    AnimationLoader.javaClass.getResourceAsStream("/animations.json")
        animations = Klaxon().parseArray(stream.bufferedReader().readText())!!
    }


    fun loadAnimationsIfNotLoaded() {
        if (animations.isEmpty()) loadAnimations()
    }

    fun loadImage(fileName: String): InputStream {
        return if (readFromJar) AnimationLoader::class.java.getResourceAsStream(fileName) else File(fileName).inputStream()
    }


}