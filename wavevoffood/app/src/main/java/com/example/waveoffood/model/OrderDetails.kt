package com.example.waveoffood.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import kotlin.collections.ArrayList

class OrderDetails():Serializable {
    var userUid : String? = null
    var userName: String? = null
    var foodName: MutableList<String>?= null
    var foodImage: MutableList<String>?= null
    var foodPrice: MutableList<String>? = null
    var foodQuantity: MutableList<Int>?= null
    var address: String? = null
    var totalPrice: String?= null
    var phoneNumber: String?= null
    var orderAccepted: Boolean?= false
    var paymentReceived: Boolean?= false
    var itemPushKey: String?= null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        paymentReceived = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        foodName: ArrayList<String>,
        foodPrice: ArrayList<String>,
        foodImage: ArrayList<String>,
        foodQuantities: ArrayList<Int>,
        address: String,
        phone: String,
        time: Long,
        itemPushKey: String?,
        b: Boolean,
        b1: Boolean
    ) : this(){
        this.userUid = userId
        this.userName = name
        this.foodName = foodName
        this.foodPrice= foodPrice
        this.foodImage = foodImage
        this.foodQuantity = foodQuantity
        this.address = address
        this.totalPrice = totalPrice
        this.phoneNumber = phone
        this.currentTime = time
        this.itemPushKey = itemPushKey
        this.orderAccepted = orderAccepted
        this.paymentReceived = paymentReceived

    }


   fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeValue(orderAccepted)
        parcel.writeValue(paymentReceived)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

   fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}