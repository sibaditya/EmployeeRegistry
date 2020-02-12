import android.os.Parcel
import android.os.Parcelable

data class Geo (

	val lat : Double,
	val lng : Double
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readDouble(),
		parcel.readDouble()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeDouble(lat)
		parcel.writeDouble(lng)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Geo> {
		override fun createFromParcel(parcel: Parcel): Geo {
			return Geo(parcel)
		}

		override fun newArray(size: Int): Array<Geo?> {
			return arrayOfNulls(size)
		}
	}
}