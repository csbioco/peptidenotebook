package com.mycompany.myapp.service.classlib;

import com.mycompany.myapp.domain.CalculatedEntry;
import java.util.*;
import com.mycompany.myapp.service.classlib.Protocolret;

public class Ret {
    List<Protocolret> protocolret;
    List<CalculatedEntry> protocolentry;
    public Ret (List<Protocolret> protocolret, List<CalculatedEntry> protocolentry) {
        this.protocolret = protocolret;
        this.protocolentry = protocolentry;
    }
    
    public void setProtocolret(List<Protocolret> data) {
        this.protocolret = data;
    }
    public List<Protocolret> getProtocolret() {
        return protocolret;
    }

    public void setProtocolentry(List<CalculatedEntry> data) {
        this.protocolentry = data;
    }
    public List<CalculatedEntry> getProtocolentry() {
        return protocolentry;
    }
    
}