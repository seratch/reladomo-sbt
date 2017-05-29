package example.util

import com.gs.fw.common.mithra.{MithraSequence, MithraSequenceObjectFactory}
import example.domain.ObjectSequence
import example.domain.ObjectSequenceFinder

class ObjectSequenceObjectFactory extends MithraSequenceObjectFactory {
  override def getMithraSequenceObject(sequenceName: String, sourceAttribute: scala.Any, initialValue: Int): MithraSequence = {
    var objectSequence = ObjectSequenceFinder.findByPrimaryKey(sequenceName)
    if (objectSequence == null) {
      objectSequence = new ObjectSequence
      objectSequence.setSequenceName(sequenceName)
      objectSequence.setNextId(initialValue)
      objectSequence.insert()
    }
    objectSequence
  }
}
