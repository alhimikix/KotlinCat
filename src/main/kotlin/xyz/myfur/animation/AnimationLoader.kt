package xyz.myfur.animation

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject

object AnimationLoader{

    var animations:ArrayList<Animation> = ArrayList()
        private set
    fun loadAnimations() {
        animations = ArrayList()
        JSON.parseArray(AnimationLoader.javaClass.getResourceAsStream("/animations.json").bufferedReader().readText())
                .forEach{
                    (it as JSONObject).apply {
                        var animation = Animation(
                                getString("name"),
                                getInteger("blocks"),
                                getInteger("block_width"),
                                getInteger("block_height"),
                                getLong("pause")
                        )
                        var layers = ArrayList<Layer>()
                        getJSONArray("layers").forEach { layer ->
                            (layer as JSONObject).apply{
                                layers.add(Layer(
                                        getFloat("alpha"),
                                        animation,
                                        getString("file_name")
                                ))
                        }}
                        animation.layers = layers
                        animations.add(animation)
                    }
                }
    }

    fun loadAnimationsIfNotLoaded() {
        if(animations.size==0) loadAnimations()
    }

}