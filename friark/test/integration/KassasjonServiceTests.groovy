/*
    This file is part of Friark.

    Friark is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Friark is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Friark.  If not, see <http://www.gnu.org/licenses/>.
*/

import grails.test.*
import no.friark.ds.*
import no.friark.ds.Arkiv

class KassasjonServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
				loginTestUser()
    }

    protected void tearDown() {
        super.tearDown()
    }

	
    void testOversiktIngenKassason() {
			def ark, del, reg = createStructure()
			
			KassasjonService service = new KassasjonService()
			def list = service.oversikt([fra: new Date(), til: new Date(), kassasjonsvedtak: "Bevares"])
			assertEquals 0, list?.size()
    }

		void testOversikt(){
			def ark, del, reg = createStructure()
			Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")

			if(!desc.save()){
        println desc.errors
        fail "unable to save desc"
      }

			
			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Bevares", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
			if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }

			desc.bevaringOgKassasjon = bev
			desc.save()
			
			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Bevares"]

			KassasjonService service = new KassasjonService()

			def list = service.oversikt(co)
			assertEquals 1, list?.size()

			def dok = list[0]
			assertTrue dok instanceof Dokumentbeskrivelse
		}

		void testOversikt2i1(){
			def ark, del, reg = createStructure()
			Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")

			if(!desc.save()){
        println desc.errors
        fail "unable to save desc"
      }

			Dokumentbeskrivelse desc2 = new Dokumentbeskrivelse(systemID: "21412", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")
      
      if(!desc2.save()){
        println desc2.errors
        fail "unable to save desc"
      }

			
			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Bevares", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc, desc2])
			if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }

			desc.bevaringOgKassasjon = bev
			desc.save()
			
			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Bevares"]

			KassasjonService service = new KassasjonService()

			def list = service.oversikt(co)
			assertEquals 2, list?.size()

			def dok = list[0]
			assertTrue dok instanceof Dokumentbeskrivelse
		}

		void testOversikt2i2(){
			def ark, del, reg = createStructure()
			Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")

			if(!desc.save()){
        println desc.errors
        fail "unable to save desc"
      }

			Dokumentbeskrivelse desc2 = new Dokumentbeskrivelse(systemID: "21412", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")
      
      if(!desc2.save()){
        println desc2.errors
        fail "unable to save desc"
      }

			
			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Bevares", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
			if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }

			BevaringOgKassasjon bev2 = new BevaringOgKassasjon(kassasjonsvedtak: "Bevares", bevaringstid: 3, kassasjonsdato: new Date() + 2, dokumentBeskrivelse: [desc])
      if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }		

	
			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Bevares"]

			KassasjonService service = new KassasjonService()

			def list = service.oversikt(co)
			assertEquals 1, list?.size()

			def dok = list[0]
			assertTrue dok instanceof Dokumentbeskrivelse
		}


		void testKasser() {
			//subject.metaClass.'isPermitted' = {true}
			def (ark, del, reg) = createStructure()
			
      Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")
			saveOrFail desc
			
			Dokumentobjekt obj = new Dokumentobjekt(systemID: "AASA", versjonsnummer:"1", variantformat:"1", format:"1", formatdetaljer:"2", opprettetdato: new Date(), opprettetav:"dall", referansedokumentBeskrivelse:desc )
			saveOrFail(obj)
			
			desc.addToReferansedokumentObjekt(obj)

			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Kasseres", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
      if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }

			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Kasseres"]

      KassasjonService service = new KassasjonService()
			service.archiveService = new ArchiveService()
      def list = service.oversikt(co)
      assertEquals 1, list?.size()
			
			assertEquals 1, Dokumentobjekt.list().size()
			println "kasserer ${desc}"
			//org.apache.shiro.SecurityUtils.metaClass.'static'.getSubject = { return [principal : "testuser"] }
			service.kasser(desc)

			assertEquals 0, Dokumentobjekt.list().size()
		}

		/**
		*5.10.35 
		* For hvert dokument som blir kassert, skal det på dokumentbeskrivelsesnivå logges dato for kassasjon og hvem som utførte kassasjonen.
		*/
		void testKasserLogging() {
			//subject.metaClass.'isPermitted' = {true}
			def (ark, del, reg) = createStructure()
			
      Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")
			saveOrFail desc
			
			Dokumentobjekt obj = new Dokumentobjekt(systemID: "AASA", versjonsnummer:"1", variantformat:"1", format:"1", formatdetaljer:"2", opprettetdato: new Date(), opprettetav:"dall", referansedokumentBeskrivelse:desc )
			saveOrFail(obj)
			
			desc.addToReferansedokumentObjekt(obj)

			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Kasseres", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
      if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }

			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Kasseres"]

      KassasjonService service = new KassasjonService()
			service.archiveService = new ArchiveService()
      def list = service.oversikt(co)
      assertEquals 1, list?.size()
			
			assertEquals 1, Dokumentobjekt.list().size()
			service.kasser(desc)
			assertEquals 0, Dokumentobjekt.list().size()

			//reload for good messure
			desc = Dokumentbeskrivelse.get(desc.id)
			assertNotNull desc.kassertDato
			assertEquals "testuser", desc.kassertAv
		}

		void testKasserTilMappe() {
			//subject.metaClass.'isPermitted' = {true}
			def (ark, del, reg) = createStructure()
			
      Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")
			saveOrFail desc
			
			Dokumentobjekt obj = new Dokumentobjekt(systemID: "AASA", versjonsnummer:"1", variantformat:"1", format:"1", formatdetaljer:"2", opprettetdato: new Date(), opprettetav:"dall", referansedokumentBeskrivelse:desc )
			saveOrFail(obj)
			
			Dokumentlink dl = new Dokumentlink(dokumentnummer:"2",tilknyttetav:"3", tilknyttetdato: new Date(), tilknyttetregistreringSom: "dsfs", referanseregistrering: reg, dokumentbeskrivelse: desc)
			saveOrFail(dl)
			desc.addToRegistreringer(dl)
			desc.addToReferansedokumentObjekt(obj)
			desc.save()

			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Kasseres", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
      if(!bev.save()){
        println bev.errors
        fail "unable to save bev"
      }

			desc.bevaringOgKassasjon = bev
			desc.save()
	
			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Kasseres"]

      KassasjonService service = new KassasjonService()
			service.archiveService = new ArchiveService()
      def list = service.oversikt(co)
      assertEquals 1, list?.size()

			
		
			assertEquals 1, ForenkletRegistrering.list().size()
			assertEquals 1, Dokumentbeskrivelse.list().size()
			assertEquals 1, Dokumentobjekt.list().size()
			
			service.kasser(desc, true)
			
			assertEquals 0, Dokumentobjekt.list().size()
			assertEquals 0, Dokumentbeskrivelse.list().size()
			assertEquals 0, ForenkletRegistrering.list().size()
		}

		void testdill() {
			Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")
      saveOrFail desc

      Dokumentobjekt obj = new Dokumentobjekt(systemID: "AASA", versjonsnummer:"1", variantformat:"1", format:"1", formatdetaljer:"2", opprettetdato: new Date(), opprettetav:"dall", referansedokumentBeskrivelse:desc )
      saveOrFail(obj)

      desc.addToReferansedokumentObjekt(obj)
			desc.save()			

			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Kasseres", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
			saveOrFail(bev)
			bev.removeFromDokumentBeskrivelse(desc)
			obj.delete()
			desc.delete()


			Dokumentobjekt.list()
		}

		void testKasserUnauthorized() {
			loginTestUser({false})
			def (ark, del, reg) = createStructure()
      Dokumentbeskrivelse desc = new Dokumentbeskrivelse(systemID: "2141", dokumenttype: "type", dokumentstatus: "status", tittel: "tittel", beskrivelse:"desc", forfatter: "forfatter", opprettetdato: new Date(), opprettetav: "dill", dokumentmedium: "pysical/papyrus", oppbevaringssted: "her og der")

      if(!desc.save()){
        println desc.errors
        fail "unable to save desc"
      }
			
			Dokumentobjekt obj = new Dokumentobjekt(systemID: "AASA", versjonsnummer:"1", variantformat:"1", format:"1", formatdetaljer:"2", opprettetdato: new Date(), opprettetav:"dall", referansedokumentBeskrivelse:desc )
			saveOrFail(obj)
			
			desc.addToReferansedokumentObjekt(obj)

			BevaringOgKassasjon bev = new BevaringOgKassasjon(kassasjonsvedtak: "Kasseres", bevaringstid: 1, kassasjonsdato: new Date(), dokumentBeskrivelse: [desc])
			saveOrFail(bev)

			def co = [fra: new Date() - 2, til: new Date() + 2, kassasjonsvedtak: "Kasseres"]
			
      KassasjonService service = new KassasjonService()
			service.archiveService = new ArchiveService()
      def list = service.oversikt(co)
      assertEquals 1, list?.size()
			
			assertEquals 1, Dokumentobjekt.list().size()
			println "kasserer ${desc}"
			//org.apache.shiro.SecurityUtils.metaClass.'static'.getSubject = { return [principal : "testuser"] }
			try{
				service.kasser(desc)
				fail "kassering var vellykket når den skulle exceptet"
			} catch(Exception ex){
				//all is well
			}

			assertEquals 1, Dokumentobjekt.list().size()
		}




		void testFilter() {
			
			KassasjonService service = new KassasjonService()

			
			Basismappe m1 = new Basismappe(id: 1, systemID: "1")
			def reg1 = new ForenkletRegistrering(id:5, referanseforelderBasismappe: m1)
			def dok1 = new Dokumentbeskrivelse(registreringer: [new Dokumentlink(referanseregistrering: reg1)])
			assertEquals "1", dok1.registreringer.toArray()[0].referanseregistrering.referanseforelderBasismappe.systemID

			Basismappe m2 = new Basismappe(id: 2, systemID: "2")
			def reg2 = new ForenkletRegistrering(id:5, referanseforelderBasismappe: m2)
			def dok2 = new Dokumentbeskrivelse(registreringer: [new Dokumentlink(referanseregistrering: reg2)])
			assertEquals "2", dok2.registreringer.toArray()[0].referanseregistrering.referanseforelderBasismappe.systemID

			def list = [dok1, dok2]

			def retval = service.filter(list, "mappe(systemID: \"1\")")
			
			assertEquals 1, retval.size()

			assertEquals "1", retval.get(0).registreringer.toArray()[0].referanseregistrering.referanseforelderBasismappe.systemID

			retval = service.filter(list, "mappe(systemID: \"2\")")

      assertEquals 1, retval.size()

      assertEquals "2", retval.get(0).registreringer.toArray()[0].referanseregistrering.referanseforelderBasismappe.systemID
		}

		def createStructure(){
			Arkiv ark = new Arkiv(systemID: "1", tittel: "tittel", arkivstatus: "Opprettet", opprettetdato: new Date(), opprettetav: "meg")
			saveOrFail(ark)
      
			assertNotNull Arkiv.get(ark.id)

      Arkivdel del = new Arkivdel(systemID: "2", tittel: "tittel", arkivdelstatus: "Opprettet", dokumentmedium: "text/html", opprettetav:"deg", opprettetdato: new Date(), referanseforelder: ark )
			saveOrFail(del)

      ForenkletRegistrering reg = new ForenkletRegistrering(systemID: "3", opprettetdato: new Date(), opprettetav: "dill", arkivertdato: new Date(), arkivertav: "dall", referansearkivdel: del, registreringstype: "type")
			saveOrFail(reg)
			
			return [ark, del, reg]
		}




}