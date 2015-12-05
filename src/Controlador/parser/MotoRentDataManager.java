package controlador.parser;

import controlador.Consola;
import controlador.MotoRent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.Admin;
import modelo.Client;
import modelo.Direccio;
import modelo.Gerent;
import modelo.Local;
import modelo.Moto;

/**
 * Data manager per MotoRent . Crea les estructures de dades necessàries per a
 * manegar l'aplicació de gestió d'MotoRent .
 *
 * @author Professors disseny de software
 */
public class MotoRentDataManager {

    /* -------------------------------------------------------------------
     * Metodes a implementar per vosaltres. En aquests metodes creareu els
     * vostres objectes a partir de la informacio obtinguda del fitxer XML
     * 
     * Podeu esborrar els prints si voleu. Tambe podeu canviar el tipus de
     * dades que retorna el metode, es a dir que sou lliures de
     * modificar-ho al gust, excepte les crides inicials que es fan.
     * -------------------------------------------------------------------
     */
    private MotoRent motoRent;

    /**
     * Constructor vacío
     */
    public MotoRentDataManager() {
    }

    /**
     * Obté les dades del fitxer XML passat per paràmetre
     *
     * @param nomFitxer ruta del fitxer XML del que obtenir les dades
     */
    public void obtenirDades(String nomFitxer) {
        MotoRentXMLParser parser = new MotoRentXMLParser(this);
        parser.parse(nomFitxer);
    }

    public void iniciar(MotoRent mr) {
        this.motoRent = mr;
    }

    /**
     * Crea a partir de la informacio obtinguda del fitxer XML
     *
     * @param id id de l'local. El podeu utilitzar o no
     * @param capacitat quantita màxima de motos
     * @param gestorID gestorID del local
     * @param adreca adreça del local
     */
    public void crearLocal(String id, String capacitat, String gestorID, String adreca) {

        //El nombre de la calle llega hasta el primero número
        Matcher m = Pattern.compile("\\d").matcher(adreca);
        int finCalle = m.find() ? m.start() + 1 : 0;

        //Info dirección
        String calle = adreca.substring(0, finCalle - 1);

        String numero = adreca.substring(finCalle - 1, adreca.indexOf(","));
        String cp = adreca.substring(adreca.indexOf(", ") + 2, adreca.indexOf(",") + 7);
        String ciudad = adreca.substring(adreca.indexOf(",") + 9);

        //Creo dirección
        Direccio dir = new Direccio(ciudad, calle, Integer.valueOf(numero), cp);

        //Crear local y ponerlo en motorent
        Local loc = new Local(dir, Integer.valueOf(capacitat));
        this.motoRent.getLocales().add(loc);

        Consola.escriu("\nlocal amb ID: " + id + "\n");
        Consola.escriu("--------------------------------------------------\n");
        Consola.escriu("Capacitat: " + capacitat + "\n");
        Consola.escriu("Gestor ID: " + gestorID + "\n");
        Consola.escriu(dir.toString());
    }

    /**
     * Crea una nova moto a partir de la informacio obtinguda del fitxer XML
     *
     * @param id id de la moto. El podeu utilitzar o no
     * @param matricula matrícula de la moto
     * @param marca marca de la moto
     * @param model model de la moto
     * @param color color de la moto
     * @param estat estat de la moto: "true" representa moto activa i "false"
     * per reparar
     * @return moto creada
     */
    public Moto crearMoto(String id, String matricula, String marca, String model, String color, String estat) {

        //Cilindrada de la moto sacada del modelo
        Matcher m = Pattern.compile("\\d").matcher(model);
        int inicioCc = m.find() ? m.start() + 1 : 0;
        int finCc = model.indexOf(" ", inicioCc);

        if (finCc == -1) {
            finCc = model.length();
        }

        String cilindrada = model.substring(inicioCc - 1, finCc);

        //Estado e ID sin la letra 'm'
        boolean estado = estat.equals("true");
        id = id.substring(1, id.length());

        Moto moto = new Moto(matricula, color, marca, model, Integer.valueOf(cilindrada), Integer.valueOf(id), estado);

        Consola.escriu("\nmoto amb ID: " + id + "\n");
        Consola.escriu("--------------------------------------\n");
        Consola.escriu("Matrícula: " + matricula + "\n");
        Consola.escriu("Marca: " + marca + "\n");
        Consola.escriu("Model: " + model + "\n");
        Consola.escriu("Color: " + color + "\n");
        Consola.escriu("Estat: " + estat + "\n");
        Consola.escriu("Cilindradas: " + cilindrada + "\n");

        return moto;
    }

    /**
     * Crea un trajecte a partir de la informacio obtinguda del fitxer XML
     *
     * @param id id del trajecte. El podeu utilitzar o no
     * @param client identificador del client. El podeu utilitzar o no
     * @param moto identificador de la moto. El podeu utilitzar o no
     * @param local_inici identificador del local d'inici
     * @param hora_inici hora d'inici de trajecte
     * @param local_fi identificador del local final de trajecte
     * @param hora_fi hora de finalització de trajecte
     */
    public void crearReserva(String id, String client, String moto, String cost, String falta, String local_inici, String hora_inici, String fecha_inici, String local_fi, String hora_fi, String fecha_fi) {

        /* TODO: A partir d'aqui creeu el trajecte
         */
        Consola.escriu("\nReserva amb ID: " + id + "\n");
        Consola.escriu("--------------------------------------\n");
        Consola.escriu("Client: " + client + "\n");
        Consola.escriu("Moto: " + moto + "\n");
        Consola.escriu("Cost: " + cost + "\n");
        Consola.escriu("Faltes: " + falta + "\n");
        Consola.escriu("Local d'inici: " + local_inici + "\n");
        Consola.escriu("Hora d'inici: " + hora_inici + "\n");
        Consola.escriu("Data d'inici: " + fecha_inici + "\n");
        Consola.escriu("Local de finalització: " + local_fi + "\n");
        Consola.escriu("Hora de finalització: " + hora_fi + "\n");
        Consola.escriu("Data de finalització: " + fecha_fi + "\n");
    }

