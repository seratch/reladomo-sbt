package example

import com.gs.fw.common.mithra.MithraManagerProvider
import example.domain.Person

object Hello extends Greeting with App {
  loadReladomoConfigurationXml("reladomo/config/ReladomoRuntimeConfig.xml")
  println(greeting)

  Person.createPerson("Taro", "Tanaka", "JPN")
  val personFromDb = Person.findPersonNamed("Taro", "Tanaka")

  println("Hello " + personFromDb.getFullName)


  def loadReladomoConfigurationXml(reladomoConfigurationXml: String) = {
    val inputStream = classOf[Greeting].getClassLoader.getResourceAsStream(reladomoConfigurationXml)
    MithraManagerProvider.getMithraManager.readConfiguration(inputStream)
    inputStream.close()
  }
}

trait Greeting {
  lazy val greeting: String = "hello"
}
