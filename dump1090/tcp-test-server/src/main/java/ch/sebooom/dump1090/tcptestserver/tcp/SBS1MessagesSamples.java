package ch.sebooom.dump1090.tcptestserver.tcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * SBS1 Messages samples for tests purpose
 */
class SBS1MessagesSamples {

    static List<String> messages = new ArrayList<>();
    static List<String> erroneusMessages = new ArrayList<>();


    static{

        erroneusMessages.add("MSG,3,,,,32,32,,");
        messages.add("MSG,5,,,3CEFF1,,,,,,,18000,,,,,,,,,,");
        messages.add("MSG,8,,,4BB843,,,,,,,,,,,,,,,,,");
        messages.add("MSG,4,,,4010E9,,,,,,,,396,174,,,-960,,0,0,0,0");
        messages.add("MSG,8,,,4005BF,,,,,,,,,,,,,,,,,");
        messages.add("MSG,8,,,4BB843,,,,,,,,,,,,,,,,,");
        messages.add("MSG,5,,,3C6675,,,,,,,33975,,,,,,,0,0,0,0");
        messages.add("MSG,8,,,4005BF,,,,,,,,,,,,,,,,,");
        messages.add("MSG,5,,,3C6675,,,,,,,33975,,,,,,,0,0,0,0");
        messages.add("MSG,5,,,3C5EF1,,,,,,,38975,,,,,,,,,,");
        messages.add("MSG,5,,,3C6675,,,,,,,33975,,,,,,,0,0,0,0");
        messages.add("MSG,3,,,732443,,,,,,,35000,,,47.60120,9.67120,,,0,0,0,0");
        messages.add("MSG,8,,,896188,,,,,,,,,,,,,,,,,");
        messages.add("MSG,3,,,4690F0,,,,,,,34000,,,47.69775,7.01928,,,0,0,0,0");
        messages.add("MSG,3,,,344286,,,,,,,13125,,,47.33922,8.20040,,,0,0,0,0");
        messages.add("MSG,8,,,44CE6B,,,,,,,,,,,,,,,,,");
        messages.add("MSG,3,,,4005BF,,,,,,,34000,,,47.44711,7.70754,,,0,0,0,0");
        messages.add("MSG,3,,,44CE6B,,,,,,,39025,,,47.13763,6.68067,,,0,0,0,0");
        messages.add("MSG,3,,,3C4AA6,,,,,,,32025,,,47.82204,6.72863,,,0,0,0,0");
        messages.add("MSG,3,,,3C6675,,,,,,,33975,,,48.10373,8.81989,,,0,0,0,0");
        messages.add("MSG,4,,,44CE6B,,,,,,,,385,174,,,0,,0,0,0,0");
        messages.add("MSG,5,,,3C56ED,,,,,,,19600,,,,,,,0,0,0,0");
        messages.add("MSG,5,,,710060,,,,,,,34000,,,,,,,,,,");
        messages.add("MSG,8,,,4690F0,,,,,,,,,,,,,,,,,");
        messages.add("SEL,,496,2286,4CA4E5,27215,2010/02/19,18:06:07.710,2010/02/19,18:06:07.710,RYR1427");
        messages.add("ID,,496,7162,405637,27928,2010/02/19,18:06:07.115,2010/02/19,18:06:07.115,EZY691A");
        messages.add("AIR,,496,5906,400F01,27931,2010/02/19,18:06:07.128,2010/02/19,18:06:07.128");
        messages.add("STA,,5,179,400AE7,10103,2008/11/28,14:58:51.153,2008/11/28,14:58:51.153,RM");
        messages.add("CLK,,496,-1,,-1,2010/02/19,18:18:19.036,2010/02/19,18:18:19.036");
        messages.add("MSG,1,145,256,7404F2,11267,2008/11/28,23:48:18.611,2008/11/28,23:53:19.161,RJA1118,,,,,,,,,,,");
        messages.add("MSG,2,496,603,400CB6,13168,2008/10/13,12:24:32.414,2008/10/13,12:28:52.074,,,0,76.4,258.3,54.05735,-4.38826,,,,,,0");
        messages.add("MSG,3,496,211,4CA2D6,10057,2008/11/28,14:53:50.594,2008/11/28,14:58:51.153,,37000,,,51.45735,-1.02826,,,0,0,0,0");
        messages.add("MSG,4,496,469,4CA767,27854,2010/02/19,17:58:13.039,2010/02/19,17:58:13.368,,,288.6,103.2,,,-832,,,,,");
        messages.add("MSG,5,496,329,394A65,27868,2010/02/19,17:58:12.644,2010/02/19,17:58:13.368,,10000,,,,,,,0,,0,0");
        messages.add("MSG,6,496,237,4CA215,27864,2010/02/19,17:58:12.846,2010/02/19,17:58:13.368,,33325,,,,,,0271,0,0,0,0");
        messages.add("MSG,7,496,742,51106E,27929,2011/03/06,07:57:36.523,2011/03/06,07:57:37.054,,3775,,,,,,,,,,0");
        messages.add("MSG,8,496,194,405F4E,27884,2010/02/19,17:58:13.244,2010/02/19,17:58:13.368,,,,,,,,,,,,0");
    }

    static String getRandomMsg(){

        return messages.get(new Random().nextInt(messages.size()-1)) + "\r\n";
    }

    static String getRandomErroneusMsg(){

        return erroneusMessages.get(0) + "\r\n";
    }



}
