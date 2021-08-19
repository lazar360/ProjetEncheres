<jsp:include page="header.jsp">  
<jsp:param name="titre" value="login" />  
</jsp:include>

	<c:forEach var="key" items="${errModel.err}">
		<c:if test="${key == 'ErrLog'}">
		<p style="color:red">${errModel.errMessage.get('ErrLog')}</p>
		</c:if>	
	</c:forEach>
	<form action="LoginServlet" method="post">
		<p>Identifiant: <input type="text" name="identifiant" required="required"/></p>
		<p>Mot de passe: <input type="password" name="password" required="required"/></p>
		<button type="submit" name="formulaireLogin" value="Connexion">Connexion</button>
		<input type="checkbox" name="checkRememberMe" value="true"/>
		Se souvenir de moi
		</p>
		<a href="#">Mot de passe oubli�</a>
	</form>
	
	<a href="#">Cr�er un compte</a>
	
<jsp:include page="footer.jsp"/> 