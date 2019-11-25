package com.ever777.bouncingscroll

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class BouncingNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private var isOverScrolling = false
    private var oldYMove = 0f


    init {
        configureScroll()
        this.overScrollMode = 2
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureScroll() {
        this.setOnTouchListener { v, event ->


            val rawY: Float
            val location = intArrayOf(0, 0)
            v.getLocationOnScreen(location)
            rawY = event.rawY + location[1]



            if (event.action == MotionEvent.ACTION_DOWN) {
                oldYMove = rawY

            }

            if (event.action == MotionEvent.ACTION_MOVE) {
                if (!this.canScrollVertically(1)) {
                    for (i in 0 until this.childCount) {
                        val view: View = this.getChildAt(i)
                        val delta = oldYMove - rawY
                        if (delta > 0) {
                            isOverScrolling = true
                            if (view.translationY > -25f && oldYMove != rawY) {
                                view.translationY -= 0.85f
                            }
                        }
                    }

                    //scroll up to down
                } else if (!this.canScrollVertically(-1)) {
                    for (i in 0 until this.childCount) {
                        val view: View = this.getChildAt(i)
                        val delta = rawY - oldYMove
                        if (delta > 0) {
                            isOverScrolling = true
                            if (view.translationY < 25f && oldYMove != rawY) {
                                view.translationY += 0.85f
                            }
                        }
                    }
                } else {
                    for (i in 0 until this.childCount) {
                        val view: View = this.getChildAt(i)
                        val delta = rawY - oldYMove
                        if (delta > 0) {
                            view.translationY = 0f
                        }
                    }
                }
                oldYMove = rawY
            }
            if (event.action == MotionEvent.ACTION_UP) {

                for (i in 0 until this.childCount) {
                    val view: View = this.getChildAt(i)
                    view.translationY = 0f
                    if (isOverScrolling) {
                        view.bouncingAnimation()
                    }
                }
                if (isOverScrolling) {
                    isOverScrolling = false
                    oldYMove = 0f
                }
            }
            false
        }
    }
}

private fun View.bouncingAnimation() {
    val springAnimationY: SpringAnimation = this.let {
        SpringAnimation(it, DynamicAnimation.TRANSLATION_Y, it.translationY)
    }

    springAnimationY.setMaxValue(500f)

    val velocity = 1000f
    springAnimationY.setStartVelocity(velocity)
    springAnimationY.spring?.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    springAnimationY.spring?.stiffness = SpringForce.STIFFNESS_MEDIUM

    springAnimationY.start()
}
