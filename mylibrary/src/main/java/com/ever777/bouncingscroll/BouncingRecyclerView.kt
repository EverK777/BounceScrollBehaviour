package com.ever777.bouncingscroll

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class BouncingRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), BounceBehaiviour {

    private var isOverScrolling = false
    private var isOverScrollingVertical = true
    private var oldYMove = 0f
    private var oldXMove = 0f
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var currentXTranslation = 0f
    private var currentYTranslation = 0f
    private var isFreeScroll = false
    private var oppositeIsScrolling = false
    private var  mVelocityTracker : VelocityTracker?= null
    var activateBounceAnim = true

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

            v.parent.requestDisallowInterceptTouchEvent(true)

            val rawY: Float
            val rawX: Float
            val location = intArrayOf(0, 0)
            v.getLocationOnScreen(location)
            rawY = event.rawY + location[1]
            rawX = event.rawX + location[0]

            if (!isOverScrolling) {
                oldYMove = rawY
                oldXMove = rawX
                isOverScrolling = true

                if (!this.layoutManager!!.canScrollVertically()) {
                    isOverScrollingVertical = false
                }

                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain()
                } else {
                    mVelocityTracker?.clear()
                }
            }

            val deltaY = oldYMove - rawY
            val deltaX = oldXMove - rawX

            if (event.action == MotionEvent.ACTION_MOVE) {
                mVelocityTracker?.addMovement(event)
                mVelocityTracker?.computeCurrentVelocity(1000)

                if (!isOverScrollingVertical || oppositeIsScrolling) {
                    if (!this.canScrollHorizontally(1)) {
                        if (deltaX > 0 && oldXMove != rawX) {
                            for (i in 0 until this.childCount) {
                                val view: View = this.getChildAt(i)
                                view.translationX = (deltaX * 0.50f) * -1
                                currentXTranslation = view.translationX
                            }
                            oppositeIsScrolling = true
                            return@setOnTouchListener false
                        }
                    }
                    if (!this.canScrollHorizontally(-1) || oppositeIsScrolling) {
                        if (deltaX < 0 && oldXMove != rawX) {
                            for (i in 0 until this.childCount) {

                                val view: View = this.getChildAt(i)
                                view.translationX = (deltaX * 0.50f) * -1
                                currentXTranslation = view.translationX
                            }
                            oppositeIsScrolling = true
                            return@setOnTouchListener false
                        }

                    }
                }

                // vertical scroll
                else {
                    if (!this.canScrollVertically(-1) || oppositeIsScrolling) {
                        isOverScrolling = true
                        if (deltaY < 0 && deltaY != rawY) {
                            for (i in 0 until this.childCount) {
                                val view: View = this.getChildAt(i)
                                view.translationY = (deltaY * 0.50f) * -1
                                currentYTranslation = view.translationY
                            }
                            oppositeIsScrolling = true
                            return@setOnTouchListener false
                        }
                    }
                    if (!this.canScrollVertically(1) || oppositeIsScrolling) {
                        isOverScrolling = true
                        if (rawY != oldYMove && deltaY > 0) {
                            for (i in 0 until this.childCount) {
                                val view: View = this.getChildAt(i)
                                view.translationY = (deltaY * 0.50f) * -1
                                currentYTranslation = view.translationY
                            }
                            oppositeIsScrolling = true
                            return@setOnTouchListener false
                        }
                    }
                }

                oldYMove = rawY
                oldXMove = rawX
                isFreeScroll = true
            }
            if (event.action == MotionEvent.ACTION_UP) {


                for (i in 0 until this.childCount) {
                    val view: View = this.getChildAt(i)

                    if (isOverScrolling && isOverScrollingVertical) {
                        view.translationYAnimation(isFreeScroll,activateBounceAnim,mVelocityTracker)
                    } else if (isOverScrolling && !isOverScrollingVertical) {
                        view.translationXAnimation(isFreeScroll, activateBounceAnim,mVelocityTracker)
                    }
                }
                if (isOverScrolling) {
                    isOverScrolling = false
                    oldYMove = 0f
                    oldXMove = 0f
                }

                currentYTranslation = 0f
                currentXTranslation = 0f
                oppositeIsScrolling = false
                isFreeScroll = false
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
