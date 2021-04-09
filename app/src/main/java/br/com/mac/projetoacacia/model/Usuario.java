package br.com.mac.projetoacacia.model;


public class Usuario {

    public String username;
    private String userId;
    private String email;
    public String password;
    private String id;

    public Usuario(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Usuario(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
