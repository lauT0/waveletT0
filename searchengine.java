import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
// The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringLst = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return stringLst.toString();
        } 
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    stringLst.add(parameters[1]);
                    return "added %s" + parameters[1];
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    ArrayList<String> toReturn = new ArrayList<String>();
                    for (String s: stringLst){
                        if (s.contains(parameters[1])){
                            toReturn.add(s);
                        }
                    }
                    return toReturn.toString();
                }
            }
            else{
                return "invalid query";
            }
        }
        return "404 Not Found!";
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
