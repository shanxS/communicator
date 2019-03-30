package sshcomm.ssh.client.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import sshcomm.ssh.client.SSHClient;
import sshcomm.ssh.client.SSHInputStream;

import javax.inject.Inject;
import java.io.InputStream;
import java.io.OutputStream;

public class JSchSSH implements SSHClient, Runnable {

    private OutputStream outputStream;
    private InputStream inputStream;

    @Inject
    public JSchSSH(OutputStream os, InputStream is) {
        this.outputStream = os;
        this.inputStream = is;
    }

    @Override
    public void run() {
        connect("127.0.0.1",  22, "shashaku","!IPFood2017");
    }

    @Override
    public void connect(String server, int port, String user, String creds) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, server, 22);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(creds);
            session.setServerAliveInterval(300000);
            session.connect(30000);
            Channel channel=session.openChannel("shell");

//            channel.setInputStream(System.in);
            channel.setInputStream(inputStream);
            channel.setOutputStream(outputStream);
            channel.connect();

        } catch (Exception e) {
            System.out.print("Cant connect to ssh server " + e.getMessage());
        }
    }
}
