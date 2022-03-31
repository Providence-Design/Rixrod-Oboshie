package fashion.oboshie.models;

public interface EmailSender {
    void send(String to, String email, String subject);
}
