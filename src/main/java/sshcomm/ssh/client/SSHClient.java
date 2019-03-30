package sshcomm.ssh.client;

public interface SSHClient {
    void connect(String server, int port, String user, String creds);
}
