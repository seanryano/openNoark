

class KlasseController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ klasseInstanceList: Klasse.list( params ), klasseInstanceTotal: Klasse.count() ]
    }

    def show = {
        def klasseInstance = Klasse.get( params.id )

        if(!klasseInstance) {
            flash.message = "Klasse not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ klasseInstance : klasseInstance ] }
    }

    def delete = {
        def klasseInstance = Klasse.get( params.id )
        if(klasseInstance) {
            try {
                klasseInstance.delete(flush:true)
                flash.message = "Klasse ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Klasse ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Klasse not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def klasseInstance = Klasse.get( params.id )

        if(!klasseInstance) {
            flash.message = "Klasse not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ klasseInstance : klasseInstance ]
        }
    }

    def update = {
        def klasseInstance = Klasse.get( params.id )
        if(klasseInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(klasseInstance.version > version) {
                    
                    klasseInstance.errors.rejectValue("version", "klasse.optimistic.locking.failure", "Another user has updated this Klasse while you were editing.")
                    render(view:'edit',model:[klasseInstance:klasseInstance])
                    return
                }
            }
            klasseInstance.properties = params
            if(!klasseInstance.hasErrors() && klasseInstance.save()) {
                flash.message = "Klasse ${params.id} updated"
                redirect(action:show,id:klasseInstance.id)
            }
            else {
                render(view:'edit',model:[klasseInstance:klasseInstance])
            }
        }
        else {
            flash.message = "Klasse not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def klasseInstance = new Klasse()
        klasseInstance.properties = params
        return ['klasseInstance':klasseInstance]
    }

    def save = {
        def klasseInstance = new Klasse(params)
        if(!klasseInstance.hasErrors() && klasseInstance.save()) {
            flash.message = "Klasse ${klasseInstance.id} created"
            redirect(action:show,id:klasseInstance.id)
        }
        else {
            render(view:'create',model:[klasseInstance:klasseInstance])
        }
    }
}