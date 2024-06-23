package com.example.mp3project.model.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "item", strict = false)
data class RSS @JvmOverloads constructor(

  @field:Element(name = "title")
  @param:Element(name = "title")
  var title: String,

  @field:Element(name = "description")
  @param:Element(name = "description")
  var description: String,

  @field:Element(name = "link")
  @param:Element(name = "link")
  var link: String,

  @field:Element(name = "pubDate")
  @param:Element(name = "pubDate")
  var pubDate: String,

  @field:ElementList(name = "enclosure", inline = true, required = false)
  @param:ElementList(name = "enclosure", inline = true, required = false)
  var enclosure: List<Enclosure> = listOf()


)

