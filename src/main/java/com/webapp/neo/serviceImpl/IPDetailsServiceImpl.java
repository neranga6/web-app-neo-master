package com.webapp.neo.serviceImpl;

import com.webapp.neo.model.IPDetails;
import com.webapp.neo.repositories.IPRepository;
import com.webapp.neo.service.IPDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class IPDetailsServiceImpl implements IPDetailsService {
    Logger logger = LoggerFactory.getLogger(IPDetailsServiceImpl.class);

    private final IPRepository iprepository;

    @Autowired
    IPDetailsServiceImpl(IPRepository iprepository) {
        this.iprepository = iprepository;
    }

    public String ipGrab(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        ipAddress = Optional.ofNullable(ipAddress)
                .filter(ip -> !"unknown".equalsIgnoreCase(ip))
                .orElseGet(() -> request.getHeader("Proxy-Client-IP"));

        ipAddress = Optional.ofNullable(ipAddress)
                .filter(ip -> !"unknown".equalsIgnoreCase(ip))
                .orElseGet(() -> request.getHeader("WL-Proxy-Client-IP"));

        ipAddress = Optional.ofNullable(ipAddress)
                .filter(ip -> !"unknown".equalsIgnoreCase(ip))
                .orElseGet(() -> {
                    String ip = request.getRemoteAddr();
                    if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                        try {
                            InetAddress inetAddress = InetAddress.getLocalHost();
                            return inetAddress.getHostAddress();
                        } catch (UnknownHostException e) {
                            logger.error(e.getMessage());
                        }
                    }
                    return ip;
                });

        if (Optional.ofNullable(ipAddress).filter(ip -> ip.length() > 15 && ip.contains(",")).isPresent()) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    @Async
    public void saveIPDetails(String ip) {
        IPDetails myIP = new IPDetails();
        myIP.setIP(ip);
        iprepository.save(myIP);
        CompletableFuture.completedFuture(myIP);

    }
}
