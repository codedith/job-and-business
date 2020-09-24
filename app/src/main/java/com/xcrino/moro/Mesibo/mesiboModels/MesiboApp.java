package com.xcrino.moro.Mesibo.mesiboModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MesiboApp {

    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("u_msgsent")
    @Expose
    private String uMsgsent;
    @SerializedName("u_msgrecv")
    @Expose
    private String uMsgrecv;
    @SerializedName("u_msgfail")
    @Expose
    private String uMsgfail;
    @SerializedName("u_bytessent")
    @Expose
    private String uBytessent;
    @SerializedName("u_bytesrecv")
    @Expose
    private String uBytesrecv;
    @SerializedName("u_bytesfail")
    @Expose
    private String uBytesfail;
    @SerializedName("u_bytesnotify")
    @Expose
    private String uBytesnotify;
    @SerializedName("u_dbreads")
    @Expose
    private String uDbreads;
    @SerializedName("u_dbwrites")
    @Expose
    private String uDbwrites;
    @SerializedName("u_dbbytesread")
    @Expose
    private String uDbbytesread;
    @SerializedName("u_dbbyteswrite")
    @Expose
    private String uDbbyteswrite;
    @SerializedName("u_db")
    @Expose
    private String uDb;
    @SerializedName("u_conn")
    @Expose
    private String uConn;
    @SerializedName("u_mau")
    @Expose
    private String uMau;
    @SerializedName("u_notify")
    @Expose
    private String uNotify;
    @SerializedName("u_logins")
    @Expose
    private String uLogins;
    @SerializedName("u_users")
    @Expose
    private String uUsers;
    @SerializedName("u_groups")
    @Expose
    private String uGroups;
    @SerializedName("q_msgs")
    @Expose
    private String qMsgs;
    @SerializedName("q_users")
    @Expose
    private String qUsers;
    @SerializedName("q_groups")
    @Expose
    private String qGroups;
    @SerializedName("msgs")
    @Expose
    private String msgs;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("groups")
    @Expose
    private String groups;
    @SerializedName("conn")
    @Expose
    private String conn;
    @SerializedName("d_extra")
    @Expose
    private String dExtra;
    @SerializedName("maxmsgs")
    @Expose
    private String maxmsgs;
    @SerializedName("maxusers")
    @Expose
    private String maxusers;
    @SerializedName("maxgroups")
    @Expose
    private String maxgroups;
    @SerializedName("maxconn")
    @Expose
    private String maxconn;
    @SerializedName("turnurl")
    @Expose
    private String turnurl;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("notify")
    @Expose
    private String notify;
    @SerializedName("nrate")
    @Expose
    private String nrate;
    @SerializedName("ninterval")
    @Expose
    private String ninterval;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("f_updated")
    @Expose
    private String fUpdated;
    @SerializedName("f_suspended")
    @Expose
    private String fSuspended;
    @SerializedName("b_suspended")
    @Expose
    private String bSuspended;
    @SerializedName("f_reload")
    @Expose
    private String fReload;
    @SerializedName("f_deleted")
    @Expose
    private String fDeleted;
    @SerializedName("f_beta")
    @Expose
    private String fBeta;
    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("uts")
    @Expose
    private String uts;
    @SerializedName("burl")
    @Expose
    private String burl;
    @SerializedName("fcm_id")
    @Expose
    private String fcmId;
    @SerializedName("fcm_key")
    @Expose
    private String fcmKey;
    @SerializedName("apn_info")
    @Expose
    private String apnInfo;
    @SerializedName("token")
    @Expose
    private String token;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUMsgsent() {
        return uMsgsent;
    }

    public void setUMsgsent(String uMsgsent) {
        this.uMsgsent = uMsgsent;
    }

    public String getUMsgrecv() {
        return uMsgrecv;
    }

    public void setUMsgrecv(String uMsgrecv) {
        this.uMsgrecv = uMsgrecv;
    }

    public String getUMsgfail() {
        return uMsgfail;
    }

    public void setUMsgfail(String uMsgfail) {
        this.uMsgfail = uMsgfail;
    }

    public String getUBytessent() {
        return uBytessent;
    }

    public void setUBytessent(String uBytessent) {
        this.uBytessent = uBytessent;
    }

    public String getUBytesrecv() {
        return uBytesrecv;
    }

    public void setUBytesrecv(String uBytesrecv) {
        this.uBytesrecv = uBytesrecv;
    }

    public String getUBytesfail() {
        return uBytesfail;
    }

    public void setUBytesfail(String uBytesfail) {
        this.uBytesfail = uBytesfail;
    }

    public String getUBytesnotify() {
        return uBytesnotify;
    }

    public void setUBytesnotify(String uBytesnotify) {
        this.uBytesnotify = uBytesnotify;
    }

    public String getUDbreads() {
        return uDbreads;
    }

    public void setUDbreads(String uDbreads) {
        this.uDbreads = uDbreads;
    }

    public String getUDbwrites() {
        return uDbwrites;
    }

    public void setUDbwrites(String uDbwrites) {
        this.uDbwrites = uDbwrites;
    }

    public String getUDbbytesread() {
        return uDbbytesread;
    }

    public void setUDbbytesread(String uDbbytesread) {
        this.uDbbytesread = uDbbytesread;
    }

    public String getUDbbyteswrite() {
        return uDbbyteswrite;
    }

    public void setUDbbyteswrite(String uDbbyteswrite) {
        this.uDbbyteswrite = uDbbyteswrite;
    }

    public String getUDb() {
        return uDb;
    }

    public void setUDb(String uDb) {
        this.uDb = uDb;
    }

    public String getUConn() {
        return uConn;
    }

    public void setUConn(String uConn) {
        this.uConn = uConn;
    }

    public String getUMau() {
        return uMau;
    }

    public void setUMau(String uMau) {
        this.uMau = uMau;
    }

    public String getUNotify() {
        return uNotify;
    }

    public void setUNotify(String uNotify) {
        this.uNotify = uNotify;
    }

    public String getULogins() {
        return uLogins;
    }

    public void setULogins(String uLogins) {
        this.uLogins = uLogins;
    }

    public String getUUsers() {
        return uUsers;
    }

    public void setUUsers(String uUsers) {
        this.uUsers = uUsers;
    }

    public String getUGroups() {
        return uGroups;
    }

    public void setUGroups(String uGroups) {
        this.uGroups = uGroups;
    }

    public String getQMsgs() {
        return qMsgs;
    }

    public void setQMsgs(String qMsgs) {
        this.qMsgs = qMsgs;
    }

    public String getQUsers() {
        return qUsers;
    }

    public void setQUsers(String qUsers) {
        this.qUsers = qUsers;
    }

    public String getQGroups() {
        return qGroups;
    }

    public void setQGroups(String qGroups) {
        this.qGroups = qGroups;
    }

    public String getMsgs() {
        return msgs;
    }

    public void setMsgs(String msgs) {
        this.msgs = msgs;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }

    public String getDExtra() {
        return dExtra;
    }

    public void setDExtra(String dExtra) {
        this.dExtra = dExtra;
    }

    public String getMaxmsgs() {
        return maxmsgs;
    }

    public void setMaxmsgs(String maxmsgs) {
        this.maxmsgs = maxmsgs;
    }

    public String getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(String maxusers) {
        this.maxusers = maxusers;
    }

    public String getMaxgroups() {
        return maxgroups;
    }

    public void setMaxgroups(String maxgroups) {
        this.maxgroups = maxgroups;
    }

    public String getMaxconn() {
        return maxconn;
    }

    public void setMaxconn(String maxconn) {
        this.maxconn = maxconn;
    }

    public String getTurnurl() {
        return turnurl;
    }

    public void setTurnurl(String turnurl) {
        this.turnurl = turnurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getNrate() {
        return nrate;
    }

    public void setNrate(String nrate) {
        this.nrate = nrate;
    }

    public String getNinterval() {
        return ninterval;
    }

    public void setNinterval(String ninterval) {
        this.ninterval = ninterval;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFUpdated() {
        return fUpdated;
    }

    public void setFUpdated(String fUpdated) {
        this.fUpdated = fUpdated;
    }

    public String getFSuspended() {
        return fSuspended;
    }

    public void setFSuspended(String fSuspended) {
        this.fSuspended = fSuspended;
    }

    public String getBSuspended() {
        return bSuspended;
    }

    public void setBSuspended(String bSuspended) {
        this.bSuspended = bSuspended;
    }

    public String getFReload() {
        return fReload;
    }

    public void setFReload(String fReload) {
        this.fReload = fReload;
    }

    public String getFDeleted() {
        return fDeleted;
    }

    public void setFDeleted(String fDeleted) {
        this.fDeleted = fDeleted;
    }

    public String getFBeta() {
        return fBeta;
    }

    public void setFBeta(String fBeta) {
        this.fBeta = fBeta;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUts() {
        return uts;
    }

    public void setUts(String uts) {
        this.uts = uts;
    }

    public String getBurl() {
        return burl;
    }

    public void setBurl(String burl) {
        this.burl = burl;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public String getApnInfo() {
        return apnInfo;
    }

    public void setApnInfo(String apnInfo) {
        this.apnInfo = apnInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
