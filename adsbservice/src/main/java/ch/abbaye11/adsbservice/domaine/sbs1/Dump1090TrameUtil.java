package ch.abbaye11.adsbservice.domaine.sbs1;

import java.util.Arrays;
import java.util.Objects;

public class Dump1090TrameUtil {

    private final static int TRAME_SPLIIT_TOKEN_MIN = 10;


    /**
     * Validation de la trame au format dump1090:
     * - la trame ne doit pas être vide
     * - la trame doit contenit au moins 10 tokens via separateur
     * - l'élément 0 des tokens doit contenit un type de message identifié
     * @param tcpTrame la trame au format string reçu
     * @return l'état de cohérence de la trame
     */
    public static Boolean validatDump1090TCPTrame(String tcpTrame){

        try{
            Objects.requireNonNull(tcpTrame,"Trame can't be null");

            if(tcpTrame.isEmpty())
                return false;

            if(tcpTrame.split(",",-1).length < 10)
                return false;

            SBS1MessageType sbs1MessageType = Arrays.stream(SBS1MessageType.values()).filter(messageType -> {
                return tcpTrame.split(",")[0].equals(messageType.toString());
            }).findFirst().orElseThrow(()->
                    new TcpTrameEception("Cannot find message type correlation : " + tcpTrame.split(",",-1)[0]));

        }catch (Exception e) {

            return false;
        }

        return true;
    }

}
