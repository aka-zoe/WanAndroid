package com.zoe.wan.android.repository.data

import android.os.Parcel
import android.os.Parcelable

class DetailIntentList(var list: List<DetailTabIntentData>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(DetailTabIntentData) ?: emptyList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailIntentList> {
        override fun createFromParcel(parcel: Parcel): DetailIntentList {
            return DetailIntentList(parcel)
        }

        override fun newArray(size: Int): Array<DetailIntentList?> {
            return arrayOfNulls(size)
        }
    }


}

class DetailTabIntentData(val name: String, val id: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailTabIntentData> {
        override fun createFromParcel(parcel: Parcel): DetailTabIntentData {
            return DetailTabIntentData(parcel)
        }

        override fun newArray(size: Int): Array<DetailTabIntentData?> {
            return arrayOfNulls(size)
        }
    }

}
