package com.isaacsufyan.catchu.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import com.google.android.material.color.MaterialColors

class MaterialArrayAdapter<T>(
    context: Context,
    resource: Int,
    objects: List<T>,
    simpleItemSelectedColor: Int,
    simpleItemSelectedRippleColor: ColorStateList?
) : ArrayAdapter<T>(context, resource, objects) {

    var simpleItemSelectedColor: Int
    private val simpleItemSelectedRippleColor: ColorStateList?
    private var selectedText: String? = null
    private var selectedItemRippleOverlaidColor: ColorStateList? = null
    private var pressedRippleColor: ColorStateList? = null

    init {
        updateSelectedItemColorStateList()
        this.simpleItemSelectedColor = simpleItemSelectedColor
        this.simpleItemSelectedRippleColor = simpleItemSelectedRippleColor
    }

    fun updateColor(selectedText: String?) {
        this.selectedText = selectedText
    }

    private fun updateSelectedItemColorStateList() {
        pressedRippleColor = sanitizeDropdownItemSelectedRippleColor()
        selectedItemRippleOverlaidColor = createItemSelectedColorStateList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        if (view is TextView) {
            val isSelectedItem = selectedText.contentEquals(view.text)
            if (isSelectedItem) {
                view.setTypeface(null, Typeface.BOLD)
                ViewCompat.setBackground(view, selectedItemDrawable)
            } else {
                view.setTypeface(null, Typeface.NORMAL)
                ViewCompat.setBackground(view, null)
            }
        }
        return view
    }

    private val selectedItemDrawable: Drawable?
        get() {
            if (hasSelectedColor()) {
                return null
            }
            val colorDrawable: Drawable = ColorDrawable(simpleItemSelectedColor)
            return if (pressedRippleColor != null) {
                DrawableCompat.setTintList(colorDrawable, selectedItemRippleOverlaidColor)
                RippleDrawable(pressedRippleColor!!, colorDrawable, null)
            } else {
                colorDrawable
            }
        }

    private fun createItemSelectedColorStateList(): ColorStateList? {
        if (hasSelectedColor() || hasSelectedRippleColor()) {
            return null
        }
        val stateHovered = intArrayOf(android.R.attr.state_hovered, -android.R.attr.state_pressed)
        val stateSelected = intArrayOf(android.R.attr.state_selected, -android.R.attr.state_pressed)
        val colorSelected =
            simpleItemSelectedRippleColor!!.getColorForState(stateSelected, Color.TRANSPARENT)
        val colorHovered =
            simpleItemSelectedRippleColor.getColorForState(stateHovered, Color.TRANSPARENT)
        val colors = intArrayOf(
            MaterialColors.layer(simpleItemSelectedColor, colorSelected),
            MaterialColors.layer(simpleItemSelectedColor, colorHovered),
            simpleItemSelectedColor
        )
        val states = arrayOf(stateSelected, stateHovered, intArrayOf())
        return ColorStateList(states, colors)
    }

    private fun sanitizeDropdownItemSelectedRippleColor(): ColorStateList? {
        if (hasSelectedRippleColor()) {
            return null
        }
        val statePressed = intArrayOf(android.R.attr.state_pressed)
        val colors = intArrayOf(
            simpleItemSelectedRippleColor!!.getColorForState(statePressed, Color.TRANSPARENT),
            Color.TRANSPARENT
        )
        val states = arrayOf(statePressed, intArrayOf())
        return ColorStateList(states, colors)
    }

    private fun hasSelectedColor(): Boolean {
        return simpleItemSelectedColor == Color.TRANSPARENT
    }

    private fun hasSelectedRippleColor(): Boolean {
        return simpleItemSelectedRippleColor == null
    }
}