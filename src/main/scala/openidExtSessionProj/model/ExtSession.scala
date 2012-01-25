package openidExtSessionProj {
package model {
import net.liftweb._
import mapper._
import util._
import common._

object ExtSession extends ExtSession with MetaProtoExtendedSession[ExtSession] {
  override def dbTableName = "ext_session" // define the DB table name

  def logUserIdIn(uid: String): Unit = User.logUserIdIn(uid)

  def recoverUserId: Box[String] = User.currentUserId

  type UserType = User
}

class ExtSession extends ProtoExtendedSession[ExtSession] {
  def getSingleton = ExtSession // what's the "meta" server
}
}}