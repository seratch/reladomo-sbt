package example.util

import com.gs.fw.common.mithra.MithraSequenceObjectFactory
import example.domain.ObjectSequence
import example.domain.ObjectSequenceFinder

class ObjectSequenceObjectFactory extends MithraSequenceObjectFactory {
  def getMithraSequenceObject(sequenceName: Nothing, sourceAttribute: Nothing, initialValue: Int): Nothing = {
    var objectSequence = ObjectSequenceFinder.findByPrimaryKey(sequenceName)
    if (objectSequence == null) {
      objectSequence = new ObjectSequence
      objectSequence.setSequenceName(sequenceName)
      objectSequence.setNextId(initialValue)
      objectSequence.insert
    }
    objectSequence
  }
}
