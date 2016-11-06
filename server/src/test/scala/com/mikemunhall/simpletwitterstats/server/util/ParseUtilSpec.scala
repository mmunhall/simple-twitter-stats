package com.mikemunhall.simpletwitterstats.server.util

import com.mikemunhall.simpletwitterstats.server.UnitSpec
import org.json4s.native.JsonMethods._
import scala.collection.mutable.ListBuffer
import scala.io.Source

class ParseUtilSpec extends UnitSpec {

  "emoji" should "do all the things" in {
   // implicit val formats = DefaultFormats
    val json = parse(Source.fromURL(getClass.getResource("/emoji.json")).mkString)
    val m = (json \ "unified").values.asInstanceOf[List[String]]
    //val m2 = m.map(e => "\\u" + e.replace("-", "\\u"))
    //val m2 = m.map(e => e.split("-").foldLeft("")((acc, str) => acc + Integer.parseInt(str, 16).toChar))
    //val m2 = m.map(e => Character.toChars(Integer.parseInt(e.split("-")(0), 16)))
    val m2 = m.map(e => {
      val codepoints = e.split("-").map(str => Integer.parseInt(str, 16))
      new String(codepoints, 0, codepoints.length)
    })
    val n = (json \ "name").values.asInstanceOf[List[String]]
    val o = m2 zip n
    val p = o.par.foldLeft(Map[String, String]())((acc, v) => acc + (v._1 -> v._2))

    var status = "@x7qO7lREVo93ojs ね〜(* ˘ ³˘)♡*"
    status = "RT @nurul28okt: @PrillyBie to night ❤ https://t.co/Wo6uXZLgTo✡✡✡"
    //status = "Sesión de mandar fotos horribles con mi bea😂😂❤"
    //status = "RT @JonCozart: Do I stay hungry and work"
    //status = "RT @doispontostil: Algumas noções básicas sobre REDAÇÃO pra galera reclamando que \"é racismo a proposta falar de intolerância religiosa sem…"
    //status = "빌어먹을세상아 잘잇어라\uD83D\uDCA5☺\uD83D\uDD2B(탕"
    status = "Can't way for see shawn Mendes ❤❤"

    val emojis = ListBuffer[String]()
    p.keys.foreach(k => {
      val count = status.count(_ == k.toCharArray()(0))
      for(i <- 1 to count) {
        emojis += p(k)
      }
    })

    val y = 1
  }
}
