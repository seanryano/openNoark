class Base {
  String systemID
  static constraints = {
    systemID(nullable: false)
    systemID(unique: true)
  }
  static hasMany = [:]
  static mapping = {
    tablePerHierarchy false
  }
}
