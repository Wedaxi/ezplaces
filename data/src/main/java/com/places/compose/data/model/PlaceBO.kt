package com.places.compose.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place

@Entity(tableName = "place")
data class PlaceBO(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String? = null,
    @ColumnInfo(name = "coordinates") val coordinates: CoordinatesBO? = null,
    @ColumnInfo(name = "icon") val icon: String? = null,
    @ColumnInfo(name = "iconBackground") val iconBackground: Int? = null,
    @ColumnInfo(name = "phone") val phone: String? = null,
    @Ignore val rating: Double? = null,
    @Ignore val numRatings: Int = 0,
    @Ignore val isOpen: Boolean? = null,
    @ColumnInfo(name = "webSite") val webSite: Uri? = null,
    @Ignore val plusCode: String? = null,
    @Ignore val schedule: List<String> = emptyList(),
    @Ignore internal val types: List<Place.Type> = emptyList(),
    @Ignore internal val photoMetadata: List<PhotoMetadata> = emptyList()
) {

    val hasPhotoMetadata: Boolean
        get() = photoMetadata.isNotEmpty()

    internal constructor(place: Place): this(
        id = place.id.orEmpty(),
        name = place.name.orEmpty(),
        address = place.address,
        coordinates = place.latLng?.let { CoordinatesBO(it) },
        icon = place.iconUrl,
        iconBackground = place.iconBackgroundColor,
        phone = place.phoneNumber,
        rating = place.rating,
        numRatings = place.userRatingsTotal ?: 0,
        isOpen = place.isOpen,
        webSite = place.websiteUri,
        plusCode = place.plusCode?.globalCode,
        schedule = place.openingHours?.weekdayText.orEmpty(),
        types = place.types.orEmpty(),
        photoMetadata = place.photoMetadatas.orEmpty()
    )

    internal constructor(prediction: AutocompletePrediction): this(
        id = prediction.placeId,
        name = prediction.getFullText(null).toString(),
        types = prediction.placeTypes
    )

    internal constructor(
        id: String,
        name: String,
        coordinates: CoordinatesBO? = null,
        address: String? = null,
        icon: String? = null,
        iconBackground: Int? = null,
        phone: String? = null,
        webSite: Uri? = null
    ): this(
        id = id,
        name = name,
        coordinates = coordinates,
        address = address,
        icon = icon,
        iconBackground = iconBackground,
        phone = phone,
        webSite = webSite,
        photoMetadata = emptyList()
    )
}