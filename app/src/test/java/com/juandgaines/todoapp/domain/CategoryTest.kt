package com.juandgaines.todoapp.domain

import com.google.common.truth.Truth
import org.junit.Test

class CategoryTest {

    @Test
    fun `from ordinal out of scope applied fromOrdinal must return null`() {
        val category = Category.fromOrdinal(15)
        Truth.assertThat(category).isNull()
    }

    @Test
    fun `from ordinal in scope applied fromOrdinal must return the correct category`() {
        val category = Category.fromOrdinal(2)
        Truth.assertThat(category).isEqualTo(Category.SHOPPING)
    }

}