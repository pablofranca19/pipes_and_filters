package model;

import java.util.Objects;

public class Log {

    private String ip;
    private String date;
    private String requestType;
    private String endpoint;
    private String httpVersion;
    private String statusCode;
    private String byteLength;

    public Log(String ip, String date, String requestType, String endpoint, String httpVersion, String statusCode, String byteLength) {
        this.ip = ip;
        this.date = date;
        this.requestType = requestType;
        this.endpoint = endpoint;
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.byteLength = byteLength;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getByteLength() {
        return byteLength;
    }

    public void setByteLength(String byteLength) {
        this.byteLength = byteLength;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Log log)) return false;
        return Objects.equals(ip, log.ip) && Objects.equals(date, log.date) && Objects.equals(requestType, log.requestType) && Objects.equals(endpoint, log.endpoint) && Objects.equals(httpVersion, log.httpVersion) && Objects.equals(statusCode, log.statusCode) && Objects.equals(byteLength, log.byteLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, date, requestType, endpoint, httpVersion, statusCode, byteLength);
    }
}


