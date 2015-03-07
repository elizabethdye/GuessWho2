package Network;


public enum Message {
    TEXT, GUESS, RESPONSE, QUESTION;

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
}
