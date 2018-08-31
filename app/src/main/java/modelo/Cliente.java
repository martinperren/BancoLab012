package modelo;

public class Cliente {

    private String mail;
    private String cuil;

    @Override
    public String toString() {
        return "Cliente{" +
                "mail='" + mail + '\'' +
                ", cuil='" + cuil + '\'' +
                '}';
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
