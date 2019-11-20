package ru.ruslan.weighttracker

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.*
import ru.ruslan.weighttracker.network.ApiFactory
import ru.ruslan.weighttracker.network.YoutubeApi
import kotlin.coroutines.CoroutineContext

class VideosFragment : Fragment(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var restClient: YoutubeApi
    private var ctx: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ctx?.let {
            restClient = ApiFactory.getRestClient(it)
            job = CoroutineScope(coroutineContext).launch {
                val result = async { restClient.getPlaylistVideos() }
                val response = result.await()

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideosFragment()
    }
}
