package persistence;

import model.PokemonToEdit;
import model.PokemonToView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;

// web crawler utility
public class Crawler {
    private static final String URL = "https://bulbapedia.bulbagarden.net/wiki/";

    // REQUIRE: pokemon can be found
    // EFFEFCT: fetch for the html page of current pokemon
    private static Document fetch(String name) throws IOException {
        return Jsoup.connect(URL + name + "_(Pok%C3%A9mon)").get();
    }

    // REQUIRE: pokemon can be found
    // EFFEFCT: load pokemon online
    public static PokemonToEdit loadPokemon(String name) throws IOException {
        Document content = fetch(name.toLowerCase());
//        System.out.println("loading pokemon " + name);

        PokemonToEdit pkm = new PokemonToEdit();

        String id = content.select("a[title=\"List of Pokémon by National Pokédex number\"] > span").get(1).html();
        Elements types = content.select("table[style=\"margin:auto; background:none;\"] > tbody").get(0).select("b");
        Elements abilities = content.select("td[style!=\"display: none\"] > a[title$=\"(Ability)\"] > span");
        Elements stats = content.select("th[style=\"width:85px; padding-left:0.5em; padding-right:0.5em\"]");

        String type = "";
        for (Element element : types) {
            if (!type.equals("Unkown")) {
                type = type.concat(element.html() + " ");
            }
        }

        String ability = "";
        for (Element element : abilities) {
            ability = ability.concat(element.html() + " ");
        }

        setup(pkm, name, id, type, ability);

        for (int i = 0; i < 6; i++) {
            pkm.setSS(i, Integer.parseInt(stats.get(i).getElementsByIndexEquals(1).html()));
        }

        return pkm;
    }

    private static void setup(PokemonToEdit pkm, String name, String id, String type, String ability) {
        pkm.setName(name);
        pkm.setId(Integer.parseInt(id.substring(1)));
        pkm.setType(type.substring(0, type.length() - 1));
        pkm.setAbility(ability.substring(0, ability.length() - 1));
    }

    public static ImageIcon loadImage(PokemonToView pkm) {
        String name = pkm.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        int id = pkm.getId();
        String idString = id + "";
        if (id / 100 == 0) {
            idString = "0" + idString;
            if (id / 10 == 0) {
                idString = "0" + idString;
            }
        }
        String query = URL + "File:" + idString + name + ".png";
        Connection content = Jsoup.connect(query);
        try {
            Document doc = content.get();
            Element e = doc.select("div.fullImageLink > a").first();
            String url = e.attr("href");
            return new ImageIcon(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
