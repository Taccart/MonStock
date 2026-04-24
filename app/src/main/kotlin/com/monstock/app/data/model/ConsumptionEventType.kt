package com.monstock.app.data.model

enum class ConsumptionEventType {
    /** Item was consumed normally. */
    CONSUMED,

    /** Item was thrown away before being consumed (waste). */
    WASTED
}
