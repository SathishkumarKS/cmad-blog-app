package com.cisco.training.cmad.blog.clients;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by satkuppu on 27/04/16.
 */
public class PasswordHashClient {

    public static void main(String[] args) {
        String hashed = BCrypt.hashpw("Cisco_123", BCrypt.gensalt());
        System.out.println("hashed = " + hashed);

        if (BCrypt.checkpw("Cisco_123", "$2a$10$UPV1FP2spzD0AVdnajo2pOhSm9m63dVE/1VUQYMp6hG9TV/D26dUm"))
            System.out.println("It matches");
        else
            System.out.println("It does not match");
    }
}