    /**
     * Crea un nou admin a partir de la informacio obtinguda del fitxer XML
     *
     * @param id id del administrador
     * @param nom nom del administrador
     * @param usuari usuari del administrador
     * @param password password del administrador
     * @param cognoms del administrador
     */
    public void crearAdmin(String id, String nom, String usuari, String password) {

        //Separo nombre y apellidos
        String cognoms = "";
        if (nom.contains(" ")) {
            cognoms = nom.substring(nom.indexOf(" ") + 1);
            nom = nom.substring(0, nom.indexOf(" "));
        }

        //Crear admin y ponerlo en motorent
        Admin adm = new Admin(usuari, password, nom, cognoms);
        this.motoRent.setAdmin(adm);

        Consola.escriu("\nAdmin ID: " + id + "\n");
        Consola.escriu("-----------------\n");
        Consola.escriu("Nom: " + nom + "\n");
        Consola.escriu("Cognoms: " + cognoms + "\n");
        Consola.escriu("Usuari: " + usuari + "\n");
        Consola.escriu("Password: " + password + "\n");
    }

    /**
     * Crea un nou gestor a partir de la informacio obtinguda del fitxer XML
     *
     * @param id id del gestor
     * @param nom nom del gestor
     * @param usuari usuari del gestor
     * @param password password del gestor
     * @param cognoms cognoms del gestor
     */
    public void crearGestor(String id, String nom, String usuari, String password) {

        //Separo nombre y apellidos
        String cognoms = "";
        if (nom.contains(" ")) {
            cognoms = nom.substring(nom.indexOf(" ") + 1);
            nom = nom.substring(0, nom.indexOf(" "));
        }
        //Crear gerente y ponerlo en motorent
        Gerent nGer = new Gerent(usuari, password, nom, cognoms);
        this.motoRent.getGerentes().add(nGer);

        Consola.escriu("\nGestor ID: " + id + "\n");
        Consola.escriu("-----------------\n");
        Consola.escriu("Nom: " + nom + "\n");
        Consola.escriu("Cognoms: " + cognoms + "\n");
        Consola.escriu("Usuari: " + usuari + "\n");
        Consola.escriu("Password: " + password + "\n");
    }

    /**
     * Crea un nou client a partir de la informacio obtinguda del fitxer XML
     *
     * @param id id del client
     * @param nom nom del client
     * @param cognoms cognoms del client
     * @param dni dni del client
     * @param adreca adreça del client
     * @param usuari usuari al sistema del client
     * @param password password del client
     * @param vip true si el client es vip. false si no
     * @param renovacio true si el client renova automaticament. false si no
     * @param faltes nombre de faltes
     */
    public void crearClient(String id, String nom, String dni, String adreca, String usuari, String password, String vip, String renovacio, String faltes) {

        //Separo nombre y apellidos
        String cognoms = "";
        if (nom.contains(" ")) {
            cognoms = nom.substring(nom.indexOf(" ") + 1);
            nom = nom.substring(0, nom.indexOf(" "));
        }

        //El nombre de la calle llega hasta el primero número
        Matcher m = Pattern.compile("\\d").matcher(adreca);
        int finCalle = m.find() ? m.start() + 1 : 0;

        //Info dirección
        String calle = adreca.substring(0, finCalle - 1);

        String numero = adreca.substring(finCalle - 1, adreca.indexOf(","));
        String cp = adreca.substring(adreca.indexOf(", ") + 2, adreca.indexOf(",") + 7);
        String ciudad = adreca.substring(adreca.indexOf(",") + 9);

        //Creo dirección
        Direccio dir = new Direccio(ciudad, calle, Integer.valueOf(numero), cp);

        boolean esVip = vip.equals("true");

        //Crear cliente y ponerlo en motorent
        Client nCl = new Client(dni, dir, Integer.valueOf(faltes), usuari, password, nom, cognoms, esVip);
        this.motoRent.getClientes().add(nCl);

        Consola.escriu("\nClient ID: " + id + "\n");
        Consola.escriu("-----------------\n");
        Consola.escriu("Nom: " + nom + "\n");
        Consola.escriu("Cognoms: " + cognoms + "\n");
        Consola.escriu("Usuari: " + usuari + "\n");
        Consola.escriu("Dni: " + dni + "\n");
        Consola.escriu(dir.toString());
        Consola.escriu("Password: " + password + "\n");
        Consola.escriu("Es VIP: " + vip + "\n");
        Consola.escriu("Renovació automàtica: " + renovacio + "\n");
        Consola.escriu("Nombre de faltes: " + faltes + "\n");
    }

    public MotoRent getMotoRent() {
        return motoRent;
    }
}