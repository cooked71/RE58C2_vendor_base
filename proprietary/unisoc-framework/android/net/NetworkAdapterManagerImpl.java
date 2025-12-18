package android.net;

import android.net.INetworkAdapterService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.net.InetAddress;

/* loaded from: classes.dex */
public class NetworkAdapterManagerImpl extends NetworkAdapterManager {
    private final String TAG = "NetworkAdapterManagerImpl";
    private INetworkAdapterService mNAS;

    public NetworkAdapterManagerImpl() {
        getNetworkAdapter();
    }

    public void setIPv6Mtu(String iface, int ipv6Mtu) {
        Log.d("NetworkAdapterManagerImpl", "setIPv6Mtu: ipv6Mtu = " + ipv6Mtu);
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            this.mNAS.setIPv6Mtu(iface, ipv6Mtu);
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public void sendCmdsToDaemon(String cmd) {
        Log.d("NetworkAdapterManagerImpl", "sendCmdsToDaemon: cmd = " + cmd);
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            this.mNAS.sendCmdsToDaemon(cmd);
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public void setDnsFilterEnabled(int enabled) {
        Log.d("NetworkAdapterManagerImpl", "setDnsFilterEnabled: enabled = " + enabled);
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            this.mNAS.setDnsFilterEnabled(enabled);
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public void setEthernetInterface(int enabled) {
        Log.d("NetworkAdapterManagerImpl", "setEthernetInterface: enabled = " + enabled);
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            this.mNAS.setEthernetInterface(enabled);
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public void deleteExtraIPv6Addr(String ipv6Addr, String interfaceName) {
        Log.d("NetworkAdapterManagerImpl", "deleteExtraIPv6Addr: ipv6Addr = " + ipv6Addr);
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            this.mNAS.deleteExtraIPv6Addr(ipv6Addr, interfaceName);
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public boolean bindAppUidToNetwork(int uid, Network network) {
        Log.d("NetworkAdapterManagerImpl", "Bind app's uid to network");
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            return this.mNAS.bindAppUidToNetwork(uid, network);
        } catch (RemoteException e) {
            Log.e("NetworkAdapterManagerImpl", "Unable to bind appid to network");
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public boolean bindDstIpToNetwork(InetAddress dstAddr, Network network) {
        Log.d("NetworkAdapterManagerImpl", "Bind app's IP address to network");
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            return this.mNAS.bindDstIpToNetwork(dstAddr.getHostAddress(), network);
        } catch (RemoteException e) {
            Log.e("NetworkAdapterManagerImpl", "Unable to bind destined IP to network");
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    private void getNetworkAdapter() {
        if (this.mNAS == null) {
            this.mNAS = INetworkAdapterService.Stub.asInterface(ServiceManager.getService("network_adapter"));
        }
        Log.d("NetworkAdapterManagerImpl", "getNetworkAdapter: mNAS = " + this.mNAS);
    }

    public int doPingForVowifi(int ipv4Flag, String srcIP, String dstIP) {
        Log.d("NetworkAdapterManagerImpl", "doPingForVowifi: dstIP = " + dstIP);
        if (this.mNAS == null) {
            getNetworkAdapter();
        }
        try {
            return this.mNAS.doPingForVowifi(ipv4Flag, srcIP, dstIP);
        } catch (RemoteException e) {
            Log.e("NetworkAdapterManagerImpl", "Unable to do Ping for Vowifi");
            return -1;
        } catch (NullPointerException e2) {
            return -1;
        }
    }
}