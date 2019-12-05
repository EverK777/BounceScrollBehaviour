package com.ever777.bouncingscroll

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import kotlin.math.abs


class BouncingNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr), BounceBehaiviour {

    private var isOverScrolling = false
    private var isOverScrollingVertical = true
    private var oldYMove = 0f
    private var oldXMove = 0f
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var currentXTranslation = 0f
    private var currentYTranslation = 0f
    private var isFreeScroll = false
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
                if (this.canScrollHorizontally(1) || this.canScrollHorizontally(-1) ) {
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
                else if(this.canScrollVertically(1) || this.canScrollVertically(-1)) {
                    val delta = oldYMove - rawY
                    if (!this.canScrollVertically(1)){
                        isOverScrollingVertical = true
                        isOverScrolling = true
                        isFreeScroll =false
                        for (i in 0 until this.childCount) {
                            if(delta > 0 && oldXMove != rawY){
                                val view: View = this.getChildAt(i)
                                view.translationY = (delta * 0.50f) * -1
                                currentYTranslation = view.translationY
                            }
                        }
                        return@setOnTouchListener false
                    }
                    if(!this.canScrollVertically(-1)) {
                        isOverScrollingVertical = true
                        isOverScrolling = true
                        isFreeScroll =false
                        for (i in 0 until this.childCount) {
                            if(delta < 0 && oldXMove != rawY) {
                                val view: View = this.getChildAt(i)
                                view.translationY = (delta * 0.50f) * -1
                                currentYTranslation = view.translationY
                            }
                        }
                        return@setOnTouchListener false
                    }
                }
                oldYMove = rawY
                oldXMove = rawX
                isFreeScroll = true
            }
            if (event.action == MotionEvent.ACTION_UP) {
                val delta = oldYMove - rawY
                if( abs(delta) < 80f){
                    isFreeScroll = true
                }

                for (i in 0 until this.childCount) {
                    val view: View = this.getChildAt(i)

                    if (isOverScrolling && isOverScrollingVertical) {
                        view.translationYAnimation(isFreeScroll,activateBounceAnim)
                    } else if (isOverScrolling && !isOverScrollingVertical) {
                        view.translationXAnimation(isFreeScroll,activateBounceAnim)
                    }

                }
                if (isOverScrolling) {
                    isOverScrolling = false
                    oldYMove = 0f
                    oldXMove = 0f
                }

                currentYTranslation = 0f
                currentXTranslation = 0f

                isFreeScroll = false
            }
            false
        }
    }

}
