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


import no.friark.ds.*
import grails.converters.* 
/**
* CRUD-operasjoner for Kalssifikasjonsystem
*
* @author Kent Inge Fagerland Simonsen
*/
class ClassificationSystemController {
 		def commonService   
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
   // static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
       params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
			withFormat{
	        html { [ klassifikasjonssystemInstanceList: ClassificationSystem.list( params ), klassifikasjonssystemInstanceTotal: ClassificationSystem.count() ] }
					xml{ render ClassificationSystem.list() as XML }

			}
   }	

    def show = {
        def klassifikasjonssystemInstance = ClassificationSystem.get( params.id )

       if(!klassifikasjonssystemInstance) {
            flash.message = "ClassificationSystem not found with id ${params.id}"
            redirect(action:list)
       }
       else { 
				withFormat{
					html { return [ klassifikasjonssystemInstance : klassifikasjonssystemInstance ] }
					xml { render klassifikasjonssystemInstance as XML  }
				}
			}
   }

    def delete = {
        def klassifikasjonssystemInstance = ClassificationSystem.get( params.id )
        if(klassifikasjonssystemInstance) {
            try {
                klassifikasjonssystemInstance.delete(flush:true)
                flash.message = "ClassificationSystem ${params.id} deleted"
                redirect(action:list)
           }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "ClassificationSystem ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
           }
       }
        else {
            flash.message = "ClassificationSystem not found with id ${params.id}"
            redirect(action:list)
       }
   }

    def edit = {
        def klassifikasjonssystemInstance = ClassificationSystem.get( params.id )

        if(!klassifikasjonssystemInstance) {
            flash.message = "ClassificationSystem not found with id ${params.id}"
            redirect(action:list)
       }
        else {
            return [ klassifikasjonssystemInstance : klassifikasjonssystemInstance ]
       }
   }

    def update = {
			if(request.method == 'PUT'){
				def klassifikasjonssystemInstance = ClassificationSystem.get( params.classificationSystem.id )
				params.classificationSystem.createdDate =  klassifikasjonssystemInstance.createdDate//can not change created date
				klassifikasjonssystemInstance.properties = params.classificationSystem
        if(!klassifikasjonssystemInstance.hasErrors() && klassifikasjonssystemInstance.save()) {
					render klassifikasjonssystemInstance as XML
        } else {
					render text:"<errors>${klassifikasjonssystemInstance.errors}</errors>", contentType:"text/xml",encoding:"UTF-8"
        }

			}
			else {
        def klassifikasjonssystemInstance = ClassificationSystem.get( params.id )
        if(klassifikasjonssystemInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(klassifikasjonssystemInstance.version > version) {
                    
                    klassifikasjonssystemInstance.errors.rejectValue("version", "klassifikasjonssystem.optimistic.locking.failure", "Another user has updated this ClassificationSystem while you were editing.")
                    render(view:'edit',model:[klassifikasjonssystemInstance:klassifikasjonssystemInstance])
                    return
               }
           }
            klassifikasjonssystemInstance.properties = params
            if(!klassifikasjonssystemInstance.hasErrors() && klassifikasjonssystemInstance.save()) {
                flash.message = "ClassificationSystem ${params.id} updated"
                redirect(action:show,id:klassifikasjonssystemInstance.id)
           }
            else {
                render(view:'edit',model:[klassifikasjonssystemInstance:klassifikasjonssystemInstance])
           }
       }
        else {
            flash.message = "ClassificationSystem not found with id ${params.id}"
            redirect(action:list)
       }
		}
   }

    def create = {
        def klassifikasjonssystemInstance = new ClassificationSystem()
        klassifikasjonssystemInstance.properties = params
        return ['klassifikasjonssystemInstance':klassifikasjonssystemInstance]
   }

    def save = {
        def klassifikasjonssystemInstance
				if(params.classificationSystem) klassifikasjonssystemInstance = new ClassificationSystem(params.classificationSystem)
				else klassifikasjonssystemInstance = new ClassificationSystem(params)
				commonService.setCreated(klassifikasjonssystemInstance)
				commonService.setNewSystemID(klassifikasjonssystemInstance)
        if(!klassifikasjonssystemInstance.hasErrors() && klassifikasjonssystemInstance.save()) {
            flash.message = "ClassificationSystem ${klassifikasjonssystemInstance.id} created"
						withFormat{
		            html { redirect(action:show,id:klassifikasjonssystemInstance.id) }
								xml { render klassifikasjonssystemInstance as XML }
						}
       }
        else {
					withFormat{
            html { render(view:'create',model:[klassifikasjonssystemInstance:klassifikasjonssystemInstance]) }
						xml { render text:"<errors>${klassifikasjonssystemInstance.errors}</errors>", contentType:"text/xml",encoding:"UTF-8"}
					}
       }
   }
}
