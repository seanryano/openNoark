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

import grails.converters.*
import no.friark.ds.*

/**
* CRUD for Arkivdeler
*/
class ArkivdelController {
		def commonService
		def arkivdelService

    def index = { redirect(action:list,params:params)}

		def create = {
			[arkiver: Arkiv.list()]
		}


		/**
		* Lager en ny arkivdel. Denne funksjonen setter automatisk verdiene dor systemID, arkivdelstatus og opprettetav
		*/
		def save = {
			def arkivdel = new Arkivdel(params)
			arkivdel.arkivdelstatus = "Opprettet"
			commonService.setNewSystemID(arkivdel)
			commonService.setCreated(arkivdel)
			if(!params.referanseforelder || params.referanseforelder == "null") {
				arkivdel.referanseforelder = null
			} else {
				arkivdel.referanseforelder = Arkiv.get(params.referanseforelder)
			}
			if(!arkivdel.save()){
				render(view: "create", model: [errors: arkivdel.errors])
			} else {
				render(view: "show", model: [arkivdel: arkivdel])
			}
		}
	
		def show = {
			return [arkivdel: Arkivdel.get(params.id)]
		}
		
		def list = {
			if (!params.sort) params.sort = "tittel"
	    if (!params.order) params.order = "asc"

			def arkivdeler = Arkivdel.withCriteria {
  	    if(params.sort == "forelder"){
    	    referanseforelder {
        	  order('tittel', params.order)
      	  }
		    } else {
  	      order(params.sort, params.order)
				}
			}      

			withFormat {
	      html {
  	      return [arkivdeler: arkivdeler]
        }
        xml {
    	    render Arkivdel.findAll() as XML
        }
        json {
       		 println Arkivdel.findAll() as JSON
           render Arkivdel.findAll() as JSON
        }
     }

			return [arkivdeler: arkivdeler]
		}

	def edit = {
		render(view: 'update', model: [arkivdel: Arkivdel.get(params.id)])
	}

	def update = { UpdateArkivdelCommand updateCommand ->

		switch(request.method){
      case 'GET':
        return [arkivdel: Arkivdel.get(params.id)]
        break
      case 'POST':
				def arkivdel = Arkivdel.get(params.id)
				if(updateCommand.avsluttetdato == null){
          params.avsluttetdato = null
        }
				if(updateCommand.arkivperiodestartdato == null) params.arkivperiodestartdato = null
        if(updateCommand.arkivperiodesluttdato == null) params.arkivperiodesluttdato = null
				if(updateCommand.opprettetdato != null && updateCommand.opprettetdato == arkivdel.opprettetdato){
					params.opprettetdato = null
				}
				arkivdel.properties = params
        if(!arkivdel.hasErrors() && arkivdel.validate() && arkivdel.save()){
          render(view: "show", model: [arkivdel: arkivdel])
        } else {
          render(view: "update", model: [errors: arkivdel.errors, arkivdel: arkivdel])
        }
        break
    }

	}
	
	def håndterOppbevaringsted = {
		return [arkivdel: Arkivdel.get(params.id)]
	}

	def fjernOppbevaringsted = {
		def arkivdel = Arkivdel.get(params.arkivdelid)
		arkivdel.oppbevaringssted.remove  params.sted
		if(arkivdel.save()){
			println arkivdel.errors
			render view: 'håndterOppbevaringsted', model: [arkivdel: arkivdel]
		}else{
			render view: 'håndterOppbevaringsted', model: [arkivdel: arkivdel, errors: arkivdel.errors]
			println arkivdel.errors
		}
	}

	def leggTilOppbevaringsted = {
		def arkivdel = Arkivdel.get(params.arkivdelid)
    arkivdel.oppbevaringssted.add params.sted
		if(arkivdel.save()){
      println arkivdel.errors
      render view: 'håndterOppbevaringsted', model: [arkivdel: arkivdel]
    }else{
      render view: 'håndterOppbevaringsted', model: [arkivdel: arkivdel, errors: arkivdel.errors]
      println arkivdel.errors
    }


  }

}
class UpdateArkivdelCommand {
  Date opprettetdato
  Date avsluttetdato
	Date arkivperiodestartdato
	Date arkivperiodesluttdato
}