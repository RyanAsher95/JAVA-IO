package DM19S1;

/**
 * @Project DM19S1
 * @Package DM19S1
 * @Author Ryan<ywan3120 @ uni.sydney.edu.au>
 * @Date 26/04/2019
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Ryan
 * @Description: Process input records with instructions
 **/
public class Process {
    private ArrayList<Donator> donator;
    private String report = "";

    /**
     * @Author Ryan
     * @Description: Construct  constructor
     **/
    public Process() {
    }

    public Process(ArrayList<Donator> donator) {
        this.donator = donator;
    }

    /**
     * @Author Ryan
     * @Description: Getters and setters
     **/
    public ArrayList<Donator> getDonator() {
        return donator;
    }

    public void setDonator(ArrayList<Donator> donator) {
        this.donator = donator;
    }

    public String getReport() {
        return report;
    }

    /**
     * @return void
     * @Author Ryan
     * @Description: Execute instructions
     * @Param [commands]
     **/
    public void executeInstructions(String[] instructions) throws ParseException {
        for (String i : instructions) {
            i.trim();
            if (i.startsWith("update")) {
                String instruction = i.substring(7).trim();
                report += update(instruction);
            } else if (i.startsWith("delete")) {
                String[] delete = i.substring(7).trim().split(";");
                String name = delete[0].trim();
                String birthday = delete[1].trim();
                report += delete(name, birthday);
            } else if (i.startsWith("donate")) {
                String instruction = i.substring(7).trim();
                report += donate(instruction);
            } else {
                String instruction = i.substring(6).trim();
                report += query(instruction);
            }
        }
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Update or add basic information
     * @Param [info]
     **/
    private String update(String info) {
        StringBuilder sb = new StringBuilder();
        Donator dr = new Donator();
        String[] a = info.split("; |;");
        for (String str : a) {
            str.trim();
        }
        sb.append("\n" + "\n----update ").append(a[0]).append("----\n\r");
        for (String str : a) {
            if (str.startsWith("name")) {
                dr.setName(str.substring(5));
            } else if (str.startsWith("birthday")) {
                dr.setBirthday(str.substring(9));
            } else if (str.startsWith("phone")) {
                dr.setPhone(str.substring(6));
            } else if (str.startsWith("address")) {
                dr.setAddress(str.substring(8));
            } else if (str.startsWith("postcode")) {
                dr.setPostcode(str.substring(9));
            }
        }
        for (Donator d : donator) {
            if (d.getName().equals(dr.getName()) && d.getBirthday().equals(dr.getBirthday())) {
                sb.append("\n" + "\nRecord updated!\n\r");
                if (dr.getAddress() != null)
                    d.setAddress(dr.getAddress());
                if (dr.getPhone() != null)
                    d.setPhone(dr.getPhone());
                if (dr.getPostcode() != null)
                    d.setPostcode(dr.getPostcode());
            }
        }
        if (dr.getName() != null && dr.getBirthday() != null && !sb.toString().endsWith("updated!\n\r")) {
            donator.add(dr);
            sb.append("\n" + "\nRecord added!\n\r");
        } else if (!sb.toString().endsWith("ed!\n\r")) sb.append("\n" + "\nInvalid instruction (" + a[0] + ")!\n\r");

        sb.append("\n" + "\n-------------------------\n\r\n\r");
        return sb.toString();
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Add donation information
     * @Param [s]
     **/
    private String donate(String s) throws ParseException {
        StringBuilder sb = new StringBuilder();
        String[] a = s.split("; |;");
        for (String str : a) {
            str.trim();
        }
        for (Donator d : donator) {
            if (d.getName().equals(a[0]) && d.getBirthday().equals(a[1])) {
                sb.append("\n" + "\n----donate ").append(a[0]).append("----\n\r");
                sb.append("\n" + "\n" + (a.length - 2)).append(" new donation record(s) for ").append(a[0]).append(" birthday ").append(a[1] + "\n\r");
                sb.append("\n" + "\n-------------------------\n\r\n\r");
                for (int i = 2; i < a.length; i++) {
                    String[] temp = a[i].trim().split(", |,");
                    if (d.getRecipient() != null && d.getDonation() != null) {
                        String[] dn = d.getDonation().trim().split(",|, ");
                        String[] rt = d.getRecipient().trim().split(", |,");
                        int[] donation = new int[dn.length];
                        if (dn.length == rt.length) {
                            if (d.getRecipient().contains(temp[0].trim())) {
                                for (int j = 0; j < rt.length; j++) {
                                    donation[j] = Integer.parseInt(dn[j].trim());
                                    if (rt[j].trim().equals(temp[0].trim())) {
                                        donation[j] = Integer.parseInt(dn[j].trim()) + Integer.parseInt(temp[1].trim());
                                    }
                                }
                                String str = "";
                                for (int o : donation) {
                                    str += o + ", ";
                                }
                                str = str.substring(0, str.length() - 2);
                                d.setDonation(str);
                            } else {
                                d.setRecipient(d.getRecipient() + ", " + temp[0]);
                                d.setDonation(d.getDonation() + ", " + temp[1]);
                            }
                        }
                    } else {
                        d.setRecipient(temp[0]);
                        d.setDonation(temp[1]);
                    }
                }

            }

        }
        if (sb.toString().equals("")) {
            sb.append("\n" + "\n----donate ").append(a[0]).append("----\n\r");
            sb.append("\n" + "\nInvalid instruction!\n\r");
            sb.append("\n" + "\n-------------------------\n\r\n\r");
        }
        return sb.toString();
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Delete donator
     * @Param [name, birthday]
     **/
    private String delete(String name, String birthday) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n" + "\n----delete ").append(name).append("----\n\r");
        for (Donator dr : donator) {
            if (dr.getName().equals(name) && dr.getBirthday().equals(birthday)) {
                sb.append("\n" + "\nRecord deleted!\n\r");
                donator.remove(dr);
                break;
            }
        }
        if (!sb.toString().endsWith("deleted!\n\r")) {
            sb.append("\n" + "\nRecord not found!\n\r");
        }
        sb.append("\n" + "\n-------------------------\n\r\n\r");
        return sb.toString();
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Integration of query methods
     * @Param [info]
     **/
    private String query(String info) throws ParseException {
        StringBuilder sb = new StringBuilder();
        if (info.startsWith("name")) {
            String name = queryName(info.substring(5).trim());
            sb.append(name);
        } else if (info.startsWith("recipients")) {
            String recipients = queryRecipient();
            sb.append(recipients);
        } else if (info.startsWith("top")) {
            String top = queryTop(Integer.parseInt(info.substring(4).trim()));
            sb.append(top);
        }
        return sb.toString();
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Query name and sort
     * @Param [name]
     **/
    private String queryName(String name) throws ParseException {
         ArrayList<Donator> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        for (Donator dr : donator) {
            if (name.equals(dr.getName())) {
                if(result.size()==0){
				result.add(dr);
				}
				else{
				Date date=sdf.parse(dr.getBirthday());
				
				for(int i=0;i<result.size();i++){
				Date date1=sdf.parse(result.get(i).getBirthday());
				
				if(date.before(date1)){
				  result.add(i,dr);
				  break;}}}
            }
        }
        
        if (!result.isEmpty()) {
            sb.append("\n" + "\n----------query name ").append(name).append("----------\n\r");
            sb.append("\n" + "\n").append(result.size()).append(" record(s) found:\n\r");
            for (Donator dr : result) {
                sb.append("\n" + "\nname ").append(dr.getName() + "\n\r").append("\n" + "\nbirthday ").append(dr.getBirthday() + "\n\r");
                if (dr.getAddress() != null)
                    sb.append("\n" + "\naddress ").append(dr.getAddress() + "\n\r");
                if (dr.getPostcode() != null)
                    sb.append("\n" + "\npostcode ").append(dr.getPostcode() + "\n\r");
                if (dr.getPhone() != null)
                    sb.append("\n" + "\nphone ").append(dr.getPhone() + "\n\r");
                if (dr.getRecipient() != null)
                    sb.append("\n" + "\nrecipient ").append(dr.getRecipient() + "\n\r");
                if (dr.getDonation() != null)
                    sb.append("\n" + "\ndonation ").append(dr.getDonation() + "\n\r");
            }
            sb.append("\n" + "\n----------------------------------------\n\r\n\r");
        }
        return sb.toString();
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Query top n donation
     * @Param [n]
     **/
    private String queryTop(int n) throws ParseException {
        Comparator<Map.Entry<String, Integer>> descendingSort = (o1, o2) -> o2.getValue() - o1.getValue();
        String results = "\n" + "\n----query top " + n + "----\n\r";
        HashMap<String, Integer> donation = new HashMap<>();
        for (Donator dr : donator) {
            if (dr.getDonation() != null) {
                String[] dn = dr.getDonation().split(", |,");
                String[] rt = dr.getRecipient().split(", |,");
                int d = 0;
                if (dn.length == rt.length) {
                    for (int i = 0; i < dn.length; i++) {
                        d += Integer.parseInt(dn[i]);
                    }
                    donation.put(dr.getName() + "; " + dr.getBirthday() + "; ", d);
                }
            }

        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(donation.entrySet());
        list.sort(descendingSort);
        for (int i = 0; i < n; i++) {
                results += "\n" + "\n" + list.get(i).getKey() + list.get(i).getValue().toString() + "\n\r";
            }
        results += "\n" + "\n---------------------------\n\r\n\r";
        return results;
    }

    /**
     * @return java.lang.String
     * @Author Ryan
     * @Description: Query recipients by postcode
     * @Param []
     **/
    private String queryRecipient() throws ParseException {
        String results = "\n" + "\n----query recipients----\n\r";
        HashMap<String, Integer> list = new HashMap<>();
        int donation = 0;
        for (Donator dr : donator) {
            if (dr.getRecipient() != null) {
                String[] dn = dr.getDonation().trim().split(", |,");
                String[] rt = dr.getRecipient().trim().split(", |,");
                int[] d = new int[dn.length];
                if (dn.length == rt.length) {
                    for (int i = 0; i < dn.length; i++) {
                        d[i] = Integer.parseInt(dn[i]);
                        if (list.containsKey(rt[i])) {
                            if (list.get(rt[i]) != null && d[i] > list.get(rt[i])) {
                                list.put(rt[i], d[i]);
                            }
                        } else list.put(rt[i], d[i]);
                    }
                }
            }

        }

        for (String key : list.keySet()) {
            ArrayList<String> postcode = new ArrayList<>();
            for (Donator dr : donator)
                if (dr.getRecipient() != null) {
                    if (dr.getRecipient().contains(key) && dr.getDonation().contains(list.get(key).toString())) {
                        postcode.add(dr.getPostcode());
                    }
                }
            String p = "postcode ";
            for (String pe : postcode) {
                p += pe + ", ";
            }
            p = p.substring(0, p.length() - 2);
            results += "\n" + "\n" + key + ": " + list.get(key) + "; " + p + "\n\r";
        }
        results += "\n" + "\n---------------------------\n\r\n\r";
        return results;
    }


}


