<%! import no.friark.ds.* %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Dokumentlink</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Dokumentlink List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Dokumentlink</g:link></span>
        </div>
        <div class="body">
            <h1>Show Dokumentlink</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:dokumentlinkInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Referanseregistrering:</td>
                            
                            <td valign="top" class="value"><g:link controller="forenkletRegistrering" action="show" id="${dokumentlinkInstance?.referanseregistrering?.id}">${dokumentlinkInstance?.referanseregistrering?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Tilknyttetregistrering Som:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:dokumentlinkInstance, field:'tilknyttetregistreringSom')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Dokumentnummer:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:dokumentlinkInstance, field:'dokumentnummer')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Tilknyttetdato:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:dokumentlinkInstance, field:'tilknyttetdato')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Tilknyttetav:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:dokumentlinkInstance, field:'tilknyttetav')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Dokumentbeskrivelse:</td>
                            
                            <td valign="top" class="value"><g:link controller="dokumentbeskrivelse" action="show" id="${dokumentlinkInstance?.dokumentbeskrivelse?.id}">${dokumentlinkInstance?.dokumentbeskrivelse?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${dokumentlinkInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
