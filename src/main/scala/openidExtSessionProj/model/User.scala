package openidExtSessionProj {
package model {

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.common._
import net.liftweb.openid._

//
///**
// * The singleton that has methods for accessing the database
// */
//object User extends User with MetaMegaProtoUser[User] {
//  override def dbTableName = "users" // define the DB table name
//  override def screenWrap = Full(<lift:surround with="default" at="content">
//			       <lift:bind /></lift:surround>)
//  // define the order fields will appear in forms and output
//  override def fieldOrder = List(id, firstName, lastName, email,
//  locale, timezone, password, textArea)
//
//  onLogIn = List(ExtSession.userDidLogin(_))
//  onLogOut = List(ExtSession.userDidLogout(_))
//
//  // comment this line out to require email validations
//  override def skipEmailValidation = true
//}
//
///**
// * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
// */
//class User extends MegaProtoUser[User] {
//  def getSingleton = User // what's the "meta" server
//
//  // define an additional field for a personal essay
//  object textArea extends MappedTextarea(this, 2048) {
//    override def textareaRows  = 10
//    override def textareaCols = 50
//    override def displayName = "Personal Essay"
//  }
//}


import _root_.org.openid4java.discovery.DiscoveryInformation
import _root_.org.openid4java.message.AuthRequest

object MyVendor extends SimpleOpenIDVendor {
  def ext(di: DiscoveryInformation, authReq: AuthRequest): Unit = {
    import WellKnownAttributes._
    WellKnownEndpoints.findEndpoint(di) map {
      ep =>
        ep.makeAttributeExtension(List(Email, FullName, FirstName, LastName)) foreach {
          ex =>
            authReq.addExtension(ex)
        }
    }
  }

  override def createAConsumer = new OpenIDConsumer[UserType] {
    beforeAuth = Full(ext _)
  }
}

object User extends User with MetaOpenIDProtoUser[User] with LongKeyedMetaMapper[User] {
  def openIDVendor = MyVendor

  override def screenWrap = Full(<lift:surround with="default" at="content">
      <lift:bind/>
  </lift:surround>)

  override def dbTableName = "users"

 // override def homePage = if (loggedIn_?) "/" else "/"

  onLogIn = List(ExtSession.userDidLogin(_))
  onLogOut = List(ExtSession.userDidLogout(_))

}

class User extends LongKeyedMapper[User] with OpenIDProtoUser[User] {
  def getSingleton = User
}

}

}
