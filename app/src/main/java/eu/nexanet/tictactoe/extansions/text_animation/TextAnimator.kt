package eu.nexanet.tictactoe.extansions.text_animation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class TextAnimator {

    companion object {
        fun animateText(
            context: Context,
            from: String,
            to: String,
            duration: Long,
            callback: TextAnimatorCallback
        ) {
            Thread {
                val delay = duration / (to.length + Math.abs(to.length - from.length))
                val animatedText = StringBuilder(from)

                if (from.length == to.length) {
                    for (i in 0..from.length) {
                        if (i != from.length) animatedText.setCharAt(i, '▐')
                        if (i - 1 >= 0) animatedText.setCharAt(i - 1, to[i - 1])
                        runOnUiThread(context, animatedText.toString(), callback)
                        Thread.sleep(delay)
                    }
                } else if (from.length > to.length) {
                    for (i in 0..to.length) {
                        if (i != to.length) animatedText.setCharAt(i, '▐')
                        if (i - 1 >= 0) animatedText.setCharAt(i - 1, to[i - 1])
                        runOnUiThread(context, animatedText.toString(), callback)
                        Thread.sleep(delay)
                    }

                    for (i in to.length..<from.length) {
                        animatedText.deleteCharAt(to.length)
                        if (animatedText.length != to.length) animatedText.setCharAt(to.length, '▐')
                        runOnUiThread(context, animatedText.toString(), callback)
                        Thread.sleep(delay)
                    }
                } else if (from.length < to.length) {
                    for (i in 0..from.length) {
                        if (i - 1 >= 0) animatedText.setCharAt(i - 1, to[i - 1])
                        if (i != from.length) animatedText.setCharAt(i, '▐')
                        runOnUiThread(context, animatedText.toString(), callback)
                        Thread.sleep(delay)
                    }

                    for (i in from.length..<to.length) {
                        animatedText.append(to[i])
                        runOnUiThread(context, animatedText.toString(), callback)
                        Thread.sleep(delay)
                    }
                }
            }.start()
        }

        private fun runOnUiThread(context: Context, text: String, callback: TextAnimatorCallback) {
            (context as AppCompatActivity).runOnUiThread {
                callback.onTextChange(text)
            }
        }
    }
}