package com.carles.carleskotlin.common.ui

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.reactivex.disposables.Disposable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BasePresenterTest {

    private lateinit var presenter : BasePresenter<BaseView>

    @Before
    fun setup() {
        presenter = object : BasePresenter<BaseView>(mockk()) {}
    }

    @Test
    fun onViewDestroyed_shouldDispose() {
        val disposable : Disposable = mockk()
        every { disposable.dispose() } just Runs
        presenter.addDisposable(disposable)
        presenter.onViewDestroyed()
        assertTrue(presenter.disposables.isDisposed)
    }

    @Test
    fun addDisposable_shouldAdd() {
        presenter.addDisposable(mockk())
        assertEquals(1, presenter.disposables.size())
        assertFalse(presenter.disposables.isDisposed)
    }
}