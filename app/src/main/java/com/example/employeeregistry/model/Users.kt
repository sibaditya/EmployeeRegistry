import android.os.Parcel
import android.os.Parcelable

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Users (

	val id : Int,
	val avatar : Avatar?,
	val name : String?,
	val username : String?,
	val email : String?,
	val address : Address?,
	val phone : String?,
	val website : String?,
	val company : Company?
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readParcelable(Avatar::class.java.classLoader),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(Address::class.java.classLoader),
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(Company::class.java.classLoader)
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeParcelable(avatar, flags)
		parcel.writeString(name)
		parcel.writeString(username)
		parcel.writeString(email)
		parcel.writeParcelable(address, flags)
		parcel.writeString(phone)
		parcel.writeString(website)
		parcel.writeParcelable(company, flags)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Users> {
		override fun createFromParcel(parcel: Parcel): Users {
			return Users(parcel)
		}

		override fun newArray(size: Int): Array<Users?> {
			return arrayOfNulls(size)
		}
	}
}