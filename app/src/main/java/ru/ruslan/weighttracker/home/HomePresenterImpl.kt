package ru.ruslan.weighttracker.home

import ru.ruslan.weighttracker.home.contract.HomeContract

class HomePresenterImpl : HomeContract.Presenter{

    private var homeView: HomeContract.View? = null

    override fun setView(view: HomeContract.View) {
        homeView = view
    }

    override fun init() {
        homeView?.initVars()
        homeView?.initViews()
        homeView?.setListeners()
    }

    override fun getPhotoBefore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPhotoAfter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeightList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun photoBeforeViewClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun photoAfterViewClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fabVievClicked(what: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}