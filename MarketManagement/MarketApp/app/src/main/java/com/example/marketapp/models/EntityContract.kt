package com.example.marketapp.models

import android.provider.BaseColumns

object EntityContract {
    const val DB_NAME = "db_example"
    const val DB_VERSION = 1

    // Table contents are grouped together in an anonymous object.
    object TaskEntry : BaseColumns {
        const val DB_TABLE = "product_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_STATUS = "status"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PRICE = "price"

    }
}