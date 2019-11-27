package com.ever777.bouncingscroll

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
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
    private var isOverScrollingVertical = true
    private var oldYMove = 0f
    private var oldXMove = 0f
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0


    init {
        val display = (getContext() as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
        configureScroll()
        this.overScrollMode = 2

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureScroll() {
        this.setOnTouchListener { v, event ->

            val rawY: Float
            val rawX: Float
            val location = intArrayOf(0, 0)
            v.getLocationOnScreen(location)
            rawY = event.rawY + location[1]
            rawX = event.rawX + location[0]

            if (event.action == MotionEvent.ACTION_DOWN) {
                oldYMove = rawY
                oldXMove = rawX
            }

            if (event.action == MotionEvent.ACTION_MOVE) {
                // horizontal scroll right to left
                if (this.canScrollHorizontally(1) || this.canScrollHorizontally(-1)) {
                    if (!this.canScrollHorizontally(1)) {
                        isOverScrollingVertical = false
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            val delta = oldXMove - rawX
                            if (delta > 0) {
                                isOverScrolling = true
                                if (oldXMove != rawX) {
                                    view.translationX  = (delta * 0.50f) * -1
                                }
                            }
                        }
                        return@setOnTouchListener false
                    }
                    // horizontal scroll left to right
                    if (!this.canScrollHorizontally(-1)) {
                        isOverScrollingVertical = false
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            val delta = oldXMove - rawX
                            if (delta < 0) {
                                isOverScrolling = true
                                if (oldXMove != rawX) {
                                    view.translationX  = (delta * 0.50f) * -1
                                }
                            }
                        }
                        return@setOnTouchListener false
                    }

                }

                //verticalMovement down to up
                if (this.canScrollVertically(1) || this.canScrollVertically(-1)) {
                    if (!this.canScrollVertically(1)) {
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            val delta = oldYMove - rawY
                            if (delta > 0) {
                                if (oldYMove != rawY) {
                                    isOverScrolling = true
                                    view.translationY  = (delta * 0.50f) * -1
                                }
                            }
                        }
                        return@setOnTouchListener false
                    }

                    //verticalMovement up to down
                    if (!this.canScrollVertically(-1)) {
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            val delta = oldYMove - rawY
                            if (delta < 0) {
                                if (oldYMove != rawY) {
                                    isOverScrolling = true
                                    view.translationY  = (delta * 0.50f) * -1
                                }
                            }
                        }
                        return@setOnTouchListener false

                    }
                }


                oldYMove = rawY
                oldXMove = rawX
            }
            if (event.action == MotionEvent.ACTION_UP) {

                for (i in 0 until this.childCount) {
                    val view: View = this.getChildAt(i)

                    if (isOverScrolling && isOverScrollingVertical) {
                        view.translationYAnimation()
                    } else if (isOverScrolling && !isOverScrollingVertical) {
                        view.translationXAnimation()
                    }

                }
                if (isOverScrolling) {
                    isOverScrolling = false
                    oldYMove = 0f
                    oldXMove = 0f
                }
            }
            false
        }
    }


}

private fun View.bouncingAnimationY() {
    val springAnimationY: SpringAnimation = this.let {
        SpringAnimation(it, DynamicAnimation.TRANSLATION_Y, it.translationY)
    }

    springAnimationY.setMaxValue(200f)

    val velocity = 750f
    springAnimationY.setStartVelocity(velocity)
    springAnimationY.spring?.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    springAnimationY.spring?.stiffness = SpringForce.STIFFNESS_MEDIUM

    springAnimationY.start()
}

private fun View.bouncingAnimationX() {
    val springAnimationX: SpringAnimation = this.let {
        SpringAnimation(it, DynamicAnimation.TRANSLATION_X, it.translationX)
    }

    springAnimationX.setMaxValue(200f)

    val velocity = 750f
    springAnimationX.setStartVelocity(velocity)
    springAnimationX.spring?.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    springAnimationX.spring?.stiffness = SpringForce.STIFFNESS_MEDIUM

    springAnimationX.start()
}

private fun View.translationXAnimation() {
    val objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, this.translationX, 0f)
    objectAnimator.duration = 180
    objectAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            this@translationXAnimation.bouncingAnimationX()
        }

        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}

    })
    objectAnimator.start()
}
private fun View.translationYAnimation() {
    val objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, this.translationY, 0f)
    objectAnimator.duration = 180
    objectAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            this@translationYAnimation.bouncingAnimationY()
        }

        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}

    })
    objectAnimator.start()
}