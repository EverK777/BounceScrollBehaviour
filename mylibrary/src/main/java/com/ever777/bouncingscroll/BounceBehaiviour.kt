package com.ever777.bouncingscroll

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.VelocityTracker
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import java.lang.Math.abs

interface BounceBehaiviour {

    //  animation when the user makes an a over horizontal scroll
     fun View.translationXAnimation(isFreeScroll : Boolean, activateBounceAnim : Boolean, velocityTracker: VelocityTracker?) {
        val objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, this.translationX, 0f)
        objectAnimator.duration = 180
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if(!isFreeScroll && activateBounceAnim){
                    this@translationXAnimation.bouncingAnimationX(velocityTracker)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

        })
        objectAnimator.start()
    }
    //  animation when the user makes an a over vertical scroll
     fun View.translationYAnimation(isFreeScroll : Boolean, activateBounceAnim : Boolean, velocityTracker: VelocityTracker?) {
        val objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, this.translationY, 0f)
        objectAnimator.duration = 180
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if(!isFreeScroll && activateBounceAnim){
                    this@translationYAnimation.bouncingAnimationY(velocityTracker)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

        })
        objectAnimator.start()
    }

    //  Bounce physic animations
    private fun View.bouncingAnimationY(velocityTracker: VelocityTracker?) {
        val springAnimationY: SpringAnimation = this.let {
            SpringAnimation(it, DynamicAnimation.TRANSLATION_Y, it.translationY)
        }
        if(velocityTracker !=null){
            springAnimationY.setMaxValue(200f)
            var velocity = velocityTracker.xVelocity
            if(kotlin.math.abs(velocity) > 300f){
                velocity = 300f
            }
            springAnimationY.setStartVelocity(velocity)
            springAnimationY.spring?.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            springAnimationY.spring?.stiffness = SpringForce.STIFFNESS_MEDIUM

            springAnimationY.start()
        }

    }

    private fun View.bouncingAnimationX(velocityTracker: VelocityTracker?) {
        val springAnimationX: SpringAnimation = this.let {
            SpringAnimation(it, DynamicAnimation.TRANSLATION_X, it.translationX)
        }
        if(velocityTracker !=null){
            springAnimationX.setMaxValue(200f)
            var velocitx = velocityTracker.xVelocity
            if(kotlin.math.abs(velocitx) > 300f){
                velocitx = 300f
            }
            springAnimationX.setStartVelocity(velocitx)
            springAnimationX.spring?.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            springAnimationX.spring?.stiffness = SpringForce.STIFFNESS_MEDIUM

            springAnimationX.start()
        }
    }
}