/**
Metadata for klasse¤¤¤¤¤
 ¤¤¤¤¤

*/
class Klasse extends Base{
  String klasseid
  String tittel
  String beskrivelse
  Date opprettetdato
  String opprettetav
  Date avsluttetdato
  String avsluttetav
  Klassifikasjonssystem referanseforelderKlassifikasjonssystem
  Klasse referanseforelderKlasse
  static constraints = {
    klasseid(nullable: false)
    klasseid(unique: false)
    tittel(nullable: false)
    tittel(unique: false)
    beskrivelse(nullable: true)
    beskrivelse(unique: false)
    nøkkelord(nullable: true)
    nøkkelord(unique: false)
    opprettetdato(nullable: false)
    opprettetdato(unique: false)
    opprettetav(nullable: false)
    opprettetav(unique: false)
    avsluttetdato(nullable: true)
    avsluttetdato(unique: false)
    avsluttetav(nullable: true)
    avsluttetav(unique: false)
    referanseforelderKlassifikasjonssystem(nullable: false)
    referanseforelderKlassifikasjonssystem(unique: false)
    referanseforelderKlasse(nullable: true)
    referanseforelderKlasse(unique: false)
    referansebarnKlasse(minSize: 1)
    referansebarnKlasse(unique: false)
    referansebarnBasismappe(minSize: 1)
    referansebarnBasismappe(unique: false)
    referansebarnForenkletRegistrering(minSize: 1)
    referansebarnForenkletRegistrering(unique: false)
  }
  static hasMany = [nøkkelord:String, referansebarnKlasse:Klasse, referansebarnBasismappe:Basismappe, referansebarnForenkletRegistrering:ForenkletRegistrering]
  static mapping = {
  }
  String fullId
  static auditable = true
  static transients = ["fullId"]
  def afterLoad = {
    println "klasseid: ${owner.klasseid}"
    if(referanseforelderKlasse){
    	fullId = "${referanseforelderKlasse.klasseid}.${klasseid}"
    }else{
    	fullId = "${klasseid}"
    }
    println "fullId: ${fullId}"
  }
  String toString(){"Klasse : ${fullId}"}
}
