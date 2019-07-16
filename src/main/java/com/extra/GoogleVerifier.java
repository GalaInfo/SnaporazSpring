/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extra;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Arrays;

/**
 *
 * @author Marco
 */
public class GoogleVerifier {
    
    public static GoogleIdToken verify(String idTokenString){
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory()).setAudience(Arrays.asList("617542772314-f113vd4k8p397fto9s3pvopdknutqiv5.apps.googleusercontent.com", "617542772314-keo0t31kssvhk31g3ghjhho21m8s53cm.apps.googleusercontent.com", "617542772314-659dg0petgomou4g1lcqhquhictq0i24.apps.googleusercontent.com", "617542772314-9ma5pqji4ffagb0qhn3j6hruvntncvcj.apps.googleusercontent.com")).build();
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (Exception ex) {}
        return idToken;
    }
}
