<%! import no.friark.ds.* %>
<html>
    <head>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1>Arkiver</h1>
					<div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Hjem</a></span>
            <span class="menuButton"><g:link class="create" action="create">Ny Arkivdel</g:link></span>
        </div>

					<table>
						<th>SystemId</th>
						<th>Arkivdel tittel</th>
						<th>Arkivdel status</th>
						<g:each in="${arkivdeler}" var="arkivdel">
							<tr>
								<td>${arkivdel.systemID}</td>
								<td><g:link action="update" id="${arkivdel.id}">${arkivdel.tittel}</g:link></td>
      	        <td>${arkivdel.arkivdelstatus}</td>
        	    </tr>
						</g:each>
					</table>
    </body>
</html>
