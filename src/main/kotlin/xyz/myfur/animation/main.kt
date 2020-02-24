package xyz.myfur.animation



import xyz.myfur.animation.modules.animation.AnimationLoader
import xyz.myfur.animation.modules.animation.Render
import java.util.*
import kotlin.concurrent.thread


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










