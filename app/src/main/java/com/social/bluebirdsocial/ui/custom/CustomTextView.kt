package com.social.bluebirdsocial.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import com.social.bluebirdsocial.R

@SuppressLint("UseCompatLoadingForDrawables")
class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    init {
        val attr = context.theme.obtainStyledAttributes(attrs,R.styleable.CustomTextView,0,0)

        val colorHeader = attr.getColor(R.styleable.CustomTextView_colorHeader, Color.BLACK)

        val iconLeft = attr.getResourceId(R.styleable.CustomTextView_leftIcon, 0)
        val iconTop = attr.getResourceId(R.styleable.CustomTextView_topIcon, 0)
        val iconRight = attr.getResourceId(R.styleable.CustomTextView_rightIcon, 0)
        val iconBottom = attr.getResourceId(R.styleable.CustomTextView_bottomIcon, 0)

        setCompoundDrawablesWithIntrinsicBounds(
            if(iconLeft != 0) context.getDrawable(iconLeft) else null,
            if(iconTop != 0) context.getDrawable(iconTop) else null,
            if(iconRight != 0) context.getDrawable(iconRight) else null,
            if(iconBottom != 0) context.getDrawable(iconBottom) else null)


        setTypeface(null, Typeface.BOLD_ITALIC)
        setTextColor(colorHeader)
//        applyFont(attr)
        attr.recycle()

    }

//    private fun applyFont(attr: TypedArray){
//        val headTypeface = attr.getResourceId(R.styleable.CustomButton_buttonFont, R.font.svn_gilroy_regular)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            typeface = when (headTypeface) {
//                R.font.svn_gilroy_bold -> {
//                    context.resources.getFont(headTypeface)
//                }
//                R.font.svn_gilroy_regular -> {
//                    context.resources.getFont(headTypeface)
//                }
//                R.font.svn_gilroy_xbold -> {
//                    context.resources.getFont(headTypeface)
//                }
//                else ->
//                    context.resources.getFont(R.font.svn_gilroy_regular)
//            }
//        } else {
//            typeface = Typeface.MONOSPACE
//        }
//
//    }

}