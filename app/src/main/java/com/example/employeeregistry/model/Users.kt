import android.os.Parcel
import android.os.Parcelable

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