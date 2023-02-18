package com.example.test_task.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MovieModel {

    @SerializedName("Search")
    @Expose
    var movieData: List<MovieData>? = null

    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = null

    @SerializedName("Response")
    @Expose
    var response: String? = null

}

class MovieData : Serializable {
    @SerializedName("Title")
    @Expose
    var title: String? = null

    @SerializedName("Year")
    @Expose
    var year: String? = null

    @SerializedName("imdbID")
    @Expose
    var imdbID: String? = null

    @SerializedName("Type")
    @Expose
    var type: String? = null

    @SerializedName("Poster")
    @Expose
    var poster: String? = null

}