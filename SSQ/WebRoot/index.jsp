<%
 String context = request.getContextPath();
 String homePage = (context == null ? "" : context) + "/docs/index.html";
 response.sendRedirect(homePage);
 return;
%>