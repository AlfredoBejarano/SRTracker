package com.alfredobejarano.srtracker.base

import android.content.res.Resources
import android.text.Editable

/**
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-13
 */
/**
 * Checks if a Editable is empty, if it is
 * returns Zero (0) as a string, if not, returns
 * the value of the Editable as string.
 *
 * @return a blank-safe value of an Editable.
 */
fun safeString(text: Editable?) = if (text?.isBlank()!!) "0" else text.toString()

/**
 * @param value The DP quantity.
 * @return the value of int as DP.
 */
fun getDP(value: Int, resources: Resources): Int = (value * resources.displayMetrics.density + 0.5f).toInt()