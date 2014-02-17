package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import service.{TestWorker, Master}
import akka.actor.Props
import play.api.Play.current

object Application extends Controller {

  val m = Akka.system.actorOf(Props[Master], "master")
  val w1 = Akka.system.actorOf(Props(new TestWorker(m.path)))
  val w2 = Akka.system.actorOf(Props(new TestWorker(m.path)))
  val w3 = Akka.system.actorOf(Props(new TestWorker(m.path)))
  
  def index = Action {
    s.foreach( str =>
      m ! str
    )
    Ok(views.html.index("Your new application is ready."))
  }
  
  val s =
    """
      |Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a libero at nulla iaculis mattis. Phasellus auctor sem at orci molestie auctor. Donec rhoncus dolor in tortor hendrerit, pretium dignissim enim suscipit. Proin scelerisque arcu quis porttitor hendrerit. Maecenas scelerisque tellus justo. Aenean lobortis odio vitae vestibulum malesuada. Suspendisse potenti. Quisque ut turpis ac elit sollicitudin ultrices ut eleifend metus. Nulla rhoncus ipsum lectus, a dapibus ligula consectetur quis. Nunc auctor tincidunt nisi in semper. Vivamus at risus eget odio placerat suscipit.
      |
      |Vivamus id accumsan elit. In non mollis lorem. Nunc vitae molestie urna. Nunc eu justo in tellus gravida aliquam. Donec dignissim velit nibh, a laoreet eros aliquam vel. Vestibulum ullamcorper libero nec convallis tristique. Suspendisse eu neque et nisl vehicula imperdiet sed a sem. Sed non erat id ante pretium porttitor. Nullam viverra lacus venenatis, vestibulum elit id, vulputate urna. Nullam vitae commodo lorem. Vivamus aliquam augue tellus, eget accumsan urna dictum sit amet. Phasellus eget aliquet lectus, et ullamcorper nibh. Curabitur venenatis pharetra metus. Sed cursus a ante quis condimentum. Aenean lobortis fermentum nulla, nec sodales tortor placerat sit amet.
      |
      |Aenean urna orci, consequat at nibh sit amet, condimentum ornare leo. Vivamus faucibus ipsum at varius tristique. Suspendisse sit amet euismod odio. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nullam ac massa at nisi viverra hendrerit at vitae diam. Donec auctor ut felis et feugiat. Suspendisse vel ullamcorper metus, id aliquam dui. Quisque vitae metus nec turpis fermentum condimentum sit amet a nunc. Aliquam dictum, elit in ultricies volutpat, mauris nibh fermentum lectus, ornare rutrum risus ipsum at tellus. In iaculis nibh non velit porttitor euismod. Nulla lobortis, massa eget fermentum cursus, augue nulla dapibus dui, quis mollis erat erat nec nisl. Curabitur mollis rutrum ullamcorper.
      |
      |Aliquam ut malesuada erat. Sed ullamcorper nisl a augue accumsan imperdiet. Nulla condimentum neque commodo gravida posuere. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum eleifend vestibulum sem non elementum. Sed purus ipsum, lacinia sit amet volutpat quis, pulvinar vel metus. Etiam feugiat ipsum lorem, non tempus nulla volutpat ac. Duis accumsan sagittis dolor a euismod. Nunc sit amet metus et mauris rhoncus posuere sit amet eu nisl. Nunc consequat ante augue, eget dignissim enim eleifend ac. Donec auctor aliquam est id tincidunt. Donec tincidunt dui id pharetra bibendum.
      |
      |Mauris nisi sem, rutrum vitae feugiat vitae, mattis non nibh. Aliquam et turpis eu tortor ornare sagittis. Pellentesque elementum adipiscing lectus et volutpat. Donec commodo ipsum et tincidunt faucibus. Ut nec turpis facilisis, tempor eros vitae, cursus arcu. Phasellus vestibulum bibendum enim, sed egestas ante sodales sed. Integer vestibulum vitae dolor id condimentum. Vivamus accumsan laoreet tellus non tempor. Etiam gravida urna sit amet est sollicitudin, a consectetur massa vulputate. In gravida dapibus magna, id luctus metus porta vitae. Nam gravida odio dui, eu fermentum arcu congue non. Duis egestas congue risus id tincidunt.
    """.stripMargin.split(" ")

}
