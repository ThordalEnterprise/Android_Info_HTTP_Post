package com.example.androidid


import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*
import kotlin.experimental.and


class MainActivity : AppCompatActivity() {


    //String Functions
    fun getMacAddr(): String {
        try {
            val all: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(Integer.toHexString((b and 0xFF.toByte()).toInt()) + ":")
                }
                if (res1.length > 0) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: java.lang.Exception) {
            //handle exception
        }
        return ""
    }
    fun getIpv4HostAddress(): String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress }
        }
        return ""
    }
    fun getAndroidVersion(): String {
        val release = Build.VERSION.RELEASE
        val sdkVersion = Build.VERSION.SDK_INT
        return "Android SDK: $sdkVersion ($release) "
    }


    //Override Function
    @SuppressLint("HardwareIds")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //-----------------------------------------------------------------





        //-----------------------------------------------------------------






        //Network-----------------------------------------------------------------
        //Google Ad ID
        val GoogleADID = findViewById<TextView>(R.id.adidText2)
        GlobalScope.launch {
            val adid =
                com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(
                    applicationContext
                ).id
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                GoogleADID.setText(adid)
            }
        }

        // Android ID
        val Androidid = findViewById<TextView>(R.id.adidText4)
        GlobalScope.launch {
            val adid = Settings.Secure.getString(
                getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID
            );
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                Androidid.setText(adid)
            }
        }

        // Device Name
        val Devicenam = findViewById<TextView>(R.id.adidText6)
        GlobalScope.launch {
            val adid = android.os.Build.MODEL
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                Devicenam.setText(adid)
            }
        }

        // Software Version
        val SoftwareVersion = findViewById<TextView>(R.id.adidText8)
        GlobalScope.launch {
            val adid = getAndroidVersion();
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                SoftwareVersion.setText(adid)
            }
        }

        // Device Model
        val DeviceModel = findViewById<TextView>(R.id.adidText10)
        GlobalScope.launch {
            val adid = android.os.Build.MANUFACTURER + android.os.Build.PRODUCT + Build.BRAND
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                DeviceModel.setText(adid)
            }
        }

        // Serial Number

        // Display
        val disp = findViewById<TextView>(R.id.adidText14)
        GlobalScope.launch {
            val adid = Build.DISPLAY
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                disp.setText(adid)
            }
        }

        // Hardware
        val hardwa = findViewById<TextView>(R.id.adidText16)
        GlobalScope.launch {
            val adid = Build.HARDWARE
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                hardwa.setText(adid)
            }
        }

        // Display
        val user = findViewById<TextView>(R.id.adidText18)
        GlobalScope.launch {
            val adid = Build.USER
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                user.setText(adid)
            }
        }

        // Device Host
        val hostna = findViewById<TextView>(R.id.adidText20)
        GlobalScope.launch {
            val adid = Build.HOST
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                hostna.setText(adid)
            }
        }

        // Device ID
        val devID = findViewById<TextView>(R.id.adidText22)
        GlobalScope.launch {
            val adid = Build.ID
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                devID.setText(adid)
            }
        }

        // Device IP
        val devIP = findViewById<TextView>(R.id.adidText24)
        GlobalScope.launch {
            val adid = getIpv4HostAddress()
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                devIP.setText(adid)
            }
        }

        // Mac IP 1
        val manager =
            getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        val address = info.macAddress
        // MAC Wifi Address
        val macIP = findViewById<TextView>(R.id.adidText26)
        GlobalScope.launch {
            val adid = address
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                macIP.setText(adid)
            }
        }

        // Mac IP 3

        val macIPssm = findViewById<TextView>(R.id.adidText30)
        GlobalScope.launch {
            val adid = getMacAddr()
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                macIPssm.setText(adid)
            }
        }

        // Bluetooth Name of Device
        var strAddress: String? = null
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        strAddress = if (bluetoothAdapter == null) {
            Log.d(TAG, "device doesn't supports bluetooth") // like in case of emulator
            null
        } else bluetoothAdapter.name
        val bluetand = strAddress
        val baa = findViewById<TextView>(R.id.adidText32)
        GlobalScope.launch {
            val adid = bluetand
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa.setText(adid)
            }
        }

        // Device brand
        val ba1 = findViewById<TextView>(R.id.adidText34)
        GlobalScope.launch {
            val adid = Build.BRAND
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                ba1.setText(adid)
            }
        }

        // Device type
        val ba2 = findViewById<TextView>(R.id.adidText36)
        GlobalScope.launch {
            val adid = Build.TYPE
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                ba2.setText(adid)
            }
        }


        // Device FINGERPRINT
        val ba3 = findViewById<TextView>(R.id.adidText38)
        GlobalScope.launch {
            val adid = Build.BOARD
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                ba3.setText(adid)
            }
        }

        // Device product
        val ba4 = findViewById<TextView>(R.id.adidText40)
        GlobalScope.launch {
            val adid = Build.PRODUCT
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                ba4.setText(adid)
            }
        }

        val baa6 = findViewById<TextView>(R.id.adidText42)
        GlobalScope.launch {
            val adid = bluetoothAdapter.address
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa6.setText(adid)
            }
        }

        val baa8 = findViewById<TextView>(R.id.adidText46)
        GlobalScope.launch {
            val adid = bluetoothAdapter.bluetoothLeAdvertiser.toString()
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa8.setText(adid)
            }
        }


        val serviceName = TELEPHONY_SERVICE
        val m_telephonyManager = getSystemService(serviceName) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        //deviceSoftwareVersion
        val baa91 = findViewById<TextView>(R.id.adidText50)
        GlobalScope.launch {
            val adid =
                m_telephonyManager.deviceSoftwareVersion
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa91.setText(adid)
            }
        }

        //manufacturerCode
        val baa92 = findViewById<TextView>(R.id.adidText52)
        GlobalScope.launch {
            val adid = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                m_telephonyManager.manufacturerCode
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa92.setText(adid)
            }
        }

        //networkCountryIso
        val baa93 = findViewById<TextView>(R.id.adidText54)
        GlobalScope.launch {
            val adid = m_telephonyManager.networkCountryIso
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa93.setText(adid)
            }
        }

        //networkOperator
        val baa94 = findViewById<TextView>(R.id.adidText56)
        GlobalScope.launch {
            val adid = m_telephonyManager.networkOperator
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa94.setText(adid)
            }
        }

        //networkOperatorName
        val baa95 = findViewById<TextView>(R.id.adidText58)
        GlobalScope.launch {
            val adid = m_telephonyManager.networkOperatorName
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa95.setText(adid)
            }
        }

        //networkSelectionMode
        val baa96 = findViewById<TextView>(R.id.adidText60)
        GlobalScope.launch {
            val adid = m_telephonyManager.networkSpecifier.toString()
            Log.e("MainActivity", "ADID=" + adid)
            withContext(Dispatchers.Main) {
                baa96.setText(adid)
            }
        }

        val connectivityMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nwInfos = connectivityMgr.allNetworkInfo


        // slut på onCreate
    }
    // klasse slut
}





