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

/**
* Kontroller for å håndtere nøkkelord på vilkårlig (Base) objekter.
*
* @author Kent Inge F. Simonsen
*/
class KeywordController {

    def index = { }

		def edit = {
			[base: Base.findBySystemID(params.id)]	
		}

		def update = {
			def base = Base.findBySystemID(params.systemID)
			base.nøkkelord << params.new
			base.save()
			render(view: "edit", model: [base: base])
		}

		def delete = {
			def base = Base.findBySystemID(params.systemID)
			base.nøkkelord -= params.ord
			base.save()
			render(view: "edit", model: [base: base])
		}
}