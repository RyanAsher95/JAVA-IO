package DM19S1;

/**
 * @Project DM19S1
 * @Package DM19S1
 * @Author Ryan<ywan3120 @ uni.sydney.edu.au>
 * @Date 26/04/2019
 */

import java.io.*;
import java.util.*;

/**
 * @Author Ryan
 * @Description: Input and output operation
 **/
public class IO {

    private String records;
    private String instruction;
    private String report;
    private String result;

    /**
     * @Author Ryan
     * @Description: Construct constructor
     **/
    public IO(String[] s) {

        records = s[0];
        instruction = s[1];
        result = s[2];
        report = s[3];
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Getters
     * @Param []
     **/
    public String getRecords() {
        return records;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getReport() {
        return report;
    }

    public String getResult() {
        return result;
    }


    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Read txt into an whole string
     * @Param [path]
     **/
    public static String readFile(String path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream input = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line = "";
        line = br.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = br.readLine();
        }
        br.close();
        input.close();
        return buffer.toString();
    }


    /**
     * @return java.util.ArrayList<java.util.HashMap < java.lang.String, java.lang.String>>
     * @Author Ryan
     * @Description: Split whole string by blank line and set information
     * @Param [p]
     **/
    public static ArrayList<HashMap<String, String>> getMembers(String p) {
        ArrayList<HashMap<String, String>> members = new ArrayList<>();
        String[] text = p.split("\n\n");
        for (String t : text) {
            HashMap<String, String> map = new HashMap<>();
            String[] line = t.split("\n");
            for (String l : line) {
                String[] temp = l.trim().split("\\s");
                switch (temp[0]) {
                    case "name":
                        map.put("name", l.substring(5));
                        break;
                    case "birthday":
                        map.put("birthday", l.substring(9));
                        break;
                    case "address":
                        map.put("address", l.substring(8));
                        break;
                    case "postcode":
                        map.put("postcode", l.substring(9));
                        break;
                    case "phone":
                        map.put("phone", l.substring(6));
                        break;
                    case "donation":
                        map.put("donation", l.substring(9));
                        break;
                    case "recipient":
                        map.put("recipient", l.substring(10));
                        break;
                    default:
                        map.replace("address", map.get("address") + " " + l.trim());
                }
            }
            members.add(map);
        }
        return members;
    }

    /**
     * @return java.lang.String[]
     * @Author Ryan
     * @Description: Separate every command
     * @Param [paragraph]
     **/
    public static String[] getInstruction(String paragraph) {
        return paragraph.trim().split("\n\n");
    }

    /**
     * @return boolean
     * @Author Ryan
     * @Description: Validation of input members
     * @Param [member]
     **/
    public static boolean isValid(HashMap<String, String> member) {
        if (member.containsKey("name") && member.containsKey("birthday")) {
            if (member.containsKey("address")) {
                if (!validAddress(member.get("address"))) {
                    return false;
                }

            } else if (member.containsKey("postcode")) {
                if (!validPostcode(member.get("postcode"))) {
                    return false;
                }

            } else if (member.containsKey("phone")) {
                if (!validPhone(member.get("phone"))) {
                    return false;
                }

            }
            return validName(member.get("name")) && validBirthday(member.get("birthday"));
        } else
            return false;
    }

    private static boolean validName(String name) {
        if (name != null && name.length() > 0) {
            for (char ch : name.toCharArray()) {
                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch == ' ')))
                    return false;
            }
        }
        return true;
    }

    private static boolean validBirthday(String birthday) {
        if (birthday.matches("^(\\d{2})+(-\\d{2})+(-\\d{4})$")) {
            return true;
        } else return false;
    }

    private static boolean validAddress(String address) {
        if (address.endsWith("ACT") || address.endsWith("NT") || address.endsWith("SA") || address.endsWith("TAS") || address.endsWith("VIC") || address.endsWith("WA")||address.endsWith("NSW")||address.endsWith("QLD")) {
            return true;
        } else return false;
    }

    private static boolean validPostcode(String postcode) {
        if (postcode.matches("^\\d{4}$")) {
            return true;
        } else return false;
    }

    private static boolean validPhone(String phone) {
        if (phone.matches("^\\d{8}$")) {
            return true;
        } else return false;
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Get results string
     * @Param [records]
     **/
    public static String getResults(ArrayList<Donator> records) {
        StringBuilder results = new StringBuilder();
        for (Donator record : records) {
            results.append("\n" + "\nname ").append(record.getName()).append("\n\r");
            results.append("\n" + "\nbirthday ").append(record.getBirthday()).append("\n\r");
            if (record.getAddress() != null)
                results.append("\n" + "\naddress ").append(record.getAddress()).append("\n\r");
            if (record.getPostcode() != null)
                results.append("\n" + "\npostcode ").append(record.getPostcode()).append("\n\r");
            if (record.getPhone() != null)
                results.append("\n" + "\nphone ").append(record.getPhone()).append("\n\r");
            if (record.getDonation() != null&&record.getRecipient()!=null) {
                results.append("\n" + "\nrecipient ").append(record.getRecipient()).append("\n\r");
                results.append("\n" + "\ndonation ").append(record.getDonation()).append("\n\r");

            }
            results.append("\n\r");
        }
        return results.toString();
    }


    /**
     * @return void
     * @Author Ryan
     * @Description: Write string into file
     * @Param [path, s]
     **/
    public static void writeFile(String path, String s) {
        try {
            File file = new File(path);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.append(s);
            printStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

}