package org.serogr.apiservlet.webapp.cookies.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* Obtener la Cookie de usuario; si existe se da el mensaje de bienvenida, si no
         * se carga el formulario de login
         * 1- Leer la Cookie (Obtenerla) en cada request se envía la cookie al servidor y se tiene que leer
         *
         *
         * 2-Primero validamos que la cookie no venga vacía para que no de un null pointer exception*/
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];
        // 3- Obtener la Cookie
        Optional<String> cookieOptional = Arrays.stream(cookies)
                .filter(cookie -> "username".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
        // 4- Si la cookie tiene contenido, se carga un anuncio de bienvenida.
        if (cookieOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("   <head>");
                out.println("       <meta charset=\"UTF-8\">");
                out.println("           <title>Hola " + cookieOptional.get() + "</title>");
                out.println("   </head>");
                out.println("   <body>");
                out.println("       <h1>Hola " + cookieOptional.get() + "</h1>");
                out.println("   </body>");
                out.println("</html>");
            }
        //5- En caso de que no haya cookie guardada, se carga el login de bienvenida.
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)){

            Cookie usernameCookie = new Cookie("username", username);
            resp.addCookie(usernameCookie);
            resp.setContentType("text/html;charset=UTF8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("   <head>");
                out.println("       <meta charset=\"UTF-8\">");
                out.println("           <title>Login</title>");
                out.println("   </head>");
                out.println("   <body>");
                out.println("       <h1>Login correcto!</h1>");
                out.println("       <h3>Hola " + username + "!</h3>");
                out.println("   </body>");
                out.println("</html>");
            }

        } else {
            //sendError permite enviar un mensaje de error personalizado
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, no está autorizado para ingresar a esta página!");
        }
    }
}
