package controllers

import java.util.Locale
import org.ocpsoft.prettytime.PrettyTime

/**
 * Created by sumnulu on 23/03/15.
 */
object Utils {
  private val urlRegex = """(?i)\b(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]*[-A-Za-z0-9+&@#/%=~_|]""".r


  //  lazy val prettyTime =  new PrettyTime(new Locale("tr"))
  lazy val prettyTime = new PrettyTime

  object markdown {

    import com.github.rjeschke.txtmark.{Configuration, Processor}

    private lazy val conf = Configuration.builder().setSafeMode(true).build()

    def parse(txt: String) = Processor.process(txt, conf)
  }

  def parseComment(s: String) = {
    var p = s
    p = linkify(p)
    p = markdown.parse(p)
    removeH1(p)
  }

  def linkify(s: String) = urlRegex replaceAllIn(s, m => """<a href="%s" target="aaa">%s</a>""" format(m.matched, m.matched))

  def removeH1(s: String) = s.replaceAll("<h1>", "#").replaceAll("</h1>", "")

  def renderName(s: String) = {
    val spliced = s.split(" ").toList
    val surnamePart = List(spliced.lastOption.map(_ head).getOrElse("") + ".")
    val headPart = spliced.dropRight(1)
    (headPart ::: surnamePart).mkString(" ")
  }


  def lastNDayFromNow(day:Int) = {
    val now = System.currentTimeMillis
    val deltaDate = 1000 * 60 * 60 * 24 * day
    now - deltaDate
  }

}
