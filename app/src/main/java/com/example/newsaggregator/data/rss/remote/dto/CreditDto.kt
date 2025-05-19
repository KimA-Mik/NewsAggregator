package com.example.newsaggregator.data.rss.remote.dto

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("credit", "http://search.yahoo.com/mrss/", "media")
data class CreditDto(
    val scheme: String?,

    @XmlValue
    val value: String
)