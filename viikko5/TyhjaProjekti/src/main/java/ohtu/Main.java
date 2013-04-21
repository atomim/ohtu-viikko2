package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
 
public class Main {
 
    public static void main(String[] args) throws IOException {
        String studentNr = "14031450";
        if ( args.length>0) {
            studentNr = args[0];
        }
 
        String url = "http://ohtustats-2013.herokuapp.com/opiskelija/"+studentNr+".json";
 
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        client.executeMethod(method);
 
        InputStream stream =  method.getResponseBodyAsStream();
 
        String bodyText = IOUtils.toString(stream);
 
        System.out.println("json-muotoinen data:");
        System.out.println( bodyText );
 
        Gson mapper = new Gson();
        Palautukset palautukset = mapper.fromJson(bodyText, Palautukset.class);
        int tehtavia = 0;
        int tunteja =0;
        for (Palautus palautus : palautukset.getPalautukset()) {
            System.out.print("Viikko "+palautus.getViikko()+": ");
            System.out.print(palautus.getTehtavia()+" tehtävää ");
            System.out.print(palautus.getTehtavat());
            System.out.print("  Aikaa kului:"+ palautus.getTunteja());
            System.out.println("");
            tehtavia+=palautus.getTehtavia();
            tunteja+=palautus.getTunteja();
        }
        System.out.println("Yhteensä: "+tehtavia+" tehtävää ja "+tunteja+" tuntia");
        
 /*
        System.out.println("oliot:");
        int viikko =1 ;
        for (Palautus palautus : palautukset.getPalautukset()) {
            System.out.println( palautus );
            viikko++;
        }
 */
        
    }
}