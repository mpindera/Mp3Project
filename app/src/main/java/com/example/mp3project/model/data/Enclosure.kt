package com.example.mp3project.model.data

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "enclosure", strict = false)
data class Enclosure @JvmOverloads constructor(
  @field:Attribute(name = "url")
  @param:Attribute(name = "url")
  var url: String
)