package com.iemr.hwc.utils.validator;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class URLValidator {

    public static URL convertToSafeURL(String urlString, String masteString) {
        try {
            URL url = new URL(urlString);

            // Whitelist approved schemes (e.g., http and https)
            String scheme = url.getProtocol().toLowerCase();
            String hostname = url.getHost();
            if (!"http".equals(scheme) && !"https".equals(scheme) && !masteString.equals(hostname)) {
                throw new MalformedURLException("Unsupported URL");
            }

            // Add more validation and restriction logic if required

            return url;
        } catch (MalformedURLException e) {
            // Handle validation errors or invalid URLs here
            e.printStackTrace();
            return null;
        }
    }
}
