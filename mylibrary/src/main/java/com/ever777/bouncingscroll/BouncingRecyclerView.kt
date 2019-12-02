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
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView


class BouncingRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {


    private var isOverScrolling = false
    private var isOverScrollingVertical = true
    private var oldYMove = 0f
    private var oldXMove = 0f
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var currentXTranslation = 0f
    private var currentYTranslation = 0f
    private var isFreeScroll = false

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

            if (!isOverScrolling) {
                oldYMove = rawY
                oldXMove = rawX
            }

            if (event.action == MotionEvent.ACTION_MOVE) {
                if (!this.layoutManager!!.canScrollVertically()) {
                    if (!this.canScrollHorizontally(1)) {
                        isOverScrolling = true
                        isOverScrollingVertical = false
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            val delta = oldXMove - rawX
                            if (delta > 0) {
                                if (oldXMove != rawX) {
                                    view.translationX = (delta * 0.50f) * -1
                                    currentXTranslation = view.translationX
                                }
                            }
                        }
                        return@setOnTouchListener false
                    }
                    // horizontal scroll left to right
                    if (!this.canScrollHorizontally(-1)) {
                        isOverScrollingVertical = false
                        isOverScrolling = true
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            val delta = oldXMove - rawX
                            println("DELTAA $delta")
                            if (delta < 0) {
                                if (oldXMove != rawX) {
                                    view.translationX = (delta * 0.50f) * -1
                                    currentXTranslation = view.translationX
                                }
                            }
                        }
                        return@setOnTouchListener false
                    }

                }
                else {
                    isOverScrollingVertical = true
                    val delta = oldYMove - rawY
                    if (delta > 0 ){
                        isOverScrolling = true
                        isFreeScroll =false
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            view.translationY = (delta * 0.50f) * -1
                            currentYTranslation = view.translationY
                        }
                        return@setOnTouchListener false
                    }else {
                        isOverScrolling = true
                        isFreeScroll =false
                        for (i in 0 until this.childCount) {
                            val view: View = this.getChildAt(i)
                            view.translationY = (delta * 0.50f) * -1
                            currentYTranslation = view.translationY
                        }
                        return@setOnTouchListener false
                    }
                }
                oldYMove = rawY
                oldXMove = rawX
            }
            if (event.action == MotionEvent.ACTION_UP) {

                if(isFreeScroll){
                    return@setOnTouchListener false
                }

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

                currentYTranslation = 0f
                currentXTranslation = 0f
            }
            false
        }
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        child.translationY = currentYTranslation
        child.translationX = currentXTranslation
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