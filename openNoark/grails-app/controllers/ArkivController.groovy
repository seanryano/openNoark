import org.apache.shiro.SecurityUtils
import no.friark.ds.*
class ArkivController {
		def commonService
    def index = {redirect(action:list,params:params)}
		
		def create = {
			
		}

	/**
	*Lagrer arkiv basert på params. Feltene arkivstatus, systemtID, opprettetDato og opprettetav settet automatisk til hhv "Opprettet", en UUID, dagens dato og den inloggede brukerens brukernavn.
	*/
	def save = {
			fixParent(params)
			def arkiv = new Arkiv(params)
			arkiv.arkivstatus = "Opprettet"
			commonService.setNewSystemID(arkiv)
			commonService.setCreated(arkiv)
			stripParent(params, arkiv)
			if(arkiv.forelder) arkiv.forelder.addToSubArkiv(arkiv)
			if(!arkiv.hasErrors() && arkiv.validate() && arkiv.save()){
				flash.message = "Arkiv opprettet"
				render(view: "show", model: [arkiv: arkiv])

			} else {
					render(view: "create", model: [errors: arkiv.errors])

			}
	}

	def list = {
		if (!params.sort) params.sort = "tittel"
    if (!params.order) params.order = "asc"
		def arkiver = Arkiv.withCriteria {
			if(params.sort == "forelderTittel"){
				forelder {
					order('tittel', params.order)
				}
			} else {
				order(params.sort, params.order)
			}
			
		}
		[ arkiver: arkiver, arkivTotal: Arkiv.count() ]
	}

	def show = {
		return [arkiv: Arkiv.get(params.id)]
	}

	def update = { UpdateArkivCommand updateCommand ->
		switch(request.method){
			case 'GET':
				return [arkiv: Arkiv.get(params.id)]	
				break
			case 'POST':
				def arkiv = Arkiv.get(params.id)
				if(updateCommand.opprettetdato){
						arkiv.errors.rejectValue "opprettetdato", "USER_ERROR",  "Kan ikke endre dato for opprettelse av arkiv."
						return [errors: arkiv.errors, arkiv: arkiv]
				}
				if(arkiv.avsluttetdato != null && updateCommand.avsluttetdato == null){
						arkiv.errors.rejectValue "avsluttetdato", "USER_ERROR",  "Kan ikke fjerne avsluttetdato."
						return [errors: arkiv.errors, arkiv: arkiv]
				} else if(updateCommand.avsluttetdato == null){
					params.avsluttetdato = null
				}
				stripParent(params, arkiv)
				if(arkiv.arkivstatus != params.arkivstatus && params.arkivstatus == "Avsluttet"){
					params.avsluttetav = SecurityUtils.subject.principal
					params.avsluttetdato = new Date()
				}
				arkiv.properties = params
				if(!arkiv.hasErrors() && arkiv.validate() && arkiv.save()){
					render(view: "show", model: [arkiv: arkiv])
    	  } else {
					render(view: "update", model: [errors: arkiv.errors, arkiv: arkiv])
				}
				break
		}
	}

	def stripParent(params, arkiv) {
		if(!params.forelder || params.forelder == "null") {
        println "nulling parent"
				params.forelder = null
        arkiv.forelder = null
      } else if(params.forelder instanceof String){
        arkiv.forelder = Arkiv.get(Integer.parseInt(params.forelder))
      }
	}
	def fixParent(params){
		if(!params.forelder || params.forelder == "null") {
			params.forelder = null
		} else if(params.forelder instanceof String){
			println params.forelder
			params.forelder = Arkiv.get(Integer.parseInt(params.forelder))
		} else {
			params.forelder.merge()
		}
	}
}

class UpdateArkivCommand {
	Date opprettetdato
	Date avsluttetdato
}
