<%@ include file="/init.jsp" %>
<%@ page import="com.liferay.portal.kernel.json.JSONArray" %>

<table class="table table-striped">
	<thead>
		<tr>
			<th>ID</th>
			<th>AVATAR</th>			
			<th>NOMBRE</th>
			<th>APELLIDOS</th>
			<th>EMAIL</th>
		</tr>
	</thead>
	<tbody>
	
	<% JSONArray data = (JSONArray)request.getAttribute("Data"); 
	//out.println(data.get(0));%>
		<% for(int i = 0; i < data.length(); i++) { %>					
		<tr>
			<td>
				<div class="table-title">
					<%= data.getJSONObject(i).getString("id") %>
				</div>
			</td>
			<td><img src="<%= data.getJSONObject(i).getString("avatar") %>" /></td>
			<td><%= data.getJSONObject(i).getString("first_name") %></td>
			<td><%= data.getJSONObject(i).getString("last_name") %></td>
			<td><%= data.getJSONObject(i).getString("email") %></td>
		</tr>
		 <% } %>
	</tbody>
</table>


