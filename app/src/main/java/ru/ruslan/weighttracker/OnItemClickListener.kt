package ru.ruslan.weighttracker

import java.text.FieldPosition

interface OnItemClickListener {
    fun itemClick(position: Int)
    fun itemLongClick(position: Int)
}