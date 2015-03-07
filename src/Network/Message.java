package Network;


public enum Message {
    TEXT, GUESS, RESPONSE, QUESTION, NONE;

    public String toString() {
        switch (this){
            case TEXT:
                return "TEXT";
            case GUESS:
                return "GUESS";
            case RESPONSE:
                return "RESPONSE";
            case QUESTION:
                return "QUESTION";
            default:
                return "NONE";
        }
    }
    public static Message fromString(String s){
        if (s.equals(TEXT.toString())){
            return TEXT;
        }
        else if (s.equals(GUESS.toString())){
            return GUESS;
        }
        else if (s.equals(RESPONSE.toString())){
            return RESPONSE;
        }
        else if (s.equals(QUESTION.toString())){
            return QUESTION;
        }
        else {
            return NONE;
        }
    }
}
