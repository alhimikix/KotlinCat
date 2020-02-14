package xyz.myfur.animation

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.awt.*

import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.concurrent.timer


fun main() {
    val render = Render()
    animationConsoleLoader(render)


}

fun animationConsoleLoader(render: Render){
    thread(name = "Console") {
        while (true){
            println("Enter animation name")
            val sc = Scanner(System.`in`)
            val name = sc.nextLine().trim()
            val anim = AnimationLoader.animations.firstOrNull { it.name == name }
            if (anim!=null){
                render.render(anim)
            }else{
                println("Animation found!!")
            }
        }
    }
}










