
<%@ page import="no.friark.ds.Saksansvar" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Saksansvar</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Saksansvar List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Saksansvar</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${saksansvarInstance}">
            <div class="errors">
                <g:renderErrors bean="${saksansvarInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<g:render template="create_form"/>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
