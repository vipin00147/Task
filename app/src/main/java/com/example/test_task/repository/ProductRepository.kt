package com.example.test_task.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.example.test_task.R
import com.example.test_task.model.MovieModel
import com.example.test_task.network.ApiConstants
import com.example.test_task.network.ApiConstants.API_KEY
import com.example.test_task.network.responseAndErrorHandle.ApiResponse
import com.example.test_task.network.responseAndErrorHandle.ErrorMessage
import com.example.test_task.ui.activities.MainActivity
import com.example.test_task.utils.ConnectivityReceiver
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository : ApiResponse, Callback<JsonElement> {

    constructor()

    lateinit var baseContainer : MainActivity<ViewBinding>

   fun initContext(mainActivity: MainActivity<ViewBinding>) {
       this.baseContainer = mainActivity
   }

    private var getCallVideos : Call<JsonElement>?= null

    lateinit var apiResponse : ApiResponse

    val isProgressShowing : MutableLiveData<Boolean> = MutableLiveData()
    var productData : MutableLiveData<MovieModel> = MutableLiveData()
    var errorOccur : MutableLiveData<Boolean> = MutableLiveData()

    private var errorMessageModel : MutableLiveData<ErrorMessage> = MutableLiveData()


    fun getVideoList(
        productData: MutableLiveData<MovieModel>,
        pageNo: Int,
        showLoader: Boolean,
        errorOccur: MutableLiveData<Boolean>
    ) {
        this.productData = productData
        this.errorOccur = errorOccur

        val movieParams = HashMap<String, String>()
        movieParams["apikey"] = API_KEY
        movieParams["s"] = "Marvel"
        movieParams["type"] = "movie"
        movieParams["page"] = pageNo.toString()

        getCallVideos = ApiConstants.getApiServices().getData(movieParams)
        hitApi(getCallVideos, showLoader, baseContainer, this)

    }

    private fun hitApi(call: Call<JsonElement>?, showProgress: Boolean, context: Context, listener: ApiResponse) {

        if (ConnectivityReceiver().isConnectedOrConnecting(context)) {
            if(showProgress) {
                isProgressShowing.value = showProgress
            }
            call?.enqueue(this)
            apiResponse = listener
        } else {
            errorOccur.value = true
            baseContainer.showHomeErrorSnackBar(baseContainer.resources.getString(R.string.no_internet_available))
        }
    }

    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
        isProgressShowing.value = false
        if (response.isSuccessful) {
            apiResponse.onSuccess(call, response.code(), response.body()!!.toString())
        } else {
            try {
                errorOccur.value = true
                errorMessageModel.value = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
                apiResponse.onError(call, response.code(), errorMessageModel.value?.message.toString())
            }
            catch (ex : Exception) {
                errorOccur.value = true
                ex.printStackTrace()
            }
        }
    }

    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
        isProgressShowing.value = false
        errorOccur.value = true
        baseContainer.showHomeErrorSnackBar(t.message.toString())
    }

    override fun onSuccess(call: Call<JsonElement>, responseCode: Int, response: String) {
        if(call == getCallVideos) {
            productData.value = Gson().fromJson(response, MovieModel::class.java)
        }
    }

    override fun onError(call: Call<JsonElement>, errorCode: Int, errorMsg: String) {
        isProgressShowing.value = false
        errorOccur.value = true
        baseContainer.showHomeErrorSnackBar(errorMsg)
    }

    companion object {
        private const val TAG = "ProductRepository"
        val repository = ProductRepository()
    }
}